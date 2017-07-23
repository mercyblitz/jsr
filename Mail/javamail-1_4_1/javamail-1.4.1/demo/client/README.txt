SimpleClient
------------

Notes:
======

This should not be taken as a demo of how to use the Swing API, but
rather a very simple graphical mail client. It shows how viewers can
be used to display the content from mail messages.  It also (like the
other demos) shows how to retrieve Folders from a Store, Messages
from a Folder, and content from Messages.


To run the demo:
================

    1.  If you're using JDK 1.1.x, download the latest version of the JFC
	(Swing) APIs from http://java.sun.com/products/jfc/download.html.
	The SimpleClient uses at least version 1.1 of Swing.

	If you're using JDK 1.2 (J2SE 1.2) or newer, Swing is included
	and no separate download is necessary.

	We *strongly* encourage you to use the latest version of J2SE,
	which you can download from http://java.sun.com/j2se/.

    2.  Set your CLASSPATH to include the "mail.jar", "activation.jar",
	and (if you're using JDK 1.1.x and downloaded Swing separately)
	"swingall.jar", and the current directory.  For example:

	For JDK 1.1 on UNIX:

	export CLASSPATH=/u/me/download/mail.jar:/u/me/download/activation.jar:/u/me/download/swingall.jar:.

	For JDK 1.2 and newer on UNIX:

	export CLASSPATH=/u/me/download/mail.jar:/u/me/download/activation.jar:.

    3.  Go to the demo/client directory

    4.  Compile all the files using your Java compiler.  For example:

	  javac *.java

    5.  Run the demo. For example:

	  java SimpleClient -L imap://username:password@hostname/

	Note that SimpleClient expects to read the "simple.mailcap"
	file from the current directory.  The simple.mailcap file
	contains configuration information about viewers needed by
	the SimpleClient demo program.



Overview of the Classes
=======================

Main Classes:

	SimpleClient   =    contains main().
			     Uses the parameters to the application to
			     locate the correct Store.  e.g.

				SimpleClient -L imap://cotton:secret@snow-goon/

			     It will create the main frame and
			     creates a tree.  The tree uses the
			     StoreTreeNodes and FolderTreeNodes.

	StoreTreeNode   =    subclass of Swing's DefaultMutableTreeNode.
			     This class shows how to get Folders from
			     the Store.

	FolderTreeNode  =    subclass of Swing's DefaultMutableTreeNode.
			     If the folder has messages, it will create
			     a FolderViewer.  Otherwise it will add the
			     subfolders to the tree.

	SimpleAuthenticator = subclass of javax.mail.Authenticator. If
			     the Store is missing the username or the
			     password, this authenticator will be used.
			     It displays a dialog requesting the
			     information from the user.
				

Viewing Folders:

	FolderViewer    =    Uses a Swing Table to display all of the
			     Message in a Folder.  The "model" of the
			     data for this Table is a FolderModel which
			     knows how to get displayable information
			     from a Message.

JAF Viewers:

	MessageViewer   =    Uses the content of the DataHandler.  The
			     content will be a javax.mail.Message
			     object.  Displays the headers and then
			     uses the JAF to find another viewer for
			     the content type of the Message.  (either
			     multipart/mixed, image/gif, or text/plain)

	MultipartViewer =    Uses the content of the DataHandler.  The
			     content will be a javax.mail.Multipart
			     object.  Uses the JAF to find another
			     viewer for the first BodyPart's content.
			     Also puts Buttons (as "attachments") for
			     the rest of the BodyParts.  When the
			     Button are pressed, it uses the JAF to
			     find a viewer for the BodyPart's content,
			     and displays it in a separate frame (using
			     ComponentFrame).

	TextViewer      =    Uses the content of the DataHandler.  The
			     content will be either a java.lang.String
			     object, or a java.io.InputStream object.
			     Creates a TextArea and sets the text using
			     the String or InputStream.

Support Classes:

	ComponentFrame  =    support class which takes a java.awt.Component
			     and displays it in a Frame.
