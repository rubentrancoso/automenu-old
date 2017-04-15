package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Component;

import javax.swing.JRadioButtonMenuItem;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            AMenuPopup

public class ARadioButtonMenuItem extends JRadioButtonMenuItem
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2367124882663184413L;
	public ARadioButtonMenuItem()
    {
    }

    public ARadioButtonMenuItem(AMenuPopup menu)
    {
        popmenu = menu;
    }

    public void setGroup(String igroup)
    {
        if(group == null)
            group = igroup;
    }

    public String getGroup()
    {
        return group;
    }

    public Component getInvoker()
    {
        if(popmenu != null)
            return popmenu.getInvoker();
        else
            return null;
    }
    
//    protected void paintComponent( Graphics g )
//    {
//        ((Graphics2D)g).setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
//        super.paintComponent( g );        
//    }
    

    private AMenuPopup popmenu;
    String group;
}
