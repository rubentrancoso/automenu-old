package org.digitalmonks.automenu.view.automenu;

import java.awt.Component;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.digitalmonks.automenu.control.Control;
import org.digitalmonks.automenu.model.ModelEntity;
import org.digitalmonks.automenu.view.View;
import org.digitalmonks.automenu.view.automenu.widgets.ACheckBoxMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.AInvokerStateCheckBoxItem;
import org.digitalmonks.automenu.view.automenu.widgets.AInvokerStateMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.AMenu;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuIcon;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuInvokerGetHintList;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuPopup;
import org.digitalmonks.automenu.view.automenu.widgets.ARadioButtonMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.ASeparator;

public class APopupMenuView extends View
{
    private class PopupListener extends MouseAdapter
    {

        public void mousePressed(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e)
        {
            if(e.isPopupTrigger())
            {
                boolean enable = ((JComponent)e.getComponent()).isEnabled();
                if(enable)
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        private AMenuPopup popMenu;

        PopupListener(AMenuPopup menu)
        {
            popMenu = menu;
        }
    }


    public APopupMenuView(ModelEntity rootEntity, JComponent iholder, Object icontrol)
    {
    	super(rootEntity, icontrol);
        tranverseView();
        jholder = iholder;
        popupListener = new PopupListener((AMenuPopup)rootMenu);
        jholder.addMouseListener(popupListener);
        updateHolder();
    }

    public APopupMenuView(ModelEntity rootEntity, Component iholder, Object icontrol)
    {
    	super(rootEntity, icontrol);
        tranverseView();
        cholder = iholder;
        updateHolder();
    }
    
    protected void tranverseView()
    {
        int z = rootMenu.getComponentCount();
        if(adjustMenuIcons)
        	((AMenuPopup)rootMenu).adjustMenuGap();
        for(int i = 0; i < z; i++)
        {
            Object item = rootMenu.getComponent(i);
            if(item instanceof AMenu)
                tranverseMenu((AMenu)item);
        }

    }

	protected void updateHolder() {
        popupListener = new PopupListener((AMenuPopup)rootMenu);
        if(cholder!=null)
        	cholder.addMouseListener(popupListener);
        if(jholder!=null)
        	jholder.addMouseListener(popupListener);
	}

    protected JComponent renderEntity(ModelEntity entity)
    {
        JComponent entityView = null;
        String name = (String)entity.getAttribute("entityType");
        if(name.equals("menu"))
        {
            entityView = new AMenuPopup((Control)control);
            rootMenu = (AMenuPopup)entityView;
            setMenuAttributes(entity);
        }
        if(name.equals("submenu"))
        {
            String itemType = (String)entity.getAttribute("type");
            if(itemType != null) {
	            if(itemType.equals("gethintlist")) {
	                entityView = new AMenuInvokerGetHintList((AMenuPopup)rootMenu);
	                ((AMenuInvokerGetHintList)entityView).setItemsFont(menuFont);
	            }
            } else
                entityView = new AMenu();
            applyAttributes((JMenuItem)entityView, entity);
            JComponent lastElement = (JComponent)buildStack.lastElement();
            if(lastElement instanceof AMenuPopup)
                ((AMenuPopup)buildStack.lastElement()).add((JMenuItem)entityView);
            if(lastElement instanceof AMenu)
                ((AMenu)buildStack.lastElement()).add((JMenuItem)entityView);
        }
        if(name.equals("item"))
        {
            String itemType = (String)entity.getAttribute("type");
            if(itemType.equals("action"))
                entityView = new AMenuItem((AMenuPopup)rootMenu);
            if(itemType.equals("radio"))
                entityView = new ARadioButtonMenuItem((AMenuPopup)rootMenu);
            if(itemType.equals("check"))
                entityView = new ACheckBoxMenuItem((AMenuPopup)rootMenu);
            if(itemType.equals("invokerstatecheck"))
                entityView = new AInvokerStateCheckBoxItem((AMenuPopup)rootMenu);
            if(itemType.equals("invokerstateitem"))
                entityView = new AInvokerStateMenuItem((AMenuPopup)rootMenu);
            applyAttributes((JMenuItem)entityView, entity);
            Object lastElement = buildStack.lastElement();
            if(lastElement instanceof AMenu)
                ((AMenu)lastElement).add((JMenuItem)entityView);
            if(lastElement instanceof AMenuPopup)
                ((AMenuPopup)lastElement).add((JMenuItem)entityView);
        }
        if(name.equals("separator"))
        {
            entityView = new ASeparator();
            Object lastElement = buildStack.lastElement();
            if(lastElement instanceof AMenu)
                ((AMenu)lastElement).add(entityView);
            if(lastElement instanceof AMenuPopup)
                ((AMenuPopup)lastElement).add(entityView);
        }
        return entityView;
    }

    private void applyAttributes(JMenuItem item, ModelEntity entity)
    {
        String property = (String)entity.getAttribute("property");
        if(property != null)
        {
            if(item instanceof AInvokerStateCheckBoxItem)
                ((AInvokerStateCheckBoxItem)item).setInvokerProperty(property);
            if(item instanceof AInvokerStateMenuItem)
                ((AInvokerStateMenuItem)item).setInvokerProperty(property);
        }
        item.setText(i18N.getI18NMessage((String)entity.getAttribute("name")));
        String id = (String)entity.getAttribute("id");
        if(id != null)
            item.setActionCommand(id);
        String accelerator = (String)entity.getAttribute("accelerator");
        if(accelerator != null)
            item.setAccelerator(KeyStroke.getKeyStroke(accelerator));
        String shortCut = (String)entity.getAttribute("shortcut");
        if(shortCut != null)
            item.setMnemonic(shortCut.charAt(0));
        String tooltip = i18N.getI18NMessage((String)entity.getAttribute("tooltip"));
        if(tooltip != null)
            item.setToolTipText(tooltip);
        item.setFont(menuFont);
        String group = (String)entity.getAttribute("group");
        if(group != null)
        {
            if(!groups.containsKey(group))
                groups.put(group, new ButtonGroup());
            ((ButtonGroup)groups.get(group)).add(item);
            ((ARadioButtonMenuItem)item).setGroup(group);
        }
        String preSelected = (String)entity.getAttribute("selected");
        if(preSelected != null)
            item.setSelected(true);
        if(drawImages)
        {
            String icon = (String)entity.getAttribute("icon");
            if(icon != null)
            {
                AMenuIcon eicon = new AMenuIcon(icon);
                item.setIcon(eicon);
                javax.swing.ImageIcon dicon = eicon.getGrayIcon();
                item.setDisabledIcon(dicon);
            }
        } else
        {
            item.setIcon(null);
        }
        String disablemap = (String)entity.getAttribute("disablemap");
        if(disablemap != null)
        {
            if(disableMap == null)
                disableMap = new Hashtable();
            Vector mapNames = getMapNamesVector(disablemap);
            for(int i = 0; i < mapNames.size(); i++)
            {
                String disableMapKey = (String)mapNames.get(i);
                Vector disableVector = (Vector)disableMap.get(disableMapKey);
                if(disableVector == null)
                {
                    disableVector = new Vector();
                    disableMap.put(disableMapKey, disableVector);
                }
                disableVector.add(item);
            }

        }
        String type = (String)entity.getAttribute("type");
        if(type != null)
            if(type.equals("check") || type.equals("radio") || type.equals("invokerstatecheck"))
                item.addItemListener((ItemListener)control);
            else
                item.addActionListener((ActionListener)control);
    }

    public void addHolder(JComponent holder)
    {
        holder.addMouseListener(popupListener);
    }

    private PopupListener popupListener;
    private JComponent jholder;
    private Component cholder;

}
