			     JavaMail Servlet
			     ~~~~~~~~~~~~~~~~

Overview:
=========

JavaMailServlet should not be taken as a demo of how to use the Java 
Servlet API. It is rather an example of how the JavaMail APIs could 
be used in a server in a three-tier environment described by the 
following diagram:

	+-----------+        +-----------+        +-----------+
	|   IMAP    |        |           |        |           |
	|  Server   |<-IMAP->| JavaMail  |<-HTTP->|    WWW    |
	+-----------+        | Servlet   |--HTML->|  Browser  |
	|   SMTP    |<-SMTP->|           |        |           |
	|  Server   |        |           |        |           |
	+-----------+        +-----------+        +-----------+


The JavaMailServlet supports the following functionality:
	* login to an IMAP server
	* list all the messages in the INBOX folder
	* view the selected message
	* compose and send a message


Setting up and running the demo:
================================
Note: These instructions explain how to compile and run the servlet 
demo with Java Web Server (JWS). The procedure should be similar with 
other web servers that support the Java Servlet API.

	1. Download the latest version of the Java Web Server from
	   http://www.sun.com/software/jwebserver/index.html and
	   install according to the included documentation. Make
	   sure JWS is listening to requests on port 80 (you may 
	   need to modify it from default port 8080; use the JWS 
	   Admin Applet). Make sure you can load the Java Web 
	   Server welcome page when you connect with your browser 
	   to the machine that the server is installed on. Also,
	   make sure your web browser has cookie support turned on.

	2. Set your classpath to include the following:
	    * mail.jar:		in the JavaMail API distribution
	    * activation.jar:	in the JAF distribution
	    * jws.jar:		in the /lib/ directory in JWS installation

	3. In javamail-1.1/demo/servlet directory, compile the 
	   JavaMailServlet.java file. That produces two class files,
	   JavaMailServlet.class and MailUserData.class. Copy these
	   class files to the /servlets/ directory in the JWS 
	   installation.

	4. Copy the mail.jar and activation.jar to the /lib/
	   directory in the JWS installation.

	5. Copy the JavaMail.html file to the /public_html/
	   directory in the JWS installation.

	6. Restart Java Web Server to pick up the new jar files
	   added to its lib directory. Check again that you can
	   load the default JWS page to verify that the server
	   is working fine.

	7. Using a web browser, go to 
	   http://<hostname>/JavaMail.html and login to a
	   valid IMAP account. From here on, you can view 
	   messages in your INBOX and create and send new 
	   messages.



JavaMailServlet Design:
=======================

The following is a brief description of JavaMailServlet class. It
is not intended to serve as an example of how to develop servlets;
see http://java.sun.com/products/servlet for information on the Java
Servlet API. You may find it useful to refer to JavaMailServlet.java
source while reading this.

The JavaMailServlet uses two primary methods to process all
requests: doPost() and doGet(). doPost() processes submissions
from the login and compose forms. When the user logs in, the
doPost() method gets a JavaMail Session and uses the values
of the "hostname", "username" and "password" parameters to login
to the IMAP Store and get the INBOX Folder. To preserve state
between multiple HTTP requests, the necessary information
(Session, Store, Folder, URLName) are collected in the
MailUserData object which is stored using JWS's Session
technology (don't confuse HttpSession and JavaMail's Session--
they are different). Finally, the doPost() method outputs 
a table listing the INBOX and the number of messages in it.

Clicking on the "INBOX" link calls the doGet() method
which displays a table of message headers. (See the doGet() 
and displayHeaders() methods.)

Clicking on a message generates a request to the servlet with
the message sequence number as a parameter. The doGet() method
receives the request and calls the displayMessage() method 
passing it the message sequence number to display. The 
displayMessage() method first lists the message headers 
by calling the displayMessageHeaders() utility method. 
For text/plain messages, the message content is then displayed
as a string wrapped in HTML <pre>...</pre> tags. For multipart 
messages, each part is passed to the displayPart() method.

There are two displayPart() methods. The one with signature
	displayPart(MailUserData mud, int msgNum, Part part, 
		    int partNum, HttpServletRequest req, 
		    ServletOutputStream out) 
is called from the displayMessage() method for each part. For
any part with text/plain content type, the content is output
as a string wrapped in HTML <pre>...</pre> tags. For other
content types, a link representing the part is displayed,
along with the part filename and description, if available.

Clicking in the part link generates a request to the servlet
with two parameters: the message sequence number and the
part number. The doGet() method interprets the parameters and 
invokes the second displayPart() method with the signature 
	displayPart(MailUserData mud, int msgNum,
	            int partNum, ServletOutputStream out, 
		    HttpServletResponse res) 
This method retrieves the specified part from the message and
streams it to the web browser, preceded by the MIME content type 
of this part. For example, if the part has a MIME type image/gif,
the method will set the servlet response MIME content type to 
"image/gif" and then follow it with the bytes of the actual 
image. This leverages the web browser's content handling
ability to display different media types.

Message composition and sending is very similar to message
viewing. When the "Compose" link is clicked in the headerlist
page, the servlet outputs the HTML source for the compose
form stored in the composeForm variable. The user then fills
in the destination address, subject, text, and presses
send. This sends a POST request to the servlet, which 
invokes the doPost() method. doPost() calls the servlet's 
send() method, which creates a new MimeMessage and fills 
it with data retrieved from the POST request. The message
is then sent to its destination using the Transport.send()
static method.
