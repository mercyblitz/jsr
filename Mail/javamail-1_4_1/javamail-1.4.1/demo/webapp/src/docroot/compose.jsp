<%--
 % @(#)compose.jsp	1.2 07/07/06
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

<%@ page language="java" %>
<%@ page errorPage="errorpage.jsp" %>

<html>
<head>
	<title>JavaMail compose</title>
</head>

<body bgcolor="#ccccff">
<form ACTION="send" METHOD=POST>
<input type="hidden" name="send" value="send">
<p align="center">
<b><font size="4" face="Verdana, Arial, Helvetica">
JavaMail Compose Message</font></b>
<p>
<table border="0" width="100%">
<tr>
<td width="16%" height="22">	
<p align="right">
<b><font face="Verdana, Arial, Helvetica">To:</font></b></td>
<td width="84%" height="22">
<% if (request.getParameter("to") != null) { %>
<input type="text" name="to" value="<%= request.getParameter("to") %>" size="30">
<% } else { %>
<input type="text" name="to" size="30"> 
<% } %>
<font size="1" face="Verdana, Arial, Helvetica">
 (separate addresses with commas)</font></td></tr>
<tr>
<td width="16%"><p align="right">
<b><font face="Verdana, Arial, Helvetica">From:</font></b></td>
<td width="84%">
<input type="text" name="from" size="30"> 
<font size="1" face="Verdana, Arial, Helvetica">
 (separate addresses with commas)</font></td></tr>
<tr>
<td width="16%"><p align="right">
<b><font face="Verdana, Arial, Helvetica">Subject:</font></b></td>
<td width="84%">
<input type="text" name="subject" size="55"></td></tr>
<tr>
<td width="16%">&nbsp;</td>
<td width="84%"><textarea name="text" rows="15" cols="53"></textarea></td></tr>
<tr>
<td width="16%" height="32">&nbsp;</td>
<td width="84%" height="32">
<input type="submit" name="Send" value="Send">
<input type="reset" name="Reset" value="Reset"></td></tr>
</table>
</form>
</body>
</html>

