/*
 * @(#)TextInternalizer.java	1.4 07/07/13
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
import java.io.*;
import java.beans.*;
import javax.activation.*;

public class TextInternalizer extends Panel implements Externalizable {
    // UI Vars...
    private TextArea text_area = null;
    
    // File Vars
    private File text_file = null;
    private String text_buffer = null;
    
    private DataHandler _dh = null;
    private boolean DEBUG = false;
    /**
     * Constructor
     */
    public TextInternalizer() {
	System.out.println("TextInternalizer!!!!!");

	setLayout( new GridLayout(1,1));
	// create the text area
	text_area = new TextArea("", 24, 80, 
				 TextArea.SCROLLBARS_VERTICAL_ONLY );
	text_area.setEditable( false );
	
	add(text_area);
    }
    
    public void writeExternal(ObjectOutput out) throws IOException{

    }

    public void readExternal(ObjectInput in) throws IOException, 
	ClassNotFoundException {

	
	this.setObjectInput(in);
    }



    //--------------------------------------------------------------------
//     public void setCommandContext(String verb, DataHandler dh) throws IOException {
// 	_dh = dh;
// 	this.setInputStream( _dh.getInputStream() );
//     }
  //--------------------------------------------------------------------

  /**
   * set the data stream, component to assume it is ready to
   * be read.
   */
  public void setObjectInput(ObjectInput ins) throws IOException {
      try {

      text_buffer = (String)ins.readObject();
      } catch(Exception e){ e.printStackTrace(); }
      // place in the text area
      text_area.setText(text_buffer);

    }
  //--------------------------------------------------------------------
    public void addNotify() {
	super.addNotify();
	invalidate();
    }
  //--------------------------------------------------------------------
    public Dimension getPreferredSize()	{
	return text_area.getMinimumSize(24, 80);
    }

}






