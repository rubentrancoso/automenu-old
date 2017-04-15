package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Component;
import javax.swing.JMenuItem;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            AMenuPopup

public class AMenuItem extends JMenuItem
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8296554331174535292L;

	public AMenuItem()
    {
    }

    public AMenuItem(AMenuPopup menu)
    {
        popmenu = menu;
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
}
