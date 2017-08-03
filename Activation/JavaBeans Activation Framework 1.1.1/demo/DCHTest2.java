/*
 * @(#)DCHTest2.java	1.7 07/07/13
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

import java.io.*;
import javax.activation.*;
import java.awt.datatransfer.*;

public class DCHTest2 {
    private FileDataSource fds = null;
    private MailcapCommandMap cmdMap = null;
    private DataHandler dh = null;
    private DataContentHandlerFactory dchf = null;
    /**
     * main function
     */
    public static void main(String args[]) {
	DCHTest2 test = new DCHTest2();
	
	if(args.length < 2) {
	    System.out.println("usage: DCHTest2 file.txt mailcap");
	    System.exit(1);
	}
	
	// first let's get a DataSource

	test.setFile(args[0]);
	
	test.setMailcap(args[1]);
	test.doit();
    }

    private void doit() {
	DataFlavor xfer_flavors[];
	Object content = null;

	// now let's create a DataHandler
	dh = new DataHandler(fds);
        dh.setCommandMap(cmdMap);
	System.out.println("DCHTest2: DataHandler created");

	// get the dataflavors
	xfer_flavors = dh.getTransferDataFlavors();
	System.out.println("DCHTest2: dh.getTransferDF returned " +
			   xfer_flavors.length + " data flavors.");

	// get the content:
	try {
	    content = dh.getContent();
        } catch (Exception e) { e.printStackTrace(); }
	if(content == null)
	    System.out.println("DCHTest2: no content to be had!!!");
	else
	    System.out.println("DCHTest2: got content of the following type: " +
			       content.getClass().getName());
	
    }

    /**
     * set the file
     */
    public void setFile(String filename) {
	fds = new FileDataSource(filename);
	System.out.println("DCHTest2: FileDataSource created");
	if(!fds.getContentType().equals("text/plain")) {
	    System.out.println("Type must be text/plain");
	    System.exit(1);
	}
    }

   /**
    * set the mailcap file in the CMD Map
    */
   public void setMailcap(String mailcap) {

       try {
	   cmdMap = new MailcapCommandMap(mailcap);
       } catch(Exception e){
	   e.printStackTrace();
	   System.exit(1);
       }
   }

	    
}


