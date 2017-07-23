/*
 * @(#)SendTag.java	1.4 07/07/06
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
import java.net.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Custom tag for sending messages.
 */
public class SendTag extends BodyTagSupport {
    private String body;
    private String cc;
    private String host;
    private String recipients;
    private String sender;
    private String subject;

    /**
     * host attribute setter method.
     */
    public void setHost(String host) {
        this.host = host;
    }
    
    /**
     * recipient attribute setter method.
     */
    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    /**
     * sender attribute setter method.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * cc attribute setter method.
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * subject attribute setter method.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Method for processing the end of the tag.
     */
    public int doEndTag() throws JspException {
        Properties props = System.getProperties();
        
        try {
            if (host != null)
                props.put("mail.smtp.host", host);
            else if (props.getProperty("mail.smtp.host") == null)
                props.put("mail.smtp.host", InetAddress.getLocalHost().
                    getHostName());
        } catch (Exception ex) {
            throw new JspException(ex.getMessage());
        }
        Session session = Session.getDefaultInstance(props, null);
		
	Message msg = new MimeMessage(session);
	InternetAddress[] toAddrs = null, ccAddrs = null;

        try {
	    if (recipients != null) {
	        toAddrs = InternetAddress.parse(recipients, false);
	        msg.setRecipients(Message.RecipientType.TO, toAddrs);
	    } else
	        throw new JspException("No recipient address specified");

            if (sender != null)
                msg.setFrom(new InternetAddress(sender));
            else
                throw new JspException("No sender address specified");

	    if (cc != null) {
                ccAddrs = InternetAddress.parse(cc, false);
	        msg.setRecipients(Message.RecipientType.CC, ccAddrs);
	    }

	    if (subject != null)
	        msg.setSubject(subject);

	    if ((body = getBodyContent().getString()) != null)
	        msg.setText(body);
            else
                msg.setText("");

            Transport.send(msg);
	
        } catch (Exception ex) {
            throw new JspException(ex.getMessage());
        }

        return(EVAL_PAGE);
   }
}

