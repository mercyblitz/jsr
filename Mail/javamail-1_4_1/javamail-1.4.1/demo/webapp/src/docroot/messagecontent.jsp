<%--
 % @(#)messagecontent.jsp	1.5 07/07/06
 %
 % Copyright 2001-2007 Sun Microsystems, Inc. All Rights Reserved.
 %
 % Redistribution and use in source and binary forms, with or without
 % modification, are permitted provided that the following conditions
 % are met:
 %
 %   - Redistributions of source code must retain the above copyright
 %     notice, this list of conditions and the following disclaimer.
 %
 %   - Redistributions in binary form must reproduce the above copyright
 %     notice, this list of conditions and the following disclaimer in the
 %     documentation and/or other materials provided with the distribution.
 %
 %   - Neither the name of Sun Microsystems nor the names of its
 %     contributors may be used to endorse or promote products derived
 %     from this software without specific prior written permission.
 %
 % THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 % IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 % THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 % PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 % CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 % EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 % PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 % PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 % LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 % NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 % SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 %
--%>

<%@ page language="java" import="demo.MessageInfo, demo.AttachmentInfo" %>
<%@ page errorPage="errorpage.jsp" %>
<%@ taglib uri="http://java.sun.com/products/javamail/demo/webapp" 
    prefix="javamail" %>
 

<html>
<head>
    <title>JavaMail messagecontent</title>
</head>

<javamail:message 
id="msginfo"
folder="folder"
num="<%= request.getParameter(\"message\") %>" 
/>

<body bgcolor="#ccccff">
<center><font face="Arial,Helvetica" font size="+3">
<b>Message<sp> 
<sp>in folder /INBOX</b></font></center><p>

<%-- first, display this message's headers --%>
<b>Date:</b>
<%= msginfo.getSentDate() %>
<br>
<% if (msginfo.hasFrom()) { %>
<b>From:</b>
<a href="compose?to=<%= msginfo.getReplyTo() %>"
    target="reply<%= msginfo.getNum() %>">
<%= msginfo.getFrom() %>
</a>
<br>
<% } %>

<% if (msginfo.hasTo()) { %>
<b>To:</b>
<%= msginfo.getTo() %>
<br>
<% } %>   

<% if (msginfo.hasCc()) { %>
<b>CC:</b>
<%= msginfo.getCc() %>
<br>
<% } %>

<b>Subject:</b>
<% if (msginfo.hasSubject()) { %>
<%= msginfo.getSubject() %>
<% } %>
<br>
<pre>
<%= msginfo.getBody() %>
</pre>
<% if (msginfo.hasAttachments()) { %>
<javamail:listattachments
 id="attachment"
 messageinfo="msginfo">
<p><hr>
<b>Attachment Type:</b>
<%= attachment.getAttachmentType() %>
<br>
<% if (attachment.hasMimeType("text/plain") && 
       attachment.isInline()){ %>
<pre>
<%= attachment.getContent() %>
</pre>
<% } else { %>
<b>Filename:</b>
<%= attachment.getFilename() %>
<br>
<b>Description:</b>
<%= attachment.getDescription() %>
<br>
<a href="attachment?message=
<%= msginfo.getNum() %>&part=<%= attachment.getNum() %>">
Display Attachment</a>
<% } %>
</javamail:listattachments>
<% } %>
</body>
</html>

