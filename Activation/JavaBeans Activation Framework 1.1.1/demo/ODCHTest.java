/*
 * @(#)ODCHTest.java	1.4 07/07/13
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

public class ODCHTest {
    private FileDataSource fds = null;
    private DataHandler dh = null;
    private DataContentHandlerFactory dchf = null;
    private String str;
    /**
     * main function
     */
    public static void main(String args[]) {
	ODCHTest test = new ODCHTest();
	
	if(args.length != 0) {
	    System.out.println("usage: ODCHTest");
	    System.exit(1);
	}
	
	// first let's get a DataSource

	
	test.doit();
    }

    private void doit() {
	DataFlavor xfer_flavors[];
	Object content = null;

	str = new String("This is a test");

	// now let's create a DataHandler
	dh = new DataHandler(str, "text/plain");
	System.out.println("ODCHTest: DataHandler created with str & text/plain");

	// now lets set a DataContentHandlerFactory
	dchf = new SimpleDCF("text/plain:PlainDCH\n");
	System.out.println("ODCHTest: Simple dchf created");
	
	// now let's set the dchf in the dh
	dh.setDataContentHandlerFactory(dchf);
	System.out.println("ODCHTest: DataContentHandlerFactory set in DataHandler");
	
	// get the dataflavors
	xfer_flavors = dh.getTransferDataFlavors();
	System.out.println("ODCHTest: dh.getTransferDF returned " +
			   xfer_flavors.length + " data flavors.");

	// get the content:
        try {
	   content = dh.getContent();
        } catch (Exception e) { e.printStackTrace(); }

	if(content == null)
	    System.out.println("ODCHTest: no content to be had!!!");
	else {
	    System.out.println("ODCHTest: got content of the following type: " +
			       content.getClass().getName());
	    if(content == str)
		System.out.println("get content works");
	    
	}
    }
	    
}
