package org.digitalmonks.automenu.view.automenu;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;

import org.digitalmonks.automenu.model.ModelEntity;
import org.digitalmonks.automenu.view.View;
import org.digitalmonks.automenu.view.automenu.widgets.ACheckBoxMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.AMenu;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuBar;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuIcon;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.ARadioButtonMenuItem;
import org.digitalmonks.automenu.view.automenu.widgets.ASeparator;

public class ABarMenuView extends View
{
	private JFrame holder;

    public ABarMenuView(ModelEntity rootEntity, JFrame iholder, Object icontrol)
    {
    	super(rootEntity, icontrol);
        tranverseView();
        holder = iholder;
        updateHolder();
    }

    protected void updateHolder() {
        holder.setJMenuBar((AMenuBar)rootMenu);
        holder.validate();
    }
    
    protected void tranverseView()
    {
        int z = rootMenu.getComponentCount();
        for(int i = 0; i < z; i++)
        {
            Object item = (JMenuItem)rootMenu.getComponent(i);
            if(item instanceof AMenu)
                tranverseMenu((AMenu)item);
        }

    }

    protected JComponent renderEntity(ModelEntity entity)
    {
        JComponent entityView = null;
        String name = (String)entity.getAttribute("entityType");
        if(name.equals("menu"))
        {
            entityView = new AMenuBar();
            rootMenu = (AMenuBar)entityView;
            setMenuAttributes(entity);
        }
        if(name.equals("submenu"))
        {
            entityView = new AMenu();
            applyAttributes((JMenuItem)entityView, entity);
            JComponent lastElement = (JComponent)buildStack.lastElement();
            if(lastElement instanceof AMenuBar)
                ((AMenuBar)buildStack.lastElement()).add((JMenuItem)entityView);
            if(lastElement instanceof AMenu)
                ((AMenu)buildStack.lastElement()).add((JMenuItem)entityView);
        }
        if(name.equals("item"))
        {
            String itemType = (String)entity.getAttribute("type");
            if(itemType.equals("action"))
                entityView = new AMenuItem();
            if(itemType.equals("radio"))
                entityView = new ARadioButtonMenuItem();
            if(itemType.equals("check"))
                entityView = new ACheckBoxMenuItem();
            applyAttributes((JMenuItem)entityView, entity);
            ((AMenu)buildStack.lastElement()).add((JMenuItem)entityView);
        }
        if(name.equals("separator"))
        {
            entityView = new ASeparator();
            ((AMenu)buildStack.lastElement()).add(entityView);
        }
        return entityView;
    }
    
    private void applyAttributes(JMenuItem item, ModelEntity entity)
    {
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
                if( eicon.hasIcon() ) {
                	javax.swing.ImageIcon dicon = eicon.getGrayIcon();
                	item.setDisabledIcon(dicon);
                }
                else {
                	eicon = null;
                }
                item.setIcon(eicon);
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
            if(type.equals("check") || type.equals("radio"))
                item.addItemListener((ItemListener)control);
            else
                item.addActionListener((ActionListener)control);
    }

}
