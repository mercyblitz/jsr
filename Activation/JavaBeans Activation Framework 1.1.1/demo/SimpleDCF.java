/*
 * @(#)SimpleDCF.java	1.4 07/07/13
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

import javax.activation.*;
import java.util.StringTokenizer;
import java.util.Hashtable;

public class SimpleDCF implements DataContentHandlerFactory {
    Hashtable entry_hash = new Hashtable();
    /**
     * the constructor, takes a list of classes as an argument in the
     * form:
     * <mimetype>:<class name>\n
     *
     * For Example:
     *
     * application/x-wombat:com.womco.WombatDCH
     * text/plain:com.textco.TextDCH
     *
     */
    public SimpleDCF(String entries) {
	StringTokenizer tok = new StringTokenizer(entries);

	String entry;
	System.out.println("SimpleDCH: new SimpleDCF being created");

	// parse the string
	while(tok.hasMoreTokens()) {
	    int colon;

	    entry = tok.nextToken();
	    System.out.println("full entry = " + entry);

	    // parse out the fields
	    colon = entry.indexOf(':');
	    VectorEntry ve = new VectorEntry(entry.substring(0,colon),
					     entry.substring(colon + 1, 
							     entry.length()));
	    System.out.println("adding element = " + ve);
	    entry_hash.put(ve.getMimeType(),ve);
	}
    }

    /**
     * implement the factor interface
     */
    public DataContentHandler createDataContentHandler(String mimeType){
	DataContentHandler dch = null;

	System.out.print("SimpleDCF: trying to create a DCH");

	VectorEntry ve = (VectorEntry)entry_hash.get(mimeType);
	if(ve != null) {
	    System.out.print("...found token");
	    try { 
		
		dch = (DataContentHandler)Class.forName(
					ve.getClassName()).newInstance();
		if(dch == null)
		    System.out.println("...FAILED!!!");
		else
		    System.out.println("...SUCCESS!!!");

	    } catch(Exception e) {
		System.out.println(e);
	    }
	}
	return dch;
    }
}

class VectorEntry {
    private String mimeType;
    private String className;

    public VectorEntry(String mimeType, String className) {
	this.mimeType = mimeType;
	this.className = className;
    }
    
    public String getMimeType() { return mimeType; }
    public String getClassName() { return className; }
    public String toString() { 
	return new String("type: " + mimeType + " class name: " + className);
    }

}
