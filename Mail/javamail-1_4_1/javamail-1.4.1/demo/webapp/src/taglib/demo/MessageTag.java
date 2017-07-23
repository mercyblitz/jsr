/*
 * @(#)MessageTag.java	1.5 07/07/06
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

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Custom tag for retrieving a message.
 */
public class MessageTag extends TagSupport {
    private String folder;
    private String session;
    private int num = 1;

    /**
     * folder attribute setter method.
     */
    public String getFolder() {
        return folder;
    }
    
    /**
     * num attribute getter method.
     */
    public String getNum() {
        return Integer.toString(num);
    }
    
    /**
     * session attribute getter method.
     */
    public String getSession() {
        return session;
    }
    
    /**
     * folder setter method.
     */
    public void setFolder(String folder) {
        this.folder = folder;
    }

    /**
     * num attribute setter method.
     */
    public void setNum(String num) {
        this.num = Integer.parseInt(num);
    }
    
    /**
     * session attribute setter method.
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Method for processing the start of the tag.
     */
    public int doStartTag() throws JspException {
        MessageInfo messageinfo = new MessageInfo();
        try {
	    Folder f = (Folder)pageContext.getAttribute(
		getFolder(), PageContext.SESSION_SCOPE);
            Message message = f.getMessage(num);
            messageinfo.setMessage(message);
            pageContext.setAttribute(getId(), messageinfo);
        } catch (Exception ex) {
            throw new JspException(ex.getMessage());
        }
 
        return SKIP_BODY;
   }
}

