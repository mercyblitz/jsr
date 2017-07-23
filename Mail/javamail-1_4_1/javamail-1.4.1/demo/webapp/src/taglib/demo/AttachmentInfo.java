/*
 * @(#)AttachmentInfo.java	1.4 07/07/06
 *
 * Copyright 2001-2007 Sun Microsystems, Inc. All Rights Reserved.
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

package demo;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Used to store attachment information.
 */
public class AttachmentInfo {
    private Part part;
    private int num;
    

    /**
     * Returns the attachment's content type.
     */
    public String getAttachmentType() throws MessagingException {
        String contentType;
        if ((contentType = part.getContentType()) == null)
            return "invalid part";
        else
	    return contentType;
    }

    /**
     * Returns the attachment's content (if it is plain text).
     */
    public String getContent() throws java.io.IOException, MessagingException {
        if (hasMimeType("text/plain"))
            return (String)part.getContent();
        else
            return "";
    }
    
    /**
     * Returns the attachment's description.
     */
    public String getDescription() throws MessagingException {
        String description;
        if ((description = part.getDescription()) != null)
            return description;
        else 
            return "";
    }
    
    /**
     * Returns the attachment's filename.
     */
    public String getFilename() throws MessagingException {
        String filename;
        if ((filename = part.getFileName()) != null)
            return filename;
        else
            return "";
    }

    /**
     * Returns the attachment number.
     */
    public String getNum() {
        return (Integer.toString(num));
    }
    
    /**
     * Method for checking if the attachment has a description.
     */
    public boolean hasDescription() throws MessagingException {
        return (part.getDescription() != null);
    }
    
    /**
     * Method for checking if the attachment has a filename.
     */
    public boolean hasFilename() throws MessagingException {
        return (part.getFileName() != null);
    }
    
    /**
     * Method for checking if the attachment has the desired mime type.
     */
    public boolean hasMimeType(String mimeType) throws MessagingException {
        return part.isMimeType(mimeType);
    }
    
    /**
     * Method for checking the content disposition.
     */
    public boolean isInline() throws MessagingException {
        if (part.getDisposition() != null)
            return part.getDisposition().equals(Part.INLINE);
        else
            return true;
    }
    
    /**
     * Method for mapping a message part to this AttachmentInfo class.
     */
    public void setPart(int num, Part part) 
        throws MessagingException, ParseException {
            
        this.part = part;
        this.num = num;
    }
}

