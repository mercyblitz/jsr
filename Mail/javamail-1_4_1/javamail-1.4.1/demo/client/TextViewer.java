/*
 * @(#)TextViewer.java	1.13 07/07/06
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


/**
 * A very simple TextViewer Bean for the MIMEType "text/plain"
 *
 * @version	1.13, 07/07/06
 * @author	Christopher Cotton
 */

public class TextViewer extends JPanel implements CommandObject 
{

    private JTextArea text_area = null;
    private DataHandler dh = null;
    private String	verb = null;

    /**
     * Constructor
     */
    public TextViewer() {
	super(new GridLayout(1,1));

	// create the text area
	text_area = new JTextArea();
	text_area.setEditable(false);
	text_area.setLineWrap(true);

	// create a scroll pane for the JTextArea
	JScrollPane sp = new JScrollPane();
	sp.setPreferredSize(new Dimension(300, 300));
	sp.getViewport().add(text_area);
	
	add(sp);
    }


    public void setCommandContext(String verb, DataHandler dh)
	throws IOException {

	this.verb = verb;
	this.dh = dh;
	
	this.setInputStream( dh.getInputStream() );
    }


  /**
   * set the data stream, component to assume it is ready to
   * be read.
   */
  public void setInputStream(InputStream ins) {
      
      int bytes_read = 0;
      // check that we can actually read
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte data[] = new byte[1024];
      
      try {
	  while((bytes_read = ins.read(data)) >0)
		  baos.write(data, 0, bytes_read);
	  ins.close();
      } catch(Exception e) {
	  e.printStackTrace();
      }

      // convert the buffer into a string
      // place in the text area
      text_area.setText(baos.toString());

    }
}
