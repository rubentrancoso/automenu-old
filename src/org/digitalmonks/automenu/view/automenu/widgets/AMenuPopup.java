package org.digitalmonks.automenu.view.automenu.widgets;

//import java.awt.Component;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;

import org.digitalmonks.automenu.control.Control;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            AMenuIcon, AMenuItem, AMenu, ACheckBoxMenuItem, 
//            ARadioButtonMenuItem

public class AMenuPopup extends JPopupMenu
{

	private static final long serialVersionUID = -3839190960179961649L;

	HashMap items = new HashMap();
	HashMap subMenus = new HashMap();

	public AMenuPopup(Control _control)
    {
        simpleMaxWidth = 0;
        radioMaxWidth = 0;
        checkMaxWidth = 0;
        control = _control;
        Border current;
        Border space;
        current = getBorder();
        space = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        setBorder(BorderFactory.createCompoundBorder(current, space));
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
        int z = getComponentCount();
        for(int i = 0; i < z; i++)
        {
            JMenuItem item = null;
            Object o = getComponent(i);
            if(o instanceof JMenuItem)
                item = (JMenuItem)getComponent(i);
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

    int simpleMaxWidth;
    int radioMaxWidth;
    int checkMaxWidth;
    Vector radioVector;
    Vector checkVector;
    Object invoker;
    Control control;
    
    public ItemListener getTarget() {
		return (ItemListener)control;
	}
    
}
