			README
			======

	    JavaMail(TM) API 1.4.1 release
	    ------------------------------

Welcome to the JavaMail API 1.4.1 release!  This release includes
versions of the JavaMail API implementation, IMAP, SMTP, and POP3
service providers, some examples, and documentation for the JavaMail
API.

Please see the FAQ at http://java.sun.com/products/javamail/FAQ.html

JDK Version notes
-----------------

The JavaMail API supports JDK 1.4 or higher.  Note that we have
currently tested this implementation with JDK 1.4, 1.5, and 1.6.

While JavaMail will work with JAF 1.0.2, we recommend the use of JAF 1.1
or newer.  JAF 1.1.1 is currently the newest version.  Note that JAF 1.1
is included in JDK 1.6 and JAF 1.1.1 will be included in a future JDK 1.6
update release.


Protocols supported
-------------------

This release supports the following Internet standard mail protocols:

    IMAP - a message Store protocol, for reading messages from a server
    POP3 - a message Store protocol, for reading messages from a server
    SMTP - a message Transport protocol, for sending messages to a server

The following table lists the names of the supported protocols (as used
in the JavaMail API) and their capabilities:

	Protocol	Store or	Uses	Supports
	Name		Transport?	SSL?	STARTTLS?
	-------------------------------------------------
	imap		Store		No	Yes
	imaps		Store		Yes	Yes
	pop3		Store		No	No
	pop3s		Store		Yes	No
	smtp		Transport	No	Yes
	smtps		Transport	Yes	Yes

See our web page at http://java.sun.com/products/javamail for the
latest information on third party protocol providers.


Contents
--------

    Included in this release are the following:

    README.txt 		this file
    LICENSE.txt		Software license
    NOTES.txt		Notes, issues and known bugs
    SSLNOTES.txt	Notes on using SSL/TLS with JavaMail
    CHANGES.txt		Changes since the previous release
    COMPAT.txt		Important notes about compatibility
    mail.jar		The JavaMail API and all service providers,
			most users need *only* this jar file
    lib/mailapi.jar	The JavaMail API with no service providers
    lib/imap.jar	The IMAP service provider
    lib/smtp.jar	The SMTP service provider
    lib/pop3.jar	The POP3 service provider
    lib/dsn.jar		multipart/report DSN message support

    docs/JavaMail-1.1-changes.txt
			Description of the new APIs that were added in
			JavaMail API 1.1
    docs/JavaMail-1.2-changes.txt
			Description of the new APIs that were added in
			JavaMail API 1.2
    docs/JavaMail-1.3-changes.txt
			Description of the new APIs that were added in
			JavaMail API 1.3
    docs/JavaMail-1.4-changes.txt
			Description of the new APIs that were added in
			JavaMail API 1.4
    docs/JavaMail-1.2.ps	
			Postscript version of the JavaMail API 1.2 specification
    docs/JavaMail-1.2.pdf	
			PDF version of the JavaMail API 1.2 specification
    docs/Providers.ps
			Postscript version of the JavaMail Service Provider
			guide.
    docs/Providers.pdf
			PDF version of the JavaMail Service Provider guide.

    docs/javadocs/	The JavaMail API javadocs

    demo/README.txt	Instructions for running the demo programs
    demo/CRLFOutputStream.java
			demo OutputStream filter to convert all line terminators
			to CRLF
    demo/NewlineOutputStream.java
			demo OutputStream filter to convert all line terminators
			to platform's line terminator
    demo/copier.java	demo program to copy mail between folders
    demo/folderlist.java demo program to list subfolders
    demo/monitor.java	demo program to monitor for new mail
    demo/mover.java	demo program to move mail between folders
    demo/msgmultisendsample.java
			demo program to send a sample multipart message
    demo/msgsend.java	demo program to send a single part text message
			to a specified recipient (ala /bin/mail)
    demo/msgsendsample.java
			demo program to send a sample single part text message
    demo/msgshow.java	demo program to read messages from an IMAP store
    demo/namespace.java	demo program that illustrates use of namespace APIs
    demo/populate.java	demo program that copies entire folder hierarchies
    demo/registry.java	demo program that illustrates the registry
    demo/search.java	demo program to search folders
    demo/sendfile.java	demo program to send a file as an attachment
    demo/sendhtml.java  demo program to send html mail
    demo/smtpsend.java	demo program to illustrate handling SMTP error codes
    demo/transport.java	demo program to illustrate use of Transport
    demo/uidmsgshow.java
			demo program to read messages from an IMAP store
    demo/client/	source files for cool demo program that implements 
			a simple mail reader. (Uses Swing)
    demo/client/README.txt
			README file for running the simple mail-reader demo
    demo/servlet/	source for a simple servlet that allows using a
			web browser to read and send mail
    demo/servlet/README.txt
			README file that describes the JavaMailServlet


