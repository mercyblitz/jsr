/*
 * @(#)ListMessagesTag.java	1.6 07/07/06
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
import javax.mail.search.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Custom tag for listing messages. The scripting variable is only
 * within the body of the tag.
 */
public class ListMessagesTag extends BodyTagSupport {
    private String folder;
    private String session;
    private int msgNum = 0;
    private int messageCount = 0;
    private Message message;
    private Message[] messages;
    private MessageInfo messageinfo;
    
    /**
     * folder attribute getter method.
     */
    public String getFolder() {
        return folder;
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
     * session attribute setter method.
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Method for processing the start of the tag.
     */
    public int doStartTag() throws JspException {
        messageinfo = new MessageInfo();
       
        try {
            Folder folder = (Folder)pageContext.getAttribute(
		getFolder(), PageContext.SESSION_SCOPE);
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.DELETED), false);
            messages = folder.search(ft);
            messageCount = messages.length;
	    msgNum = 0;
        } catch (Exception ex) {
            throw new JspException(ex.getMessage());
        }

        if (messageCount > 0) {
            getMessage();
            return BodyTag.EVAL_BODY_TAG;
        } else
            return BodyTag.SKIP_BODY;
    }
   
    /**
     * Method for processing the body content of the tag.
     */
    public int doAfterBody() throws JspException {
        
        BodyContent body = getBodyContent();
        try {
            body.writeOut(getPreviousOut());
        } catch (IOException e) {
            throw new JspTagException("IterationTag: " + e.getMessage());
        }
        
        // clear up so the next time the body content is empty
        body.clearBody();
       
        if (msgNum < messageCount) {
            getMessage();
            return BodyTag.EVAL_BODY_TAG;
        } else {
            return BodyTag.SKIP_BODY;
        }
    }
    
    /**
     * Helper method for retrieving messages.
     */
    private void getMessage() throws JspException {
        message = messages[msgNum++];
        messageinfo.setMessage(message);
        pageContext.setAttribute(getId(), messageinfo);
    }
}

