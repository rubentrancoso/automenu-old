package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Stack;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            AMenuIcon, AMenuItem, ACheckBoxMenuItem, ARadioButtonMenuItem

public class AMenuInvokerGetHintList extends JMenu
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3170310151274945275L;
	public AMenuInvokerGetHintList(AMenuPopup menu )
    {
		me = this;
		popmenu = menu;
        simpleMaxWidth = 0;
        radioMaxWidth = 0;
        checkMaxWidth = 0;
        Border current;
        Border space;
        JPopupMenu menuPopup = this.getPopupMenu();
        current = menuPopup.getBorder();
        space = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        menuPopup.setBorder(BorderFactory.createCompoundBorder(current, space));
        this.addMenuListener(
        		new MenuListener() {

					public void menuCanceled(MenuEvent e) {
						me.removeAll();
					}

					public void menuDeselected(MenuEvent e) {
						me.removeAll();
					}

					public void menuSelected(MenuEvent e) {
				        Method meth = null;
				        Object invoker = getInvoker();
				        Stack list = null;
				        try
				        {
				            meth = invoker.getClass().getMethod("getHintList", null);
				            try
				            {
				                Object result = meth.invoke(invoker, null);
				                list = (Stack)result;
				                String name = null;
				                int size = list.size();
				                for( int i=0; i<size; i++ )
				                {
				                	name = (String)list.pop();
				                	AMenuItem item = new AMenuHintItem(popmenu);
				                	item.setText(name);
				                	item.setActionCommand(me.getActionCommand());
				                	item.setFont(itemsFont);
				                	item.addActionListener((ActionListener)popmenu.getTarget());
				                	me.add(item);
				                }
				            }
				            catch(Exception ex) { 
				            	ex.printStackTrace();
				            }
				        }
				        catch(NoSuchMethodException ex)
				        {
				        }
					}
        			
        		}
   		);
    }

	public void setItemsFont(Font font) {
		itemsFont = font;
	}
	
    private Component getInvoker()
    {
        if(popmenu != null)
            return popmenu.getInvoker();
        else
            return null;
    }
	
    public JMenuItem add(JMenuItem item)
    {
        AMenuIcon icon = (AMenuIcon)item.getIcon();
        if(icon != null && ((item instanceof AMenuItem) || (item instanceof AMenuInvokerGetHintList)))
        {
            int iconWidth = icon.getIconWidth();
            if(simpleMaxWidth < iconWidth)
                simpleMaxWidth = iconWidth;
        }
        super.add(item);
        return item;
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
                if(((item instanceof AMenuItem) || (item instanceof AMenuInvokerGetHintList)) && simpleMaxWidth > 0 && item.getIcon() == null)
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

    private AMenuPopup popmenu;
    private JMenu me;
    int simpleMaxWidth;
    int radioMaxWidth;
    int checkMaxWidth;
    Vector radioVector;
    Vector checkVector;
    private Font itemsFont;
}
