/*
 * @(#)FolderViewer.java	1.13 07/07/06
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

import java.awt.*;
import javax.mail.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * @version	1.13, 07/07/06
 * @author	Christopher Cotton
 * @author	Bill Shannon
 */

public class FolderViewer extends JPanel {

    FolderModel model = new FolderModel();
    JScrollPane scrollpane;
    JTable table;

    public FolderViewer() {
	this(null);
    }

    public FolderViewer(Folder what) {
	super(new GridLayout(1,1));

	table = new JTable(model);
	table.setShowGrid(false);

	scrollpane = new JScrollPane(table);

	// setup the folder we were given
	setFolder(what);
	
	// find out what is pressed
	table.getSelectionModel().addListSelectionListener(
	    new FolderPressed());
	scrollpane.setPreferredSize(new Dimension(700, 300));
	add(scrollpane);
    }

    /**
     * Change the current Folder for the Viewer
     *
     * @param what	the folder to be viewed
     */
    public void setFolder(Folder what) {
	try {
	    table.getSelectionModel().clearSelection();
	    if (SimpleClient.mv != null)
		SimpleClient.mv.setMessage(null);
	    model.setFolder(what);
	    scrollpane.invalidate();
	    scrollpane.validate();
	} catch (MessagingException me) {
	    me.printStackTrace();
	}
    }

    class FolderPressed implements ListSelectionListener {

	public void valueChanged(ListSelectionEvent e) {
	    if (model != null && !e.getValueIsAdjusting()) {
		ListSelectionModel lm = (ListSelectionModel) e.getSource();
		int which = lm.getMaxSelectionIndex();
		if (which != -1) {
		    // get the message and display it
		    Message msg = model.getMessage(which);
		    SimpleClient.mv.setMessage(msg);
		}
	    }
	}
    }
}