Requirements
------------

Note that the JavaMail API requires the JavaBeans(TM) Activation
Framework package to be installed as well.  Download the latest version
of the JavaBeans Activation Framework from

	http://java.sun.com/beans/glasgow/jaf.html

and install it in a suitable location.


Installation
------------

  UNIX/Linux
  ----------

  1. Unzip the javamail-1_4_1.zip archive. (you may have already done this)

  2. Set your CLASSPATH to include the "mail.jar" file obtained from 
     the download, as well as the current directory. 

     Assuming you unzipped javamail-1_4_1.zip in /u/me/download/ the
     following would work:

      export CLASSPATH=$CLASSPATH:/u/me/download/javamail-1.4.1/mail.jar:.

    Also include the "activation.jar" file that you obtained from
    downloading the Java Activation Framework, in your CLASSPATH.
    For example:

      export CLASSPATH=$CLASSPATH:/u/me/download/activation/activation.jar

    
  3. Go to the demo directory

  4. Compile any demo using your java compiler. For example:

      javac msgshow.java

  5. Run the demo. The '-' option lists the required and optional
     command-line options to successfully run any demo. For example:

      java msgshow -
    
    lists the available options. And

      java msgshow -T imap -H <mailserver> -U <username> -P <passwd> -f INBOX 5
    
    uses the IMAP protocol to display message number 5 from your INBOX.

  (Additional instructions on how to run the simple mail reader demo 
  and servlet demo are provided in demo/client/README.txt and
  demo/servlet/README.txt, respectively.)


  Windows
  -------

  1. Unzip the javamail-1_4_1.zip archive. (you may have already done this)

  2. Set your CLASSPATH to include the "mail.jar" file obtained from 
     the download, as well as the current directory. 

     Assuming you unzipped javamail-1_4_1.zip in c:\download the
     following would work:
     
      set CLASSPATH=%CLASSPATH%;c:\download\javamail-1.4.1\mail.jar;.

    Also include the "activation.jar" file that you obtained from
    downloading the JavaBeans Activation Framework, in your CLASSPATH.

      set CLASSPATH=%CLASSPATH%;c:\download\activation\activation.jar
    
  3. Go to the demo directory

  4. Compile any demo using your java compiler. For example:

      javac msgshow.java

  5. Run the demo. The '-' option lists the required and optional
     command-line options to successfully run any demo. For example:

      java msgshow -

    lists the available options. And

      java msgshow -T imap -H <mailserver> -U <username> -P <passwd> -f INBOX 5
    
    uses the IMAP protocol to display message number 5 from your INBOX.


  (Additional instructions on how to run the simple mail reader demo 
  and servlet demo are provided in demo/client/README.txt and
  demo/servlet/README.txt, respectively.)


Problems?
---------

Our web page at http://java.sun.com/products/javamail has a pointer
to the FAQ that includes information on protocols supported,
installation problems, etc.  You'll also find information on how to
subscribe to our discussion-list or announcement-list for announcements 
of future JavaMail API releases.

See the NOTES.txt file for information on how to report bugs.

Enjoy!

The JavaMail API Team
