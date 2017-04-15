package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            AMenuIcon, AMenuItem, ACheckBoxMenuItem, ARadioButtonMenuItem

public class AMenu extends JMenu
{

	private static final long serialVersionUID = 3170310151274945275L;

	HashMap items = new HashMap();
	HashMap subMenus = new HashMap();
	
	public AMenu()
    {
        simpleMaxWidth = 0;
        radioMaxWidth = 0;
        checkMaxWidth = 0;
        Border current;
        Border space;
        JPopupMenu menuPopup = this.getPopupMenu();
        current = menuPopup.getBorder();
        space = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        menuPopup.setBorder(BorderFactory.createCompoundBorder(current, space));
    }

    public JMenuItem add(JMenuItem item)
    {
    	if(item instanceof AMenu)
    		subMenus.put(item.getText(), item);
    	else
    		items.put(item.getText(), item);
    	
        AMenuIcon icon = (AMenuIcon)item.getIcon();
        if(icon != null && ((item instanceof AMenuItem) || (item instanceof AMenu)))
        {
            int iconWidth = icon.getIconWidth();
            if(simpleMaxWidth < iconWidth)
                simpleMaxWidth = iconWidth;
        }
        super.add(item);
        return item;
    }
    
    public Map getItem() {
    	return items;
    }

    public Map getSubmenu() {
		return subMenus;
	}

    public void adjustMenuGap()
    {
        radioVector = new Vector();
        checkVector = new Vector();
        int z = getItemCount();
        for(int i = 0; i < z; i++)
        {
            JMenuItem item = getItem(i);
            if(item != null)
            {
                if(((item instanceof AMenuItem) || (item instanceof AMenu)) && simpleMaxWidth > 0 && item.getIcon() == null)
                    addBlankIcon(item, simpleMaxWidth);
                if(item instanceof ACheckBoxMenuItem)
                {
                    AMenuIcon icon = (AMenuIcon)item.getIcon();
                    if(icon == null)
                    {
                        checkVector.add(item);
                    } else
                    {
                        int iconWidth = icon.getIconWidth();
                        if(checkMaxWidth < iconWidth)
                            checkMaxWidth = iconWidth;
                    }
                }
                if(item instanceof ARadioButtonMenuItem)
                {
                    AMenuIcon icon = (AMenuIcon)item.getIcon();
                    if(icon == null)
                    {
                        radioVector.add(item);
                    } else
                    {
                        int iconWidth = icon.getIconWidth();
                        if(radioMaxWidth < iconWidth)
                            radioMaxWidth = iconWidth;
                    }
                }
            } else
            {
                processOptionItens();
            }
        }

        processOptionItens();
    }

    private void processOptionItens()
    {
        if(radioMaxWidth > 0)
        {
            for(int i = 0; i < radioVector.size(); i++)
                addBlankIcon((JMenuItem)radioVector.get(i), radioMaxWidth);

            radioMaxWidth = 0;
        }
        if(checkMaxWidth > 0)
        {
            for(int i = 0; i < checkVector.size(); i++)
                addBlankIcon((JMenuItem)checkVector.get(i), checkMaxWidth);

            checkMaxWidth = 0;
        }
        radioVector = new Vector();
        checkVector = new Vector();
    }

    private void addBlankIcon(JMenuItem item, int width)
    {
        AMenuIcon icon = new AMenuIcon();
        icon.resizeWidth(width);
        item.setIcon(icon);
    }

    protected void paintComponent( Graphics g )
    {
        //((Graphics2D)g).setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        super.paintComponent( g );        
    }

    int simpleMaxWidth;
    int radioMaxWidth;
    int checkMaxWidth;
    Vector radioVector;
    Vector checkVector;
}
