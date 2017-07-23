/*
 * @(#)ListAttachmentsTag.java	1.4 07/07/06
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
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Custom tag for listing message attachments. The scripting variable is only
 * within the body of the tag.
 */
public class ListAttachmentsTag extends BodyTagSupport {
    private String messageinfo;
    private int partNum = 1;
    private int numParts = 0;
    private AttachmentInfo attachmentinfo;
    private MessageInfo messageInfo;
    private Multipart multipart;

    /**
     * messageinfo attribute getter method.
     */
    public String getMessageinfo() {
        return messageinfo;
    }
    
    /**
     * messageinfo attribute setter method.
     */
    public void setMessageinfo(String messageinfo) {
        this.messageinfo = messageinfo;
    }

    /**
     * Method for processing the start of the tag.
     */
    public int doStartTag() throws JspException {
        messageInfo = (MessageInfo)pageContext.getAttribute(getMessageinfo());
        attachmentinfo = new AttachmentInfo();
        
        try {
            multipart = (Multipart)messageInfo.getMessage().getContent();
            numParts = multipart.getCount();
        } catch (Exception ex) {
            throw new JspException(ex.getMessage());
        }

        getPart();

        return BodyTag.EVAL_BODY_TAG;
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
       
        partNum++;
        if (partNum < numParts) {
            getPart();
            return BodyTag.EVAL_BODY_TAG;
        } else {
            return BodyTag.SKIP_BODY;
        }
    }
    
    /**
     * Helper method for retrieving message parts.
     */
    private void getPart() throws JspException {
        try {
            attachmentinfo.setPart(partNum, multipart.getBodyPart(partNum));
            pageContext.setAttribute(getId(), attachmentinfo);
        } catch (Exception ex) {
            throw new JspException(ex.getMessage());
        }
    }
}

