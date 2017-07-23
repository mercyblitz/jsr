/*
 * @(#)DHURL.java	1.5 07/07/13
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

import java.net.*;
import java.io.*;
import javax.activation.*;

public class DHURL {
    URL url = null;
    DataHandler dh = null;
    
    public static void main(String args[]){
        DHURL test = new DHURL();

        if(args.length == 0) {
            System.out.println("usage: DHURL url");
            System.exit(1);
        }

        test.setURL(args[0]);

        test.doit();

    }

    public void setURL(String url) {
	
	try {
	    this.url = new URL(url);
	} catch(MalformedURLException e) {
	    e.printStackTrace();
	    System.out.println("malformed URL!!!");
	    System.exit(1);
	}

    }
    
    public void doit() {
	System.out.print("Creating DataHandler...");
	dh = new DataHandler(url);
	System.out.println("...done.");

	System.out.println("The MimeType of the DH : " +
			   dh.getContentType());
	try {
	InputStream is = dh.getInputStream();
	if(is != null)
	    System.out.println("got an inputstream");
	} catch(Exception e) {
	    e.printStackTrace();
	}

	
    }
	
    
}
