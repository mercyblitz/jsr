/*
 * @(#)folderlist.java	1.31 07/07/06
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

import java.util.Properties;
import javax.mail.*;

import com.sun.mail.imap.*;

/**
 * Demo app that exercises the Message interfaces.
 * List information about folders.
 *
 * @author John Mani
 * @author Bill Shannon
 */

public class folderlist {
    static String protocol = null;
    static String host = null;
    static String user = null;
    static String password = null;
    static String url = null;
    static String root = null;
    static String pattern = "%";
    static boolean recursive = false;
    static boolean verbose = false;
    static boolean debug = false;

    public static void main(String argv[]) throws Exception {
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
	    } else if (argv[optind].equals("-L")) {
		url = argv[++optind];
	    } else if (argv[optind].equals("-R")) {
		root = argv[++optind];
	    } else if (argv[optind].equals("-r")) {
		recursive = true;
	    } else if (argv[optind].equals("-v")) {
		verbose = true;
	    } else if (argv[optind].equals("-D")) {
		debug = true;
	    } else if (argv[optind].equals("--")) {
		optind++;
		break;
	    } else if (argv[optind].startsWith("-")) {
		System.out.println(
"Usage: folderlist [-T protocol] [-H host] [-U user] [-P password] [-L url]");
		System.out.println(
"\t[-R root] [-r] [-v] [-D] [pattern]");
		System.exit(1);
	    } else {
		break;
	    }
	}
	if (optind < argv.length)
	    pattern = argv[optind];

	// Get a Properties object
	Properties props = System.getProperties();

	// Get a Session object
	Session session = Session.getInstance(props, null);
	session.setDebug(debug);

	// Get a Store object
	Store store = null;
	Folder rf = null;
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

	// List namespace
	if (root != null)
	    rf = store.getFolder(root);
	else
	    rf = store.getDefaultFolder();

	dumpFolder(rf, false, "");
	if ((rf.getType() & Folder.HOLDS_FOLDERS) != 0) {
	    Folder[] f = rf.list(pattern);
	    for (int i = 0; i < f.length; i++)
		dumpFolder(f[i], recursive, "    ");
	}

	store.close();
    }

    static void dumpFolder(Folder folder, boolean recurse, String tab)
					throws Exception {
    	System.out.println(tab + "Name:      " + folder.getName());
	System.out.println(tab + "Full Name: " + folder.getFullName());
	System.out.println(tab + "URL:       " + folder.getURLName());

	if (verbose) {
	    if (!folder.isSubscribed())
		System.out.println(tab + "Not Subscribed");

	    if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
		if (folder.hasNewMessages())
		    System.out.println(tab + "Has New Messages");
		System.out.println(tab + "Total Messages:  " +
						folder.getMessageCount());
		System.out.println(tab + "New Messages:    " +
						folder.getNewMessageCount());
		System.out.println(tab + "Unread Messages: " +
						folder.getUnreadMessageCount());
	    }
	    if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0)
		System.out.println(tab + "Is Directory");

	    /*
	     * Demonstrate use of IMAP folder attributes
	     * returned by the IMAP LIST response.
	     */
	    if (folder instanceof IMAPFolder) {
		IMAPFolder f = (IMAPFolder)folder;
		String[] attrs = f.getAttributes();
		if (attrs != null && attrs.length > 0) {
		    System.out.println(tab + "IMAP Attributes:");
		    for (int i = 0; i < attrs.length; i++)
			System.out.println(tab + "    " + attrs[i]);
		}
	    }
	}

	System.out.println();

	if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
	    if (recurse) {
		Folder[] f = folder.list();
		for (int i = 0; i < f.length; i++)
		    dumpFolder(f[i], recurse, tab + "    ");
	    }
	}
    }
}
