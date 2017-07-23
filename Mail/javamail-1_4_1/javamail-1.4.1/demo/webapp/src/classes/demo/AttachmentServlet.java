/*
 * @(#)AttachmentServlet.java	1.4 07/07/06
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
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * This servlet gets the input stream for a given msg part and 
 * pushes it out to the browser with the correct content type.
 * Used to display attachments and relies on the browser's
 * content handling capabilities.
 */
public class AttachmentServlet extends HttpServlet {

    /**
     * This method handles the GET requests from the client.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
      
        HttpSession session = request.getSession();
        ServletOutputStream out = response.getOutputStream();
        int msgNum = Integer.parseInt(request.getParameter("message"));
        int partNum = Integer.parseInt(request.getParameter("part"));
        MailUserBean mailuser = (MailUserBean)session.getAttribute("mailuser");

        // check to be sure we're still logged in
        if (mailuser.isLoggedIn()) {
            try {
                Message msg = mailuser.getFolder().getMessage(msgNum);

                Multipart multipart = (Multipart)msg.getContent();
	        Part part = multipart.getBodyPart(partNum);
                
                String sct = part.getContentType();
	        if (sct == null) {
		    out.println("invalid part");
		    return;
	        }
	        ContentType ct = new ContentType(sct);

	        response.setContentType(ct.getBaseType());
	        InputStream is = part.getInputStream();
	        int i;
	        while ((i = is.read()) != -1)
		    out.write(i);
	        out.flush();
	        out.close();

            } catch (MessagingException ex) {
                throw new ServletException(ex.getMessage());
            }
        } else {
            getServletConfig().getServletContext().
                getRequestDispatcher("/index.html").
                forward(request, response);
        }
    }   
}

