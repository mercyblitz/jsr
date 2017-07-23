/*
 * @(#)MailUserBean.java	1.8 07/07/06
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
import javax.naming.*;

/**
 * This JavaBean is used to store mail user information.
 */
public class MailUserBean {
    private Folder folder;
    private String hostname;
    private String username;
    private String password;
    private Session session;
    private Store store;
    private URLName url;
    private String protocol = "imap";
    private String mbox = "INBOX";	

    public MailUserBean(){}

    /**
     * Returns the javax.mail.Folder object.
     */
    public Folder getFolder() {
        return folder;
    }
    
    /**
     * Returns the number of messages in the folder.
     */
    public int getMessageCount() throws MessagingException {
        return folder.getMessageCount();
    }

    /**
     * hostname getter method.
     */
    public String getHostname() {
        return hostname;
    }
    
    /**
     * hostname setter method.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
	
    /**
     * username getter method.
     */
    public String getUsername() {
        return username;
    }

    /**
     * username setter method.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * password getter method.
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter method.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * session getter method.
     */
    public Session getSession() {
        return session;
    }

    /**
     * session setter method.
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * store getter method.
     */
    public Store getStore() {
        return store;
    }

    /**
     * store setter method.
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * url getter method.
     */
    public URLName getUrl() {
        return url;
    }

    /**
     * Method for checking if the user is logged in.
     */
    public boolean isLoggedIn() {
        return store.isConnected();
    }
      
    /**
     * Method used to login to the mail host.
     */
    public void login() throws Exception {
        url = new URLName(protocol, getHostname(), -1, mbox, 
                          getUsername(), getPassword());
	/*
	 * First, try to get the session from JNDI,
	 * as would be done under J2EE.
	 */
	try {
	    InitialContext ic = new InitialContext();
	    Context ctx = (Context)ic.lookup("java:comp/env");
	    session = (Session)ctx.lookup("MySession");
	} catch (Exception ex) {
	    // ignore it
	}

	// if JNDI fails, try the old way that should work everywhere
	if (session == null) {
	    Properties props = null;
	    try {
		props = System.getProperties();
	    } catch (SecurityException sex) {
		props = new Properties();
	    }
	    session = Session.getInstance(props, null);
	}
        store = session.getStore(url);
        store.connect();
        folder = store.getFolder(url);
        
        folder.open(Folder.READ_WRITE);
    }

    /**
     * Method used to login to the mail host.
     */
    public void login(String hostname, String username, String password) 
        throws Exception {
            
        this.hostname = hostname;
        this.username = username;
        this.password = password;
	    
        login();
    }

    /**
     * Method used to logout from the mail host.
     */
    public void logout() throws MessagingException {
        folder.close(false);
        store.close();
        store = null;
        session = null;
    }
}

