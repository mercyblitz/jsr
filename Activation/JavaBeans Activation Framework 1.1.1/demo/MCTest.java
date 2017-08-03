/*
 * @(#)MCTest.java	1.9 07/07/13
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
import java.beans.*;
import com.sun.activation.registries.*;
import javax.activation.*;

public class MCTest {
    static MailcapCommandMap mcf = null;

    public static void main(String args[]) {
	
	try {
	    if (args.length == 0)
		mcf = new MailcapCommandMap();
	    else
		mcf = new MailcapCommandMap(args[0]);
	} catch (Exception e){ 
	    e.printStackTrace();
	    System.exit(1);
	}

	CommandInfo cmdinfo[] = mcf.getAllCommands("text/plain");
        System.out.print("Are there any commands for text/plain?");

	if (cmdinfo != null) {
	    System.out.println("number of cmds = " + cmdinfo.length);
	    System.out.println("now try an individual cmd");
	    CommandInfo info = mcf.getCommand("text/plain", "view");
	    if (info != null) {
		System.out.println("Got command...");
	    } else {
		System.out.println("no cmds");
	    }

	    mcf.addMailcap("text/plain;; x-java-flobotz=com.sun.activation.flobotz\n");	
	    //	    System.out.println("...dome");
	    if (cmdinfo != null) {
		cmdinfo = mcf.getAllCommands("text/plain");
		System.out.println("now we have cmds = " + cmdinfo.length);
		
	    }	

        } else {
	    System.out.println("NO CMDS AT ALL!");
	}
    }
}
