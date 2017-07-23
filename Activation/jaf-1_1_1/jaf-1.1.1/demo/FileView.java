/*
 * @(#)FileView.java	1.7 07/07/13
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

import java.awt.*;
import java.beans.*;
import java.net.*;
import javax.activation.*;

public class FileView {
    private Frame frame;

    public static void main(String args[]) throws Exception {
        FileView fv = new FileView();
        if (args.length == 0) {
            System.out.println("usage: FileView  file.txt");
            System.exit(1);
        }
        fv.view(args[0]);
    }

    private void view(String filename) throws Exception {
  	FileDataSource fds = new FileDataSource(filename); 
  	DataHandler dh = new DataHandler(fds); 
	// comment out previous two lines, and uncomment next
	// line and pass in a URL on the command line.
	// DataHandler dh = new DataHandler(new URL(filename));

	CommandInfo bi = dh.getCommand("view");
	
	if (bi == null) {
	    System.out.println("no viewer found, exiting");
	    System.exit(1);
	}

	frame = new Frame("Viewer");
	frame.add((Component)dh.getBean(bi));
	frame.setSize(new Dimension(400,300));
	frame.show();
    }
}
