package org.digitalmonks.automenu.view.automenu.widgets;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;

import javax.swing.*;

public class AMenuIcon
    implements Icon
{

    public AMenuIcon()
    {
        icone = new ImageIcon("");
        width = 0;
        height = 0;
    }

    public AMenuIcon(int margem, String imagem)
    {
    	
        icone = getIcon(imagem);
        width = margem + icone.getIconWidth();
        if(width < icone.getIconWidth())
            width = icone.getIconWidth();
        height = icone.getIconHeight();
    }

    public AMenuIcon(String imagem)
    {
        icone = getIcon(imagem);
        if ( icone != null ) 
        {
        	width = icone.getIconWidth();
        	height = icone.getIconHeight();
        }
    }

    public AMenuIcon(ImageIcon icon)
    {
        icone = icon;
        width = icone.getIconWidth();
        height = icone.getIconHeight();
    }
    
    private ImageIcon getIcon(String imagem) {
    	ImageIcon icon;
    	icon = null;
    	File f = new File(imagem);
    	if (f.exists()) 
    		icon = new ImageIcon(imagem); // Try filesystem
    	else
    		try {
    			icon = new ImageIcon(getClass().getResource("/"+imagem)); // try from jar
    		}
    		catch(Exception ex) { 
    		}
    	return icon;
    }

    public ImageIcon getGrayIcon()
    {
        java.awt.Image icon = GrayFilter.createDisabledImage(icone.getImage());
        ImageIcon iicon = new ImageIcon(icon);
        return iicon;
    }

    public int getIconWidth()
    {
        return width;
    }

    public int getIconHeight()
    {
        return height;
    }

    public void resizeWidth(int largura)
    {
        width = largura;
    }

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        icone.paintIcon(c, g, (x + getIconWidth()) - icone.getIconWidth(), y);
    }
    
    public boolean hasIcon() {
    	if( icone != null ) {
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    private int width;
    private int height;
    private ImageIcon icone;
}
