/*
 * @(#)msgshow.java	1.34 07/07/06
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
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.activation.*;

/*
 * Demo app that exercises the Message interfaces.
 * Show information about and contents of messages.
 *
 * @author John Mani
 * @author Bill Shannon
 */

public class msgshow {

    static String protocol;
    static String host = null;
    static String user = null;
    static String password = null;
    static String mbox = null;
    static String url = null;
    static int port = -1;
    static boolean verbose = false;
    static boolean debug = false;
    static boolean showStructure = false;
    static boolean showMessage = false;
    static boolean showAlert = false;
    static boolean saveAttachments = false;
    static int attnum = 1;

    public static void main(String argv[]) {
	int msgnum = -1;
	int optind;
	InputStream msgStream = System.in;

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
	    } else if (argv[optind].equals("-D")) {
		debug = true;
	    } else if (argv[optind].equals("-f")) {
		mbox = argv[++optind];
	    } else if (argv[optind].equals("-L")) {
		url = argv[++optind];
	    } else if (argv[optind].equals("-p")) {
		port = Integer.parseInt(argv[++optind]);
	    } else if (argv[optind].equals("-s")) {
		showStructure = true;
	    } else if (argv[optind].equals("-S")) {
		saveAttachments = true;
	    } else if (argv[optind].equals("-m")) {
		showMessage = true;
	    } else if (argv[optind].equals("-a")) {
		showAlert = true;
	    } else if (argv[optind].equals("--")) {
		optind++;
		break;
	    } else if (argv[optind].startsWith("-")) {
		System.out.println(
"Usage: msgshow [-L url] [-T protocol] [-H host] [-p port] [-U user]");
		System.out.println(
"\t[-P password] [-f mailbox] [msgnum] [-v] [-D] [-s] [-S] [-a]");
		System.out.println(
"or     msgshow -m [-v] [-D] [-s] [-S] [-f msg-file]");
		System.exit(1);
	    } else {
		break;
	    }
	}

        try {
	    if (optind < argv.length)
	         msgnum = Integer.parseInt(argv[optind]);

	    // Get a Properties object
	    Properties props = System.getProperties();

	    // Get a Session object
	    Session session = Session.getInstance(props, null);
	    session.setDebug(debug);

	    if (showMessage) {
		MimeMessage msg;
		if (mbox != null)
		    msg = new MimeMessage(session,
			new BufferedInputStream(new FileInputStream(mbox)));
		else
		    msg = new MimeMessage(session, msgStream);
		dumpPart(msg);
		System.exit(0);
	    }

	    // Get a Store object
	    Store store = null;
	    if (url != null) {
		URLName urln = new URLName(url);
		store = session.getStore(urln);
		if (showAlert) {
		    store.addStoreListener(new StoreListener() {
			public void notification(StoreEvent e) {
			    String s;
			    if (e.getMessageType() == StoreEvent.ALERT)
				s = "ALERT: ";
			    else
				s = "NOTICE: ";
			    System.out.println(s + e.getMessage());
			}
		    });
		}
		store.connect();
	    } else {
		if (protocol != null)		
		    store = session.getStore(protocol);
		else
		    store = session.getStore();

		// Connect
		if (host != null || user != null || password != null)
		    store.connect(host, port, user, password);
		else
		    store.connect();
	    }
	    

	    // Open the Folder

	    Folder folder = store.getDefaultFolder();
	    if (folder == null) {
	        System.out.println("No default folder");
	        System.exit(1);
	    }

	    if (mbox == null)
		mbox = "INBOX";
	    folder = folder.getFolder(mbox);
	    if (folder == null) {
	        System.out.println("Invalid folder");
	        System.exit(1);
	    }

	    // try to open read/write and if that fails try read-only
	    try {
		folder.open(Folder.READ_WRITE);
	    } catch (MessagingException ex) {
		folder.open(Folder.READ_ONLY);
	    }
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

	    if (msgnum == -1) {
		// Attributes & Flags for all messages ..
		Message[] msgs = folder.getMessages();

		// Use a suitable FetchProfile
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.FLAGS);
		fp.add("X-Mailer");
		folder.fetch(msgs, fp);

		for (int i = 0; i < msgs.length; i++) {
		    System.out.println("--------------------------");
		    System.out.println("MESSAGE #" + (i + 1) + ":");
		    dumpEnvelope(msgs[i]);
		    // dumpPart(msgs[i]);
		}
	    } else {
		System.out.println("Getting message number: " + msgnum);
		Message m = null;
		
		try {
		    m = folder.getMessage(msgnum);
		    dumpPart(m);
		} catch (IndexOutOfBoundsException iex) {
		    System.out.println("Message number out of range");
		}
	    }

	    folder.close(false);
	    store.close();
	} catch (Exception ex) {
	    System.out.println("Oops, got exception! " + ex.getMessage());
	    ex.printStackTrace();
	    System.exit(1);
	}
	System.exit(0);
    }

    public static void dumpPart(Part p) throws Exception {
	if (p instanceof Message)
	    dumpEnvelope((Message)p);

	/** Dump input stream .. 

	InputStream is = p.getInputStream();
	// If "is" is not already buffered, wrap a BufferedInputStream
	// around it.
	if (!(is instanceof BufferedInputStream))
	    is = new BufferedInputStream(is);
	int c;
	while ((c = is.read()) != -1)
	    System.out.write(c);

	**/

	String ct = p.getContentType();
	try {
	    pr("CONTENT-TYPE: " + (new ContentType(ct)).toString());
	} catch (ParseException pex) {
	    pr("BAD CONTENT-TYPE: " + ct);
	}
	String filename = p.getFileName();
	if (filename != null)
	    pr("FILENAME: " + filename);

	/*
	 * Using isMimeType to determine the content type avoids
	 * fetching the actual content data until we need it.
	 */
	if (p.isMimeType("text/plain")) {
	    pr("This is plain text");
	    pr("---------------------------");
	    if (!showStructure && !saveAttachments)
		System.out.println((String)p.getContent());
	} else if (p.isMimeType("multipart/*")) {
	    pr("This is a Multipart");
	    pr("---------------------------");
	    Multipart mp = (Multipart)p.getContent();
	    level++;
	    int count = mp.getCount();
	    for (int i = 0; i < count; i++)
		dumpPart(mp.getBodyPart(i));
	    level--;
	} else if (p.isMimeType("message/rfc822")) {
	    pr("This is a Nested Message");
	    pr("---------------------------");
	    level++;
	    dumpPart((Part)p.getContent());
	    level--;
	} else {
	    if (!showStructure && !saveAttachments) {
		/*
		 * If we actually want to see the data, and it's not a
		 * MIME type we know, fetch it and check its Java type.
		 */
		Object o = p.getContent();
		if (o instanceof String) {
		    pr("This is a string");
		    pr("---------------------------");
		    System.out.println((String)o);
		} else if (o instanceof InputStream) {
		    pr("This is just an input stream");
		    pr("---------------------------");
		    InputStream is = (InputStream)o;
		    int c;
		    while ((c = is.read()) != -1)
			System.out.write(c);
		} else {
		    pr("This is an unknown type");
		    pr("---------------------------");
		    pr(o.toString());
		}
	    } else {
		// just a separator
		pr("---------------------------");
	    }
	}

	/*
	 * If we're saving attachments, write out anything that
	 * looks like an attachment into an appropriately named
	 * file.  Don't overwrite existing files to prevent
	 * mistakes.
	 */
	if (saveAttachments && level != 0 && !p.isMimeType("multipart/*")) {
	    String disp = p.getDisposition();
	    // many mailers don't include a Content-Disposition
	    if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
		if (filename == null)
		    filename = "Attachment" + attnum++;
		pr("Saving attachment to file " + filename);
		try {
		    File f = new File(filename);
		    if (f.exists())
			// XXX - could try a series of names
			throw new IOException("file exists");
		    ((MimeBodyPart)p).saveFile(f);
		} catch (IOException ex) {
		    pr("Failed to save attachment: " + ex);
		}
		pr("---------------------------");
	    }
	}
    }

    public static void dumpEnvelope(Message m) throws Exception {
	pr("This is the message envelope");
	pr("---------------------------");
	Address[] a;
	// FROM 
	if ((a = m.getFrom()) != null) {
	    for (int j = 0; j < a.length; j++)
		pr("FROM: " + a[j].toString());
	}

	// TO
	if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	    for (int j = 0; j < a.length; j++) {
		pr("TO: " + a[j].toString());
		InternetAddress ia = (InternetAddress)a[j];
		if (ia.isGroup()) {
		    InternetAddress[] aa = ia.getGroup(false);
		    for (int k = 0; k < aa.length; k++)
			pr("  GROUP: " + aa[k].toString());
		}
	    }
	}

	// SUBJECT
	pr("SUBJECT: " + m.getSubject());

	// DATE
	Date d = m.getSentDate();
	pr("SendDate: " +
	    (d != null ? d.toString() : "UNKNOWN"));

	// FLAGS
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
	pr("FLAGS: " + sb.toString());

	// X-MAILER
	String[] hdrs = m.getHeader("X-Mailer");
	if (hdrs != null)
	    pr("X-Mailer: " + hdrs[0]);
	else
	    pr("X-Mailer NOT available");
    }

    static String indentStr = "                                               ";
    static int level = 0;

    /**
     * Print a, possibly indented, string.
     */
    public static void pr(String s) {
	if (showStructure)
	    System.out.print(indentStr.substring(0, level * 2));
	System.out.println(s);
    }
}
