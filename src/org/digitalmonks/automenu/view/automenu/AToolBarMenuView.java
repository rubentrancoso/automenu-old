package org.digitalmonks.automenu.view.automenu;


import java.awt.BorderLayout;
import java.awt.Font;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;

import org.digitalmonks.automenu.model.ModelEntity;
import org.digitalmonks.automenu.view.View;
import org.digitalmonks.automenu.view.automenu.widgets.AMenuIcon;
//import org.digitalmonks.automenu.view.automenu.widgets.ASeparator;
import org.digitalmonks.automenu.view.automenu.widgets.AToolBar;
//import org.digitalmonks.automenu.view.automenu.widgets.AToolBarGroup;
import org.digitalmonks.automenu.view.automenu.widgets.AToolBarItem;

public class AToolBarMenuView extends View
{

    public AToolBarMenuView(ModelEntity rootEntity, JFrame holder, Object icontrol)
    {
    	super(rootEntity, icontrol);
        holder.getContentPane().add(rootMenu, BorderLayout.PAGE_START);
        holder.validate();
    }

    public void renderMenu(ModelEntity entity)
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

    protected void tranverseView()
    {
        //int z = rootMenu.getComponentCount();
        //for(int i = 0; i < z; i++)
        //{
        //    Object item = (JMenuItem)rootMenu.getComponent(i);
        //    if(item instanceof AMenu)
        //        tranverseMenu((AMenu)item);
        //}

    }

    //private void tranverseMenu(AMenu _item)
    //{
    //   int z = _item.getItemCount();
    //    if(adjustMenuIcons)
    //        _item.adjustMenuGap();
    //    for(int i = 0; i < z; i++)
    //    {
    //        Object item = _item.getItem(i);
    //        if(item instanceof AMenu)
    //            tranverseMenu((AMenu)item);
    //    }
    //
    //}

    protected JComponent renderEntity(ModelEntity entity)
    {
        JComponent entityView = null;
        String name = (String)entity.getAttribute("entityType");
        if(name.equals("menu"))
        {
            entityView = new AToolBar();
            rootMenu = (AToolBar)entityView;
            setMenuAttributes(entity);
        }
        //if(name.equals("group"))
        //{
        //    entityView = new AToolBarGroup();
        //    setMenuAttributes(entity);
        //    ((AMenu)buildStack.lastElement()).add(entityView);
        //}
        //if(name.equals("submenu"))
        //{
        //    entityView = new AMenu();
        //    applyAttributes((JMenuItem)entityView, entity);
        //    JComponent lastElement = (JComponent)buildStack.lastElement();
        //    if(lastElement instanceof AMenuBar)
        //        ((AMenuBar)buildStack.lastElement()).add((JMenuItem)entityView);
        //    if(lastElement instanceof AMenu)
        //        ((AMenu)buildStack.lastElement()).add((JMenuItem)entityView);
        //}
        if(name.equals("item"))
        {
            String itemType = (String)entity.getAttribute("type");
            if(itemType.equals("action"))
                entityView = new AToolBarItem();
            applyAttributes((JComponent)entityView, entity);
            ((AToolBar)buildStack.lastElement()).add((JComponent)entityView);
        }
        //if(name.equals("separator"))
        //{
        //    entityView = new ASeparator();
        //    ((AMenu)buildStack.lastElement()).add(entityView);
        //}
        return entityView;
    }
    
    public Font getFont() {
    	return menuFont;
    }

    private void applyAttributes(JComponent item, ModelEntity entity)
    {
    	if( item instanceof JButton ) 
    	{
	        ((JButton)item).setText((String)entity.getAttribute("name"));
	        String tooltip = (String)entity.getAttribute("tooltip");
	        if(tooltip != null)
	            item.setToolTipText(tooltip);
	        item.setFont(menuFont);
	        if(drawImages)
	        {
	            String icon = (String)entity.getAttribute("icon");
	            if(icon != null)
	            {
	                AMenuIcon eicon = new AMenuIcon(icon);
	                if( eicon.hasIcon() ) {
	                	javax.swing.ImageIcon dicon = eicon.getGrayIcon();
	                	((JButton)item).setDisabledIcon(dicon);
	                }
	                else {
	                	eicon = null;
	                }
	                ((JButton)item).setIcon(eicon);
	            }
	        } else
	        {
	        	((JButton)item).setIcon(null);
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
	        //String type = (String)entity.getAttribute("type");
	        //if(type != null)
	        //    if(type.equals("check") || type.equals("radio"))
	        //        item.addItemListener((ItemListener)control);
	        //    else
	        //        item.addActionListener((ActionListener)control);
    	}
    }
    
//    private void applyAttributes(JMenuItem item, ModelEntity entity)
//    {
//        item.setText((String)entity.getAttribute("name"));
//        String tooltip = (String)entity.getAttribute("tooltip");
//        if(tooltip != null)
//            item.setToolTipText(tooltip);
//        item.setFont(menuFont);
//        if(drawImages)
//        {
//            String icon = (String)entity.getAttribute("icon");
//            if(icon != null)
//            {
//                AMenuIcon eicon = new AMenuIcon(icon);
//                if( eicon.hasIcon() ) {
//                	javax.swing.ImageIcon dicon = eicon.getGrayIcon();
//                	item.setDisabledIcon(dicon);
//                }
//                else {
//                	eicon = null;
//                }
//                item.setIcon(eicon);
//            }
//        } else
//        {
//            item.setIcon(null);
//        }
//        String disablemap = (String)entity.getAttribute("disablemap");
//        if(disablemap != null)
//        {
//            if(disableMap == null)
//                disableMap = new Hashtable();
//            Vector mapNames = getMapNamesVector(disablemap);
//            for(int i = 0; i < mapNames.size(); i++)
//            {
//                String disableMapKey = (String)mapNames.get(i);
//                Vector disableVector = (Vector)disableMap.get(disableMapKey);
//                if(disableVector == null)
//                {
//                    disableVector = new Vector();
//                    disableMap.put(disableMapKey, disableVector);
//                }
//                disableVector.add(item);
//            }
//
//        }
//        //String type = (String)entity.getAttribute("type");
//        //if(type != null)
//        //    if(type.equals("check") || type.equals("radio"))
//        //        item.addItemListener((ItemListener)control);
//        //    else
//        //        item.addActionListener((ActionListener)control);
//    }

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

    private static int menuLevel = -1;
    private Stack buildStack;
    private AToolBar rootMenu;
    private boolean drawImages;
    //private boolean editMenu;
    //private boolean adjustMenuIcons;
    //private Object control;
    Font menuFont;
    Hashtable groups;
    Hashtable disableMap;
	protected void updateHolder() {
		// TODO Auto-generated method stub
		
	}

}
