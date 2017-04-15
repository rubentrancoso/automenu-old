package automenu.demo;
/*
 * SandBox.java
 *
 * Created on 8 de Abril de 2004, 19:50
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.digitalmonks.automenu.ABarMenu;
import org.digitalmonks.automenu.APopupMenu;
import org.digitalmonks.automenu.AToolBarMenu;
import org.digitalmonks.automenu.control.events.AAutoMenuEvent;
import org.digitalmonks.automenu.control.events.AAutoMenuListener;
import org.digitalmonks.automenu.view.automenu.widgets.ARadioButtonMenuItem;

/**
 *
 * @author  Simpson
 */
public class SandBox extends JFrame implements AAutoMenuListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7568671554744791795L;
	int event = 0;
	ABarMenu abarmenu;
	APopupMenu apopupmenu, apopupmenu2;
	AToolBarMenu toolbarmenu;
	JTextArea txtArea;
    
    /** Creates a new instance of SandBox */
    public SandBox() {
    	this.setTitle("AutoMenu demo");
        JPanel holder;
        Object actionTarget;
        String xmlFile;

        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException ex) {
          System.out.println("Unable to load native look and feel");
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        
        setSize( 500, 400 );
        setVisible(true);
        
        holder = new JPanel();
        holder.setBackground( Color.WHITE );
        this.getContentPane().add( holder , BorderLayout.NORTH );
        JButton b = new JButton("rigth click here");
        b.setFocusable(false);
        holder.add( b);
        EditField t = new EditField("right click here", 12 );
        holder.add( t );
        txtArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtArea);
        this.getContentPane().add(scrollPane,BorderLayout.CENTER);
        actionTarget = this;
        xmlFile = "popmenu.xml";
        apopupmenu2 = new APopupMenu( xmlFile, b, actionTarget );
        apopupmenu2.addHolder( holder );
        xmlFile = "popmenu2.xml";
        apopupmenu = new APopupMenu( xmlFile, t, actionTarget );
        xmlFile = "barmenu.xml";
        abarmenu = new ABarMenu( xmlFile, this, actionTarget );
        //xmlFile = "toolbarmenu.xml";
        //toolbarmenu = new AToolBarMenu( xmlFile, this, actionTarget );
        this.setVisible(true);

        abarmenu.setItemState("submenu[@name='Radio']/item[@name='Orange']",true);
        abarmenu.clickItem("submenu[@name='Radio']/item[@name='Small']");
        abarmenu.setItemState("submenu[@name='Check']/item[@name='Packed']",true);
        abarmenu.setItemState("submenu[@name='Check']/submenu[@name='Other']/item[@name='Check This']",true);
        abarmenu.setItemVisible("submenu[@name='Check']/item[@name='Invisible']",false);
        abarmenu.setItemVisible("submenu[@name='Check']/submenu[@name='Invisible']",false);
        
        apopupmenu.setItemState("submenu[@name='Check']/item[@name='Packed']",true);
        apopupmenu.clickItem("submenu[@name='Check']/item[@name='Fresh']");
        apopupmenu.setItemState("submenu[@name='Check']/submenu[@name='Other']/item[@name='Check This']",true);
        apopupmenu.setItemVisible("submenu[@name='Check']/item[@name='Invisible']",false);
        apopupmenu.setItemVisible("submenu[@name='Check']/submenu[@name='Invisible']",false);
        
        //int position = abarmenu.getMenuPosition("/menu/submenu[name='Submenus']");
        //abarmenu.mergeMenu("/menu, newMenu.xml);
        //abarmenu.mergeMenu("/menu, newMenu.xml, position);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SandBox sandbox = new SandBox();
        sandbox.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
    }
    
    public void receiveAutoMenuEvents( AAutoMenuEvent e ) {
    	
        String eventType = "";
        String sourceType = "";
        switch( e.getType() ) {
            case AAutoMenuEvent.EVENTTYPE_BARMENU:
                eventType = "EVENTTYPE_BARMENU";
                if ( "20.10".equals(((JMenuItem)e.getSource()).getActionCommand()))
                {
                    abarmenu.disableMapEnabled( "NoSave", true );
                    abarmenu.disableMapEnabled( "NoAccess", true );
                }
                if ( "20.20".equals(((JMenuItem)e.getSource()).getActionCommand())) { 
                    abarmenu.disableMapEnabled( "NoAccess", true );
                    abarmenu.disableMapEnabled( "NoSave", false );
                }
                if ( "20.30".equals(((JMenuItem)e.getSource()).getActionCommand())) 
                    abarmenu.disableMapEnabled( "NoAccess", false );
                if ( "10.50".equals(((JMenuItem)e.getSource()).getActionCommand())) {
                	abarmenu.loadMenu("barmenu.xml");
                }
                if ( "10.60".equals(((JMenuItem)e.getSource()).getActionCommand())) { 
                	abarmenu.loadMenu("barmenu2.xml");
                }
                break;
            case AAutoMenuEvent.EVENTTYPE_POPUPMENU:
                eventType = "EVENTTYPE_POPUPMENU";
                if ( "10.50".equals(((JMenuItem)e.getSource()).getActionCommand())) {
                	apopupmenu.loadMenu("popmenu2.xml");
                }
                if ( "10.60".equals(((JMenuItem)e.getSource()).getActionCommand())) { 
                	apopupmenu.loadMenu("popmenu3.xml");
                }
                break;
            case AAutoMenuEvent.EVENTTYPE_HINTLIST:
                eventType = "EVENTTYPE_HINTLIST";
                break;
        }
    	txtArea.insert("SandBox - Main    : " + e.getAutoMenu() + "\n", 0 );
    	txtArea.insert("SandBox - Source  : " + eventType + "\n", 0 );
        switch( e.getSourceType() ) {
            case AAutoMenuEvent.SOURCETYPE_CHECKBOX:
                sourceType = "SOURCETYPE_CHECKBOX";
                break;
            case AAutoMenuEvent.SOURCETYPE_INVOKERSTATE:
                sourceType = "SOURCETYPE_INVOKERSTATE";
                break;
            case AAutoMenuEvent.SOURCETYPE_INVOKERSTATECHECK:
                sourceType = "SOURCETYPE_INVOKERSTATECHECK";
                break;
            case AAutoMenuEvent.SOURCETYPE_MENUITEM:
                sourceType = "SOURCETYPE_MENUITEM";
                break;
            case AAutoMenuEvent.SOURCETYPE_RADIOBUTTON:
                sourceType = "SOURCETYPE_RADIOBUTTON";
                String group = ((ARadioButtonMenuItem)e.getSource()).getGroup();
                if ( group != null ) {
                	txtArea.insert("SandBox - Group   : " + group + "\n", 0 );
                }
                break;
        }
        txtArea.insert("Type    : " + sourceType + "\n", 0 );
    	txtArea.insert("Invoker : " + e.getInvoker() + "\n", 0);
    	txtArea.insert("Name    : " + ((JMenuItem)e.getSource()).getText() + "\n", 0);
    	txtArea.insert("ID      : " + ((JMenuItem)e.getSource()).getActionCommand() + "\n", 0);
    	txtArea.insert("Source  : " + e.getSource() + "\n", 0 );
    	txtArea.insert("# " + event++ + " #" + "\n", 0 );
    	txtArea.setCaretPosition(0);
    }
}
