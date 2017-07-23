/*
 * @(#)JAFApp.java	1.13 07/07/13
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
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.activation.*;

public class JAFApp extends Frame implements WindowListener, ItemListener, ActionListener {

    // UI stuff...
    private GridBagLayout gridbag = null;
    private GridBagLayout panel_gb = null;
    private List file_list = null;
    private Panel results_panel = null;
    private Label f_title = null;
    private Label f_name_label = null;
    private Label type_label = null;
    private Label type_title = null;
    private Button launch_button = null;
    private Choice verb_choice = null;
    // File stuff...
    private File _file = null;
    // private FileDataSource fds[] = null;
    private DataHandler dh[] = null;

    private boolean debug = false;
    //    private 
    ///////////////////////////////////////////////////////////////////////
    // ctor
    public JAFApp(String title)
	{
	   super(title);
	   initMe();
	}

    public JAFApp()
	{
	    super();
	    initMe();
	}
    
    ///////////////////////////////////////////////////////////////////////
    // initME -- init the UI
    private void initMe()
	{
	    Font label_font = new Font("Default", Font.PLAIN, 12);
	    Font title_font = new Font("Default", Font.BOLD, 12);

	    gridbag = new GridBagLayout();
	    setLayout(gridbag);
	    
	    this.setBackground(Color.white);

	    // results panel create
	    results_panel = new Panel();
	    panel_gb = new GridBagLayout();
	    results_panel.setLayout(panel_gb);
	    results_panel.setBackground(Color.lightGray);
	    addGridComponent(this,
			     results_panel,
			     gridbag,
			     0,0,
			     1,1,
			     1,0 );

	    // file name label
	    f_title = new Label("File Name:");
	    f_title.setBackground(Color.lightGray);
	    f_title.setAlignment(Label.RIGHT);
	    f_title.setFont(title_font);	    
	    addGridComponent(results_panel,
			     f_title,
			     panel_gb,
			     0, 0,
			     1, 1,
			     5, 100);

	    // file name label
	    f_name_label = new Label("<none selected>");
	    f_name_label.setBackground(Color.lightGray);
	    f_name_label.setAlignment(Label.LEFT);
	    f_name_label.setFont(label_font);
	    addGridComponent(results_panel,
			     f_name_label,
			     panel_gb,
			     1, 0,
			     1, 1,
			     15, 100);
	    
	    	    // file name label
	    f_title = new Label("MIME Type:");
	    f_title.setBackground(Color.lightGray);
	    f_title.setAlignment(Label.RIGHT);
	    f_title.setFont(title_font);	    
	    addGridComponent(results_panel,
			     f_title,
			     panel_gb,
			     2, 0,
			     1, 1,
			     5, 100);

	    // type_label
	    type_label = new Label("<no type>");
	    type_label.setBackground(Color.lightGray);
	    type_label.setAlignment(Label.LEFT);
	    type_label.setFont(label_font);
	    addGridComponent(results_panel,
			     type_label,
			     panel_gb,
			     3,0,
			     1,1,
			     10, 100);

	    // launch button
	    launch_button = new Button("Launch!");
	    launch_button.setEnabled( false );
	    launch_button.setBackground(Color.red);
	    launch_button.setFont(title_font);
	    addGridComponent(results_panel,
			     launch_button,
			     panel_gb,
			     4,0,
			     1,1,
			     5, 100);
	    
	    // verb popup
	    verb_choice = new Choice();
	    verb_choice.setEnabled( false );
	    verb_choice.setFont(label_font);
	    verb_choice.add("<empty>");
	    addGridComponent(results_panel,
			     verb_choice,
			     panel_gb,
			     5,0,
			     1,1,
			     10, 100);

	    // file list
	    file_list = new List();
	    file_list.setBackground(Color.white);
	    file_list.setFont(label_font);
	    addGridComponent(this,
			     file_list,
			     gridbag,
			     0,1,
			     1, 2,
			     1,1);

	    this.invalidate();

	    // set up events
	    this.addWindowListener(this);
	    file_list.addItemListener(this);
	    launch_button.addActionListener(this);

	}

    ////////////////////////////////////////////////////////////////////////
    /**
     * adds a component to our gridbag layout
     */
    private void addGridComponent(Container cont, 
				  Component comp,
				  GridBagLayout mygb,
				  int gridx,
				  int gridy, 
				  int gridw,
				  int gridh,
				  int weightx,
				  int weighty)
	{ 
	    GridBagConstraints c = new GridBagConstraints(); 
	    c.gridx = gridx; 
	    c.gridy = gridy; 
	    c.gridwidth = gridw; 
	    c.gridheight = gridh; 
	    c.fill = GridBagConstraints.BOTH;
	    c.weighty = weighty;
	    c.weightx = weightx;
	    c.anchor =  GridBagConstraints.CENTER;
	    mygb.setConstraints(comp, c); 
	    cont.add(comp); 
	}

    ///////////////////////////////////////////////////////////////////////
    // setFile - sets the file/directory for this frame at startup usually
    //
    public void setFile( File file )
	{
	    _file = file;
	    int i;
	    MailcapCommandMap cmdmap = 
		(MailcapCommandMap)CommandMap.getDefaultCommandMap();
	    cmdmap.addMailcap("application/x-int;      jaf.viewers.IntViewer");

	    if(_file.isFile())
		{
		    dh = new DataHandler[1];
		    dh[0] = new DataHandler(new FileDataSource( _file ));

		    file_list.add( dh[0].getName() );
		}
	    else if( _file.isDirectory() )
		{
		    String files[] = _file.list();
		    MimetypesFileTypeMap map = new MimetypesFileTypeMap();
		    map.addMimeTypes("text/java java\n");
		    FileTypeMap.setDefaultFileTypeMap(map);
		    dh = new DataHandler[ files.length ];
		    // iterate through the list and make fds
		    for( i = 0; i < files.length; i++)
			{


			    if(debug)
				System.out.println(files[i]);

			    dh[i] = new  DataHandler(
					   new FileDataSource( _file.getAbsolutePath() +
							       _file.separator +  
							       files[i] )
					   );
			    dh[i].setCommandMap(cmdmap);

// 			    try {
// 				((FileDataSource)dh[i].getDataSource()).addMimeTypes("text/plain java\n");
// 			    } catch(IOException e){ System.out.println(e); }
			    file_list.add(dh[i].getName());
			}
		    System.out.println("number of files: " + files.length +
				       " read : " + i);

		}

	}
    /////////////////***ACTION LISTENER***///////////////////////
    // is called when a monkey (user) presses the launch button
    public void actionPerformed(ActionEvent evt)
	{
	    Object source = evt.getSource();
	    int index = file_list.getSelectedIndex();
	    
	    // make sure it's the launch button and that
	    // a list item is selected...
	    if(source == launch_button && index > -1){
		CommandInfo cmds[] = null;
		String mimeType = dh[index].getContentType();
		
		if(debug)
		    System.out.println("Finding...");
		
		// get the available commands for this type
		cmds = dh[index].getPreferredCommands();
		
		// if we actually got some back...
		if(cmds.length > 0){ 
		    CompViewer cont = null;
		    Object my_bean = null;
		    // take the first one
		    if(debug)
			System.out.println("Launching...");
		    
		    // get the first one
		    my_bean = dh[index].getBean( cmds[verb_choice.getSelectedIndex()] );
		    
			    // if it isn't a CommandObject we still
			    // have to give it it's data...
// 			    if(!(my_bean instanceof javax.activation.CommandObject))
// 				{
// 				    System.out.println("WHOOOAAA!");
// 				    if(my_bean instanceof 
// 				       java.io.Externalizable)
		
// 					{
// 					    try
// 						{
//                 	     ((Externalizable)my_bean).readExternal(
// 						new ObjectInputStream( 
// 						   dh[index].getInputStream() 
// 						   )
// 						);
// 						} catch(Exception e)
// 						    {
// 				  System.out.println("There was a problem reading the Externalized Data in the viewer bean : " + e);
// 						    }
// 					}
// 				}
		    if(debug)
			System.out.println("GOTTA BEAN!: " + 
					   my_bean.getClass().getName() );
			    // create a new bean container
		    cont = new CompViewer( "JAF Component" );
		    cont.setBean( (Component) my_bean);
		    cont.show();
		}
	    }
	}
    /////////////////***ITEM LISTENER***/////////////////////////
    public void itemStateChanged(ItemEvent evt)
	{
	    Integer id = (Integer)evt.getItem();

	    if(evt.getStateChange() == ItemEvent.SELECTED)
		{
		    // get the content type from the data handler...
		    String mime_type = dh[id.intValue()].getContentType();
		    // set the state in the info dlog:
		    f_name_label.setText(dh[id.intValue()].getName());
		    
		    // set the field in the UI and enable launch button
		    if(mime_type != null)
			{
			    CommandInfo cmds[] = null;
			    // set the text label
			    type_label.setText(mime_type);

			    // check to see if any commands are available
			    cmds = dh[id.intValue()].getPreferredCommands();
			    if(cmds != null && cmds.length > 0) // we got a command!
				{
				    launch_button.setEnabled( true );
				    verb_choice.setEnabled( true );
				    launch_button.setBackground( Color.green );

				    verb_choice.removeAll();
				    for(int i = 0; i < cmds.length; i++)
					{
					    //		    verb_choice.addItem(cmds[i].getBeanDescriptor().getName());
					    verb_choice.addItem(cmds[i].getCommandName());
					}
				}
			    else
				{
				    launch_button.setEnabled( false );
				    verb_choice.setEnabled( false );
				    launch_button.setBackground( Color.red );
				}
			    
			}
		    else
			{
			    // set label to be unknown and launch button off!
			    type_label.setText("<unknown>");
			    launch_button.setEnabled( false );
			    launch_button.setBackground( Color.red );
			}
		    
		}
	}
    /////////////////***WINDOW LISTENER***////////////////////////
    public void windowOpened(WindowEvent e){}
    public void windowClosing(WindowEvent e)
	{
	    System.exit(0); // quit
	}
    public void windowClosed(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
	    
    ///////////////////////////////////////////////////////////////////////
    // main
    public static void main(String args[])
	{
	    JAFApp appFrame = new JAFApp("JAF Testing Application");
	    appFrame.setSize(600, 400);
	    appFrame.show();

	    // set the directory
	    if(args.length == 0)
		{
		    appFrame.setFile( new File("."));
		}
	    else
		{
		    appFrame.setFile( new File(args[0]) );
		}
	}

	    
}
