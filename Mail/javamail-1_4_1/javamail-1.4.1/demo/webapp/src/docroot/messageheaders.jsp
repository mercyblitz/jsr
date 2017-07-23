<%--
 % @(#)messageheaders.jsp	1.5 07/07/06
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

<%@ page language="java" import="demo.MessageInfo" %>
<%@ page errorPage="errorpage.jsp" %>
<%@ taglib uri="http://java.sun.com/products/javamail/demo/webapp" 
    prefix="javamail" %>

<html>
<head>
	<title>JavaMail messageheaders</title>
</head>

<body bgcolor="#ccccff"><hr>
    
<center><font face="Arial,Helvetica" font size="+3">
<b>Folder INBOX</b></font></center><p>
   
<font face="Arial,Helvetica" font size="+3">
<b><a href="logout">Logout</a>
<a href="compose" target="compose">Compose</a>
</b></font>
<hr>
    
<table cellpadding=1 cellspacing=1 width="100%" border=1>
<tr>
<td width="25%" bgcolor="ffffcc">
<font face="Arial,Helvetica" font size="+1">
<b>Sender</b></font></td>
<td width="15%" bgcolor="ffffcc">
<font face="Arial,Helvetica" font size="+1">
<b>Date</b></font></td>
<td bgcolor="ffffcc">
<font face="Arial,Helvetica" font size="+1">
<b>Subject</b></font></td>
</tr>
<javamail:listmessages
 id="msginfo"
 folder="folder">
<%-- from --%>
<tr valign=middle>
<td width="25%" bgcolor="ffffff">
<font face="Arial,Helvetica">
<% if (msginfo.hasFrom()) { %>
<%= msginfo.getFrom() %>
</font>
<% } else { %>
<font face="Arial,Helvetica,sans-serif">
Unknown
<% } %>
</font></td>
<%-- date --%>
<td nowrap width="15%" bgcolor="ffffff">
<font face="Arial,Helvetica">
<%= msginfo.getDate() %>
</font></td>
<%-- subject & link --%>
<td bgcolor="ffffff">
<font face="Arial,Helvetica">
<a href="messagecontent?message=<%= msginfo.getNum() %>">
<% if (msginfo.hasSubject()) { %>
<%=    msginfo.getSubject() %>
<% } else { %>
<i>No Subject</i>
<% } %>
</a>
</font></td>
</tr>
</javamail:listmessages>
</table>
</body>
</html>

