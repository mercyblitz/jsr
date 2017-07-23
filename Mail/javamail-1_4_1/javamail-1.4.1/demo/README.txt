	README for the demo programs in this directory
	==============================================

These demo programs illustrate how to use the JavaMail API to
perform a number of common email functions.  Note these these
programs are not intended to be examples of good user interfaces,
or good command line interfaces.  No one is expected to actually
*use* these programs for anything real.  Rather, their value is
in the source code.  Don't look at their command line arguments
or user interface to figure out what JavaMail can do, look at
their source code.  We strongly recommend that you read the
source code and understand what these programs are doing before
running them.

All of these programs are simple command line tools with a UNIX
style interface.  On Windows you'll need to run them in an MS-DOS
window.  We apologize in advance for the inconsistency in how these
programs accept options.  There are generally two styles.  The very
simple style (e.g., as used by copier.java) requires a fixed number
of arguments in a fixed order.  Others (e.g., folderlist.java) take
UNIX-style options, many of which are optional, and which may appear
in any order.  The following notes should help you figure it out,
but if in doubt, read the source code.



- copier.java

	This program copies the specified messages from one folder to
	another. Both folders must belong to the same store.

  Usage:
	java copier <urlname> <src> <dest> <start> <end>

  Arguments (in order):

  <urlname>	: URL of the Store. The URL should include
		  the password as well (if needed).
		  Example: "imap://john:password@mailstore.com"

  <src>		: source folder
  <dest>	: destination folder
  <start>	: start message number
  <end>		: end message number


- folderlist.java

	This program lists information about the folders in a Store.

   Usage:
	java folderlist -L <url> -T <protocol> -H <host> -U <user> -P <passwd>
		   [-R <root>] [-r] [-v] [-D] <pattern>

   Options:

   -L <url>	: URL of the Store. The URL should include
		  the password as well (if needed).
		  Example: "imap://john:password@mailstore.com"
   -T <protocol> : store protocol (Ex: "imap")
   -H <host>	: hostname of store.
   -U <user>	: username (if needed)
   -P <passwd>	: password (if needed)
   -R <root>	: root of the folder hierarchy. This is optional. If
		  not present, listing starts from the default folder.
   -r		: list recursively - folder and all subfolders.
   -v		: verbose - show more info about each folder.
   -D		: Turn on session debugging
   <pattern>	: folders that match this pattern are listed. Use "*"
		  as wildcard to match everything.


- monitor.java

	Illustrates how to monitor a folder for interesting events,
	like new mail arrival.

   Usage:
	java monitor <host> <user> <password> <mbox> <freq>

   Arguments (in order):

   <host>	: hostname of store.
   <user>	: username (if needed)
   <passwd>	: password (if needed)
   <mbox>	: folder to monitor
   <freq>	: frequency of monitoring


- mover.java

	Moves messages between folders.  The folders must belong to the
	same store.

   Usage:
	java mover -T <protocol> -H <host> -U <user> -P <passwd> [-v]
		-s <src> -d <dest> [-x] <start> <end>

   Options:

   -T <protocol> : store protocol (Ex: "imap")
   -H <host>	: hostname of store.
   -U <user>	: username (if needed)
   -P <passwd>	: password (if needed)
   -s <src>	: source folder
   -d <dest>	: destination folder
   -v		: Optional verbose option
   -x		: Optional expunge option, to expunge the deleted
		  messages from src

   Arguments (in order):

   <start>	: start message number
   <end>	: end message number


- msgmultisendsample.java

	Demonstrates how to construct and send a multipart message.

   Usage:
	java msgmultisendsample <to> <from> <smtphost> true|false

   Arguments (in order):

   <to>		: Recipient address
   <from>	: Sender address
   <smtphost>	: name of SMTP server
   true|false	: "true" to turn on session debugging, "false" otherwise


- msgsend.java

	Send a simple text message. Optionally saves a copy
	of the outgoing message in a folder (record-folder).

	Most parameters to this program are optional. When
	the program is run, it interactively asks for
	the "To" and "Subject" fields if not already available.
	Then the program expects the body of the message.
	After you type in the body, hit Ctrl-D on Unix
	systems or Ctrl-Z on Windows systems to send
	the message.

   Usage:
	java msgsend -L <store-url> -T <protocol> -H <host> -U <user>
		-P <passwd> -s <subject> -o <from> -c <cc> -b <bcc>
		-f <record> -M <smtphost> [-d] <to>

   Options:

   -L <store-url> : URL of the store for the record-folder
   -T <protocol> : If <store-url> is not present, this indicates
		  the store protocol for the record-folder.
   -H <host>	: If <store-url> is not present, this indicates
		  the hostname for the record-folder.
   -U <user>	: If <store-url> is not present, this indicates
		  the username for the record-folder.
   -P <passwd>	: If <store-url> is not present, this indicates
		  the password for the record-folder.
   -f <record>	: name of record-folder.
   -M <smtphost> : Host name of SMTP server.  Defaults to "localhost"
		  which often works on UNIX but rarely on Windows.
   -s <subject>	: Subject of message to be sent
   -o <from>	: From address of message to be sent
   -c <cc>	: Cc address of message to be sent
   -b <bcc>	: Bcc address of message to be sent
   -d		: Turn on session debugging.
   -a <file>	: Include file as an attachment with the message

   Argument:

   <to>		: To address of message to be sent


