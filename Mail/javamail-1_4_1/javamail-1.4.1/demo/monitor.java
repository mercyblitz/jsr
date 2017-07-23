/*
 * @(#)monitor.java	1.10 07/07/06
 *
 * Copyright 1996-2007 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.event.*;
import javax.activation.*;

import com.sun.mail.imap.*;

/* Monitors given mailbox for new mail */

public class monitor {

    public static void main(String argv[]) {
	if (argv.length != 5) {
	    System.out.println(
		"Usage: monitor <host> <user> <password> <mbox> <freq>");
	    System.exit(1);
	}
	System.out.println("\nTesting monitor\n");

        try {
	    Properties props = System.getProperties();

	    // Get a Session object
	    Session session = Session.getInstance(props, null);
	    // session.setDebug(true);

	    // Get a Store object
	    Store store = session.getStore("imap");

	    // Connect
	    store.connect(argv[0], argv[1], argv[2]);

	    // Open a Folder
	    Folder folder = store.getFolder(argv[3]);
	    if (folder == null || !folder.exists()) {
		System.out.println("Invalid folder");
		System.exit(1);
	    }

	    folder.open(Folder.READ_WRITE);

	    // Add messageCountListener to listen for new messages
	    folder.addMessageCountListener(new MessageCountAdapter() {
		public void messagesAdded(MessageCountEvent ev) {
		    Message[] msgs = ev.getMessages();
		    System.out.println("Got " + msgs.length + " new messages");

		    // Just dump out the new messages
		    for (int i = 0; i < msgs.length; i++) {
			try {
			    System.out.println("-----");
			    System.out.println("Message " +
				msgs[i].getMessageNumber() + ":");
			    msgs[i].writeTo(System.out);
			} catch (IOException ioex) { 
			    ioex.printStackTrace();	
			} catch (MessagingException mex) {
			    mex.printStackTrace();
			}
		    }
		}
	    });
			
	    // Check mail once in "freq" MILLIseconds
	    int freq = Integer.parseInt(argv[4]);
	    boolean supportsIdle = false;
	    try {
		if (folder instanceof IMAPFolder) {
		    IMAPFolder f = (IMAPFolder)folder;
		    f.idle();
		    supportsIdle = true;
		}
	    } catch (FolderClosedException fex) {
		throw fex;
	    } catch (MessagingException mex) {
		supportsIdle = false;
	    }
	    for (;;) {
		if (supportsIdle && folder instanceof IMAPFolder) {
		    IMAPFolder f = (IMAPFolder)folder;
		    f.idle();
		    System.out.println("IDLE done");
		} else {
		    Thread.sleep(freq); // sleep for freq milliseconds

		    // This is to force the IMAP server to send us
		    // EXISTS notifications. 
		    folder.getMessageCount();
		}
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
