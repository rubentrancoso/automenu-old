package org.digitalmonks.automenu.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

//import org.apache.commons.jxpath.JXPathContext;
import org.digitalmonks.automenu.model.ModelEntity;
import org.digitalmonks.automenu.support.I18N.I18N;
import org.digitalmonks.automenu.view.automenu.widgets.ACheckBoxMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.AMenu;
import org.digitalmonks.automenu.view.automenu.widgets.ARadioButtonMenuItem;


public abstract class View
{
	private final String MESSAGE_FILE = "properties/automenumessages";
	
	protected View(ModelEntity rootEntity, Object icontrol) {
        drawImages = false;
        adjustMenuIcons = false;
        control = icontrol;
        groups = new Hashtable();
        _loadView(rootEntity);
	}
	
	protected abstract void tranverseView();
	
    protected abstract JComponent renderEntity(ModelEntity entity);

    public Font getFont() {
    	return menuFont;
    }

    private void _loadView(ModelEntity rootEntity) {
    	i18N = new I18N(MESSAGE_FILE, Locale.getDefault());
        buildStack = new Stack();
        renderMenu(rootEntity);
        buildStack = null;
        i18N = null;
    }

    public void loadView(ModelEntity rootEntity) {
    	_loadView(rootEntity);
        updateHolder();
    }
    
    protected abstract void updateHolder();
    
    protected void renderMenu(ModelEntity entity)
    {
        JComponent entityView = null;
        entityView = renderEntity(entity);
        int z = entity.linkCount();
        for(int i = 0; i < z; i++)
        {
            buildStack.push(entityView);
            menuLevel++;
            renderMenu((ModelEntity)entity.getLink(i));
            menuLevel--;
            buildStack.pop();
        }
    }

    protected void setMenuAttributes(ModelEntity entity)
    {
        String font = (String)entity.getAttribute("font");
        String size = (String)entity.getAttribute("size");
        String icons = (String)entity.getAttribute("icons");
        String adjustIcons = (String)entity.getAttribute("adjustIcons");
        menuFont = rootMenu.getFont();
        menuFont = new Font(menuFont.getName(), 0, menuFont.getStyle());
        if(font != null)
            menuFont = new Font(font, menuFont.getStyle(), menuFont.getSize());
        if(size != null)
            menuFont = new Font(menuFont.getName(), menuFont.getStyle(), Integer.parseInt(size));
        if(icons.equals("true"))
            drawImages = true;
        if(adjustIcons.equals("true"))
            adjustMenuIcons = true;
    }
    
    protected void tranverseMenu(AMenu _item)
    {
        int z = _item.getItemCount();
        if(adjustMenuIcons)
            _item.adjustMenuGap();
        for(int i = 0; i < z; i++)
        {
            Object item = _item.getItem(i);
            if(item instanceof AMenu)
                tranverseMenu((AMenu)item);
        }

    }

    protected Vector getMapNamesVector(String disableMapAttribute)
    {
        int start = 0;
        if(!disableMapAttribute.endsWith(","))
            disableMapAttribute = disableMapAttribute + ",";
        Vector v = new Vector();
        for(int end = disableMapAttribute.indexOf(","); end > 0; end = disableMapAttribute.indexOf(","))
        {
            String fragment = disableMapAttribute.substring(start, end);
            v.add(fragment);
            disableMapAttribute = disableMapAttribute.substring(end + 1);
        }

        return v;
    }

    public void disableMapEnabled(String name, boolean state)
    {
        if(disableMap != null)
        {
            Vector map = (Vector)disableMap.get(name);
            if(map != null)
            {
                for(int i = 0; i < map.size(); i++)
                    ((JMenuItem)map.get(i)).setEnabled(state);

            }
        }
    }
    
    protected Hashtable disableMap;
    protected Font menuFont;
    protected boolean adjustMenuIcons;
    protected Stack buildStack;
    private static int menuLevel = -1;
    protected Vector disableMapVector;
    protected boolean drawImages;
    protected JComponent rootMenu;
    protected Hashtable groups;
    protected Object control;
    protected I18N i18N;

    private Object doJXPathContext(String xpath) {
    	// This method is optional
    	// if jar lib is not present it returns no error
    	// see equivalent code:
    	//	JXPathContext context = JXPathContext.newContext(rootMenu);
    	//	Object o = context.getValue(xpath);
		Class jxpathContext;
		Method meth = null;
		Object o = null;
		try {
			jxpathContext = getClass().getClassLoader().loadClass("org.apache.commons.jxpath.JXPathContext");
			Class partypes[] = new Class[1];
			partypes[0] = Object.class;
			meth = jxpathContext.getMethod("newContext", partypes);
			Object pars[] = new Object[1];
			pars[0] = rootMenu;
			Object context = meth.invoke(null, pars);
			partypes[0] = String.class;
			meth = context.getClass().getMethod("getValue", partypes);
			pars[0] = xpath;
			o = meth.invoke(context, pars);
		} catch (ClassNotFoundException e) {
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return o;
    }
    
    public void setItemState(String xpath, boolean checked) {
    	Object o = doJXPathContext(xpath);
    	if(o != null) {
			ItemListener[] il = ((JMenuItem)o).getItemListeners();
			for(int i = 0;i<il.length; i++) {
				((JMenuItem)o).removeItemListener(il[i]);
			}
			if(o instanceof ARadioButtonMenuItem) {
				((ARadioButtonMenuItem)o).setSelected(checked);
			}
			if(o instanceof ACheckBoxMenuItem) {
				((ACheckBoxMenuItem)o).setSelected(checked);
			}
			for(int i = 0;i<il.length; i++) {
				((JMenuItem)o).addItemListener(il[i]);
			}
    	}
	}

	public void clickItem(String xpath) {
    	Object o = doJXPathContext(xpath);
    	if(o != null) 
    		((JMenuItem)o).doClick();
	}

	public void setItemVisible(String xpath, boolean visible) {
    	Object o = doJXPathContext(xpath);
    	if(o != null) 
    		((Component)o).setVisible(visible);
	}
}