- msgsendsample.java

	Demonstrates how to construct and send a simple text message.

   Usage:
	java msgsendsample <to> <from> <smtphost> true|false

   Arguments (in order):

   <to>		: Recipient address
   <from>	: Sender address
   <smtphost>	: name of SMTP server
   true|false	: "true" to turn on session debugging, "false" otherwise


- msgshow.java

	Displays message(s) from a folder or from stdin.

   Usage:
	java msgshow -L <url> -T <protocol> -H <host> -p <port>
		-U <user> -P <password> -f <mailbox>
		[-D] [-s] [-S] [-a] [-v] [msgnum]
	java msgshow -m [-D] [-s] [-S] [-v]

   Options:

   -L <url>	: URL of the Store. The URL should include
		  the password as well (if needed).
		  Example: "imap://john:password@mailstore.com"
   -T <protocol> : If <url> is not present, this indicates
		  the store protocol
   -H <host>	: If <url> is not present, this indicates
		  the hostname
   -p <port>	: If <url> is not present, this indicates
		  the port number (usually not needed)
   -U <user>	: If <url> is not present, this indicates
		  the username
   -P <passwd>	: If <url> is not present, this indicates
		  the password
   -f <mailbox>	: Folder to open
   -m		: Read message from standard input
   -D		: Turn on session debugging
   -s		: Show the structure of the message, but not the contents
   -S		: Save attachments to appropriately named files
   -a		: Show ALERTS and NOTIFICATIONS from the Store
   -v		: Verbose mode - show total messages and number of new messages

   Argument:

   <msgnum>	: the message to be displayed. If this
  		  parameter is not present, all messages in the
		  folder are displayed.


- namespace.java

	Displays the namespaces supported by a store.

   Usage:
	java namespace -L <url> -T <protocol> -H <host> -p <port>
		-U <user> -P <password> [-D]

   Options:

   -L <url>	: URL of the Store. The URL should include
		  the password as well (if needed).
		  Example: "imap://john:password@mailstore.com"
   -T <protocol> : If <url> is not present, this indicates
		  the store protocol
   -H <host>	: If <url> is not present, this indicates
		  the hostname
   -p <port>	: If <url> is not present, this indicates
		  the port number (usually not needed)
   -U <user>	: If <url> is not present, this indicates
		  the username
   -P <passwd>	: If <url> is not present, this indicates
		  the password
   -D		: Turn on session debugging


- populate.java

	Copies an entire folder hierarchy from one message store to
	another.

   Usage:
	java populate -s <src-url> -d <dest-url> -D -f

   Options:

   -s <src-url>	: URL of source folder
   -d <dest-url> : URL of destination folder
   -D		: Turn on session debugging
   -f		: force the copy to occur even if the destination
		  folder already exists
   -S		: skip special folders named "SCCS", "Drafts", "Trash", and
		  "Shared Folders"
   -c		: clear out old folders before copying messages
   -P		: don't preserve flags when copying messages


- registry.java

	Demonstrates how to query the JavaMail "registry" for providers,
	set default providers, etc.

   Usage:
	java registry


- search.java

	Search the given folder for messages matching the
	given criteria.  Illustrates the use of the
	javax.mail.search package.

   Usage:
	java search -L <url> -T <prot> -H <host> -U <user> -P <passwd>
		-f <folder> -subject <subject> -from <from>
		-today -or

   Options:

   -L <url>	: URL of the store
   -T <protocol> : If <url> is not present, this indicates
		  the store protocol
   -H <host>	: If <url> is not present, this indicates
		  the hostname
   -U <user>	: If <url> is not present, this indicates
		  the username
   -P <passwd>	: If <url> is not present, this indicates
		  the password
   -f <folder>	: folder to search

   -or		: If this flag is present, the search will
		  return messages that match any one of the
		  below criteria. Else the search will only
		  return messages that match all the criteria

   -subject <subject>	: search for messages containing this string
			  as the Subject
   -from <from>		: search for messages containing this string
			  as the From address
   -today		: search for messages received today


- sendfile.java

	Send the specified file to the given address.  The file
	is sent as an attachment.  An SMTP server must be available.

   Usage:
	java sendfile <to> <from> <smtphost> <file> true|false

   Arguments (in order):

   <to>		: Recipient address
   <from>	: Sender address
   <smtphost>	: name of SMTP server
   <file>	: name of file to be sent
   true|false	: "true" to turn on session debugging, "false" otherwise


- sendhtml.java

	The sendhtml program works like the msgsend program, taking
	the same options and input, but the text collected from the
	user is sent as type "text/html" instead of "text/plain".

	This program is a good example of how to send arbitrary
	string data as any arbitrary MIME type.


- smtpsend.java

	Takes the same options as the msgsend program, but illustrates
	how to handle SMTP-specific error codes.  Also accepts the
	following options:

   Option:

   -v		: verbose output
   -A		: use SMTP authentication
   -S		: use SSL

- transport.java

	Illustrates how to use an explicit Transport object, how to
	handle transport exceptions, and how to handle transport events.

   Usage:
	java transport <to> <from> <smtphost> <file> true|false

   Arguments (in order):

   <to>		: Recipient address
   <from>	: Sender address
   <smtphost>	: name of SMTP server
   <file>	: name of file to be sent
   true|false	: "true" to turn on session debugging, "false" otherwise


- uidmsgshow.java

	The uidmsgshow program works like the msgshow program, taking
	the same options, except instead of using message numbers, it
	uses message UID's.  This will typically only work with IMAP
	message stores.
