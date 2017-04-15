package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Component;
import java.awt.Graphics;
import java.lang.reflect.Method;

// Referenced classes of package AutoMenu.View.automenu.widgets:
//            AMenuItem, AMenuPopup

public class AInvokerStateMenuItem extends AMenuItem
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6952836962827581312L;
	public AInvokerStateMenuItem(AMenuPopup menu)
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

    public void paint(Graphics g)
    {
        Method meth = null;
        boolean value = isEnabled();
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
        catch(NoSuchMethodException ex)
        {
            value = false;
        }
        setEnabled(value);
        //((Graphics2D)g).setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        super.paint(g);
    }
    
    private String propertySuffix;
    private AMenuPopup popmenu;
}
