package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            ACheckBoxMenuItem, AMenuPopup

public class AInvokerStateCheckBoxItem extends ACheckBoxMenuItem
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9088786690741917807L;
	/**
	 * 
	 */
	public AInvokerStateCheckBoxItem(AMenuPopup menu)
    {
        popmenu = menu;
    }

    public void setInvokerProperty(String property)
    {
        propertySuffix = property;
    }

    public String getInvokerProperty()
    {
        return propertySuffix;
    }

    public Component getInvoker()
    {
        return popmenu.getInvoker();
    }

    public void addItemListener(ItemListener l)
    {
        listener = l;
        super.addItemListener(l);
    }

    public void paint(Graphics g)
    {
        Method meth = null;
        boolean value = false;
        Object invoker = getInvoker();
        try
        {
            meth = invoker.getClass().getMethod("is" + propertySuffix, null);
            try
            {
                Object result = meth.invoke(invoker, null);
                value = ((Boolean)result).booleanValue();
            }
            catch(Exception e) { }
        }
        catch(NoSuchMethodException nosuchmethodexception) { }
        removeItemListener(listener);
        setSelected(value);
        addItemListener(listener);
        //((Graphics2D)g).setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        super.paint(g);
    }

    private String propertySuffix;
    private AMenuPopup popmenu;
    private ItemListener listener;
}
