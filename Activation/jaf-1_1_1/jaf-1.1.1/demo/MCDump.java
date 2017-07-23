/*
 * @(#)MCDump.java	1.3 07/07/13
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

/**
 * Dump out everything we know about a MailcapCommandMap.
 */
public class MCDump {
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

	String[] types = mcf.getMimeTypes();
	if (types == null) {
	    System.out.println("No known MIME types");
	    System.exit(0);
	} else {
	    System.out.println("Known MIME types:");
	    for (int i = 0; i < types.length; i++)
		System.out.println("\t" + types[i]);
	}

	System.out.println();
	System.out.println("All commands for each MIME type:");
	for (int i = 0; i < types.length; i++) {
	    System.out.println("    " + types[i]);
	    CommandInfo[] cmdinfo = mcf.getAllCommands(types[i]);
	    if (cmdinfo == null) {
		System.out.println("\tNONE");
	    } else {
		for (int k = 0; k < cmdinfo.length; k++)
		    System.out.println("\t" + cmdinfo[k].getCommandName() +
			": " + cmdinfo[k].getCommandClass());
	    }
	}

	System.out.println();
	System.out.println("Preferred commands for each MIME type:");
	for (int i = 0; i < types.length; i++) {
	    System.out.println("    " + types[i]);
	    CommandInfo[] cmdinfo = mcf.getPreferredCommands(types[i]);
	    if (cmdinfo == null) {
		System.out.println("\tNONE");
	    } else {
		for (int k = 0; k < cmdinfo.length; k++)
		    System.out.println("\t" + cmdinfo[k].getCommandName() +
			": " + cmdinfo[k].getCommandClass());
	    }
	}

	System.out.println();
	System.out.println("Native commands for each MIME type:");
	for (int i = 0; i < types.length; i++) {
	    System.out.println("    " + types[i]);
	    String[] cmds = mcf.getNativeCommands(types[i]);
	    if (cmds.length == 0) {
		System.out.println("\tNONE");
	    } else {
		for (int k = 0; k < cmds.length; k++)
		    System.out.println("\t" + cmds[k]);
	    }
	}
    }
}
