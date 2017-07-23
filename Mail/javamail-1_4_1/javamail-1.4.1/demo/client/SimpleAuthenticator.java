/*
 * @(#)SimpleAuthenticator.java	1.8 07/07/06
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All Rights Reserved.
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
/*
 * @(#)SimpleAuthenticator.java	1.8 07/07/06
 *
 * Copyright (c) 1996-2007 by Sun Microsystems, Inc.
 * All Rights Reserved.
 */

import javax.mail.*;
import java.net.InetAddress;
import java.awt.*;
import javax.swing.*;

/**
 * Simple Authenticator for requesting password information.
 *
 * @version	1.8, 07/07/06
 * @author	Christopher Cotton
 * @author	Bill Shannon
 */

public class SimpleAuthenticator extends Authenticator {

    Frame frame;
    String username;
    String password;

    public SimpleAuthenticator(Frame f) {
	this.frame = f;
    }

    protected PasswordAuthentication getPasswordAuthentication() {

	// given a prompt?
	String prompt = getRequestingPrompt();
	if (prompt == null)
	    prompt = "Please login...";

	// protocol
	String protocol = getRequestingProtocol();
	if (protocol == null)
	    protocol = "Unknown protocol";

	// get the host
	String host = null;
	InetAddress inet = getRequestingSite();
	if (inet != null)
	    host = inet.getHostName();
	if (host == null)
	    host = "Unknown host";

	// port
	String port = "";
	int portnum = getRequestingPort();
	if (portnum != -1)
	    port = ", port " + portnum + " ";

	// Build the info string
	String info = "Connecting to " + protocol + " mail service on host " +
								host + port;

	//JPanel d = new JPanel();
	// XXX - for some reason using a JPanel here causes JOptionPane
	// to display incorrectly, so we workaround the problem using
	// an anonymous JComponent.
	JComponent d = new JComponent() { };

	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	d.setLayout(gb);
	c.insets = new Insets(2, 2, 2, 2);

	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.weightx = 0.0;
	d.add(constrain(new JLabel(info), gb, c));
	d.add(constrain(new JLabel(prompt), gb, c));

	c.gridwidth = 1;
	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0.0;
	d.add(constrain(new JLabel("Username:"), gb, c));

	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.weightx = 1.0;
	String user = getDefaultUserName();
	JTextField username = new JTextField(user, 20);
	d.add(constrain(username, gb, c));

	c.gridwidth = 1;
	c.fill = GridBagConstraints.NONE;
	c.anchor = GridBagConstraints.EAST;
	c.weightx = 0.0;
	d.add(constrain(new JLabel("Password:"), gb, c));

	c.anchor = GridBagConstraints.EAST;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.weightx = 1.0;
	JPasswordField password = new JPasswordField("", 20);
	d.add(constrain(password, gb, c));
	// XXX - following doesn't work
	if (user != null && user.length() > 0)
	    password.requestFocus();
	else
	    username.requestFocus();
	
	int result = JOptionPane.showConfirmDialog(frame, d, "Login",
	    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	
	if (result == JOptionPane.OK_OPTION)
	    return new PasswordAuthentication(username.getText(),
						password.getText());
	else
	    return null;
    }

    private Component constrain(Component cmp,
        			GridBagLayout gb, GridBagConstraints c) {
	gb.setConstraints(cmp, c);
	return (cmp);
    }
}
