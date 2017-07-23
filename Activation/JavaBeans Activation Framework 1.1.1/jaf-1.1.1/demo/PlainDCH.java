/*
 * @(#)PlainDCH.java	1.10 07/07/13
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
import java.awt.datatransfer.DataFlavor;
import javax.activation.*;


public class PlainDCH implements DataContentHandler {
    /**
     * return the DataFlavors for this <code>DataContentHandler</code>
     * @return The DataFlavors.
     */
    public DataFlavor[] getTransferDataFlavors() { // throws Exception;
	DataFlavor flavors[] = new DataFlavor[2];
	

	try {
	    flavors[0] = new ActivationDataFlavor(Class.forName("java.lang.String"),
					   "text/plain",
					   "text string");
	} catch(Exception e)
	    { System.out.println(e); }

	flavors[1] = new DataFlavor("text/plain", "Plain Text");
	return flavors;
    }
    /**
     * return the Transfer Data of type DataFlavor from InputStream
     * @param df The DataFlavor.
     * @param ins The InputStream corresponding to the data.
     * @return The constructed Object.
     */
    public Object getTransferData(DataFlavor df, DataSource ds) {
	
	// this is sort of hacky, but will work for the
	// sake of testing...
	if(df.getMimeType().equals("text/plain")) {
	    if(df.getRepresentationClass().getName().equals(
					       "java.lang.String")) {
		// spit out String
		StringBuffer buf = new StringBuffer();
		char data[] = new char[1024];
		// InputStream is = null;
		InputStreamReader isr = null;
		int bytes_read = 0;
		int total_bytes = 0;

		try {
		    isr = new InputStreamReader(ds.getInputStream());
		    
// 		    while(is.read(data) > 0)
// 			buf.append(data);

		    while(true){
			bytes_read = isr.read(data);
			if(bytes_read > 0)
			    buf.append(data, total_bytes, bytes_read);
			else
			    break;
			total_bytes += bytes_read;
		    } 
		} catch(Exception e) {}

		return buf.toString();
		
	    }
	    else if(df.getRepresentationClass().getName().equals(
					     "java.io.InputStream")){
		// spit out InputStream
		try {
		    return ds.getInputStream();
		} catch (Exception e) {}
	    }
		
	} 

	    return null;
    }
    
    /**
     *
     */
    public Object getContent(DataSource ds) { // throws Exception;
	StringBuffer buf = new StringBuffer();
	char data[] = new char[1024];
	// InputStream is = null;
	InputStreamReader isr = null;
	int bytes_read = 0;
	int total_bytes = 0;
	
	try {
	    isr = new InputStreamReader(ds.getInputStream());
	    
	    // 		    while(is.read(data) > 0)
	    // 			buf.append(data);
	    
	    while(true){
		bytes_read = isr.read(data);
		if(bytes_read > 0)
		    buf.append(data, total_bytes, bytes_read);
		else
		    break;
		total_bytes += bytes_read;
	    } 
	} catch(Exception e) {}
	
	return buf.toString();
    }
    /**
     * construct an object from a byte stream
     * (similar semantically to previous method, we are deciding
     *  which one to support)
     */
    public void writeTo(Object obj, String mimeTye, OutputStream os) 
	throws IOException {
	// throws Exception;
    }
    
}
