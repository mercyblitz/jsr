/*
 * @(#)uidmsgshow.java	1.7 07/07/06
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All Rights Reserved.
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
import javax.mail.internet.*;
import javax.activation.*;

/*
 * Demo app that exercises the Message interfaces.
 * Access message given its UID.
 * Show information about and contents of messages.
 *
 * @author John Mani
 * @author Bill Shannon
 */

public class uidmsgshow {

    static String protocol;
    static String host = null;
    static String user = null;
    static String password = null;
    static String mbox = "INBOX";
    static String url = null;
    static boolean verbose = false;

    public static void main(String argv[]) {
	long uid = -1;
	int optind;

	for (optind = 0; optind < argv.length; optind++) {
	    if (argv[optind].equals("-T")) {
		protocol = argv[++optind];
	    } else if (argv[optind].equals("-H")) {
		host = argv[++optind];
	    } else if (argv[optind].equals("-U")) {
		user = argv[++optind];
	    } else if (argv[optind].equals("-P")) {
		password = argv[++optind];
	    } else if (argv[optind].equals("-v")) {
		verbose = true;
	    } else if (argv[optind].equals("-f")) {
		mbox = argv[++optind];
	    } else if (argv[optind].equals("-L")) {
		url = argv[++optind];
	    } else if (argv[optind].equals("--")) {
		optind++;
		break;
	    } else if (argv[optind].startsWith("-")) {
		System.out.println("Usage: uidmsgshow [-L url] [-T protocol] [-H host] [-U user] [-P password] [-f mailbox] [uid] [-v]");
		System.exit(1);
	    } else {
		break;
	    }
	}

        try {
	    if (optind < argv.length)
	         uid = Long.parseLong(argv[optind]);

	    // Get a Properties object
	    Properties props = System.getProperties();

	    // Get a Session object
	    Session session = Session.getInstance(props, null);
	    // session.setDebug(true);

	    // Get a Store object
	    Store store = null;
	    if (url != null) {
		URLName urln = new URLName(url);
		store = session.getStore(urln);
		store.connect();
	    } else {
		if (protocol != null)		
		    store = session.getStore(protocol);
		else
		    store = session.getStore();

		// Connect
		if (host != null || user != null || password != null)
		    store.connect(host, user, password);
		else
		    store.connect();
	    }
	    

	    // Open the Folder

	    Folder folder = store.getDefaultFolder();
	    if (folder == null) {
	        System.out.println("No default folder");
	        System.exit(1);
	    }

	    folder = folder.getFolder(mbox);
	    if (!folder.exists()) {
	        System.out.println(mbox + "  does not exist");
	        System.exit(1);
	    }

	    if (!(folder instanceof UIDFolder)) {
	        System.out.println(
		    "This Provider or this folder does not support UIDs");
	        System.exit(1);
	    }

	    UIDFolder ufolder = (UIDFolder)folder;

	    folder.open(Folder.READ_WRITE);
	    int totalMessages = folder.getMessageCount();

	    if (totalMessages == 0) {
		System.out.println("Empty folder");
		folder.close(false);
		store.close();
		System.exit(1);
	    }

	    if (verbose) {
		int newMessages = folder.getNewMessageCount();
		System.out.println("Total messages = " + totalMessages);
		System.out.println("New messages = " + newMessages);
		System.out.println("-------------------------------");
	    }

	    if (uid == -1) {
		// Attributes & Flags for ALL messages ..
		Message[] msgs = 
			ufolder.getMessagesByUID(1, UIDFolder.LASTUID);

		// Use a suitable FetchProfile
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.FLAGS);
		fp.add("X-Mailer");
		folder.fetch(msgs, fp);

		for (int i = 0; i < msgs.length; i++) {
		    System.out.println("--------------------------");
		    System.out.println("MESSAGE UID #" + 
					ufolder.getUID(msgs[i]) + ":");
		    dumpEnvelope(msgs[i]);
		    // dumpPart(msgs[i]);
		}
	    } else {
		System.out.println("Getting message UID: " + uid);
		Message m = ufolder.getMessageByUID(uid);
		if (m != null)
		    dumpPart(m);
		else
		    System.out.println(
			"This Message does not exist on this folder");
	    }

	    folder.close(false);
	    store.close();
	} catch (Exception ex) {
	    System.out.println("Oops, got exception! " + ex.getMessage());
	    ex.printStackTrace();
	}
	System.exit(1);
    }

    public static void dumpPart(Part p) throws Exception {
	if (p instanceof Message)
	    dumpEnvelope((Message)p);

	/* Dump input stream
	InputStream is = new BufferedInputStream(p.getInputStream());
	int c;
	while ((c = is.read()) != -1)
	    System.out.write(c);
	*/

	System.out.println("CONTENT-TYPE: " + p.getContentType());

	Object o = p.getContent();
	if (o instanceof String) {
	    System.out.println("This is a String");
	    System.out.println("---------------------------");
	    System.out.println((String)o);
	} else if (o instanceof Multipart) {
	    System.out.println("This is a Multipart");
	    System.out.println("---------------------------");
	    Multipart mp = (Multipart)o;
	    int count = mp.getCount();
	    for (int i = 0; i < count; i++)
		dumpPart(mp.getBodyPart(i));
	} else if (o instanceof Message) {
	    System.out.println("This is a Nested Message");
	    System.out.println("---------------------------");
	    dumpPart((Part)o);
	} else if (o instanceof InputStream) {
	    System.out.println("This is just an input stream");
	    System.out.println("---------------------------");
	    InputStream is = (InputStream)o;
	    int c;
	    while ((c = is.read()) != -1)
		System.out.write(c);
	}
    }

    public static void dumpEnvelope(Message m) throws Exception {
	System.out.println("This is the message envelope");
	System.out.println("---------------------------");
	Address[] a;
	// FROM 
	if ((a = m.getFrom()) != null) {
	    for (int j = 0; j < a.length; j++)
		System.out.println("FROM: " + a[j].toString());
	}

	// TO
	if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	    for (int j = 0; j < a.length; j++)
		System.out.println("TO: " + a[j].toString());
	}

	// SUBJECT
	System.out.println("SUBJECT: " + m.getSubject());

	// DATE
	Date d = m.getSentDate();
	System.out.println("SendDate: " +
	    (d != null ? d.toString() : "UNKNOWN"));

	// SIZE
	System.out.println("Size: " + m.getSize());

	// FLAGS:
	Flags flags = m.getFlags();
	StringBuffer sb = new StringBuffer();
	Flags.Flag[] sf = flags.getSystemFlags(); // get the system flags

	boolean first = true;
	for (int i = 0; i < sf.length; i++) {
	    String s;
	    Flags.Flag f = sf[i];
	    if (f == Flags.Flag.ANSWERED)
		s = "\\Answered";
	    else if (f == Flags.Flag.DELETED)
		s = "\\Deleted";
	    else if (f == Flags.Flag.DRAFT)
		s = "\\Draft";
	    else if (f == Flags.Flag.FLAGGED)
		s = "\\Flagged";
	    else if (f == Flags.Flag.RECENT)
		s = "\\Recent";
	    else if (f == Flags.Flag.SEEN)
		s = "\\Seen";
	    else
		continue;	// skip it
	    if (first)
		first = false;
	    else
		sb.append(' ');
	    sb.append(s);
	}

	String[] uf = flags.getUserFlags(); // get the user flag strings
	for (int i = 0; i < uf.length; i++) {
	    if (first)
		first = false;
	    else
		sb.append(' ');
	    sb.append(uf[i]);
	}
	System.out.println("FLAGS = " + sb.toString());

	// X-MAILER
	String[] hdrs = m.getHeader("X-Mailer");
	if (hdrs != null)
	    System.out.println("X-Mailer: " + hdrs[0]);
	else
	    System.out.println("X-Mailer NOT available");
    }
}
