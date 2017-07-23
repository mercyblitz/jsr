/*
 * @(#)StoreTreeNode.java	1.10 07/07/06
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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.mail.*;

/**
 * Node which represents a Store in the javax.mail apis. 
 *
 * @version 1.10, 07/07/06
 * @author Christopher Cotton
 */
public class StoreTreeNode extends DefaultMutableTreeNode {
    
    protected Store	store = null;
    protected Folder	folder = null;
    protected String	display = null;

    /**
     * creates a tree node that points to the particular Store.
     *
     * @param what	the store for this node
     */
    public StoreTreeNode(Store what) {
	super(what);
	store = what;
    }

    
    /**
     * a Store is never a leaf node.  It can always contain stuff
     */
    public boolean isLeaf() {
	return false;
    }
   

    /**
     * return the number of children for this store node. The first
     * time this method is called we load up all of the folders
     * under the store's defaultFolder
     */

    public int getChildCount() {
	if (folder == null) {
	    loadChildren();
	}
	return super.getChildCount();
    }
    
    protected void loadChildren() {
	try {
	    // connect to the Store if we need to
	    if (!store.isConnected()) {
		store.connect();
	    }

	    // get the default folder, and list the
	    // subscribed folders on it
	    folder = store.getDefaultFolder();
	    // Folder[] sub = folder.listSubscribed();
	    Folder[] sub = folder.list();

	    // add a FolderTreeNode for each Folder
	    int num = sub.length;
	    for(int i = 0; i < num; i++) {
		FolderTreeNode node = new FolderTreeNode(sub[i]);
		// we used insert here, since add() would make
		// another recursive call to getChildCount();
		insert(node, i);
	    }
	    
	} catch (MessagingException me) {
	    me.printStackTrace();
	}
    }

    /**
     * We override toString() so we can display the store URLName
     * without the password.
     */

    public String toString() {
	if (display == null) {
	    URLName url = store.getURLName();
	    if (url == null) {
		display = store.toString();
	    } else {
		// don't show the password
		URLName too = new URLName( url.getProtocol(), url.getHost(), url.getPort(),
					   url.getFile(), url.getUsername(), null);
		display = too.toString();
	    }
	}
	
	return display;
    }
    
    
}

