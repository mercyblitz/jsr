/*
 * @(#)copier.java	1.12 07/07/06
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

/**
 *
 * @version	1.12, 07/07/06
 * @author	Christopher Cotton
 */

import javax.mail.*;

/**
 * copier will copy a specified number of messages from one folder
 * to another folder. it demonstrates how to use the JavaMail APIs
 * to copy messages.<p>
 *
 * Usage for copier: copier <i>store urlname</i> 
 * <i>src folder</i> <i>dest folder</i> <i>start msg #</i> <i>end msg #</i><p>
 *
 */

public class copier {

  public static void main(String argv[]) {
      boolean debug = false;// change to get more errors
      
      if (argv.length != 5) {
	  System.out.println( "usage: copier <urlname> <src folder>" +
			      "<dest folder> <start msg #> <end msg #>");
	  return;
      }

      try {
	  URLName url = new URLName(argv[0]);
	  String src = argv[1];	// source folder
	  String dest = argv[2];	// dest folder
	  int start = Integer.parseInt(argv[3]);  // copy from message #
	  int end = Integer.parseInt(argv[4]);	// to message #

	  // Get the default Session object

	  Session session = Session.getInstance(System.getProperties(), null);
	  // session.setDebug(debug);

	  // Get a Store object that implements 
	  // the protocol.
	  Store store = session.getStore(url);
	  store.connect();
	  System.out.println("Connected...");

	  // Open Source Folder
	  Folder folder = store.getFolder(src);
	  folder.open(Folder.READ_WRITE);
	  System.out.println("Opened source...");	  

	  if (folder.getMessageCount() == 0) {
		System.out.println("Source folder has no messages ..");
		folder.close(false);
		store.close();
	  }

	  // Open destination folder, create if needed 
	  Folder dfolder = store.getFolder(dest);
	  if (!dfolder.exists()) // create
	      dfolder.create(Folder.HOLDS_MESSAGES);

	  Message[] msgs = folder.getMessages(start, end);
	  System.out.println("Got messages...");	  

	  // Copy messages into destination, 
	  folder.copyMessages(msgs, dfolder);
	  System.out.println("Copied messages...");	  

	  // Close the folder and store
	  folder.close(false);
	  store.close();
	  System.out.println("Closed folder and store...");
	  
      } catch (Exception e) {
	  e.printStackTrace();
      }

      System.exit(0);
  }

}
