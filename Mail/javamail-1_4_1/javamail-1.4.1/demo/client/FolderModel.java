/*
 * @(#)FolderModel.java	1.14 07/07/06
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

import javax.mail.*;
import java.util.Date;
import javax.swing.table.AbstractTableModel; 

/**
 * Maps the messages in a Folder to the Swing's Table Model
 *
 * @version	1.14, 07/07/06
 * @author	Christopher Cotton
 * @author	Bill Shannon
 */

public class FolderModel extends AbstractTableModel {
    
    Folder	folder;
    Message[]	messages;

    String[]	columnNames = { "Date", "From", "Subject"}; 
    Class[]	columnTypes = { String.class, String.class, String.class }; 

    public void setFolder(Folder what) throws MessagingException {
	if (what != null) {

	    // opened if needed
	    if (!what.isOpen()) {
		what.open(Folder.READ_WRITE);
	    }
    
	    // get the messages
	    messages = what.getMessages();
	    cached = new String[messages.length][];
	} else {
	    messages = null;
	    cached = null;
	}
	// close previous folder and switch to new folder
	if (folder != null)
	    folder.close(true);
	folder = what;
	fireTableDataChanged();
    }
    
    public Message getMessage(int which) {
	return messages[which];
    }

    //---------------------
    // Implementation of the TableModel methods
    //---------------------

    public String getColumnName(int column) {
	return columnNames[column];
    }
    
    public Class getColumnClass(int column) {
	return columnTypes[column];
    }
    

    public int getColumnCount() {
        return columnNames.length; 
    }

    public int getRowCount() {
	if (messages == null)
	    return 0;
	
	return messages.length;
    }
 
    public Object getValueAt(int aRow, int aColumn) {
	switch(aColumn) {
	case 0:	// date
	case 1: // From		String[] what = getCachedData(aRow);
	case 2: // Subject
	    String[] what = getCachedData(aRow);
	    if (what != null) {
		return what[aColumn];
	    } else {
		return "";
	    }
	    
	default:
	    return "";
	}
    }

    protected static String[][]	cached;
    
    protected String[] getCachedData(int row) {
	if (cached[row] == null) {
	    try{
		Message m = messages[row];
	    
		String[] theData = new String[4];
	    
		// Date
		Date date = m.getSentDate();
		if (date == null) {
		    theData[0] = "Unknown";
		} else {
		    theData[0] = date.toString();
		}
	    
		// From
		Address[] adds = m.getFrom();
		if (adds != null && adds.length != 0) {
		    theData[1] = adds[0].toString();	    
		} else {
		    theData[1] = "";
		}
		
		// Subject
		String subject = m.getSubject();
		if (subject != null) {
		    theData[2] = subject;
		} else {
		    theData[2] = "(No Subject)";
		}

		cached[row] = theData;
	    }
	    catch (MessagingException e) {
		e.printStackTrace();
	    }
	}
	
	return cached[row];
    }
}
