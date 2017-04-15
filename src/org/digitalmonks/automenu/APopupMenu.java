package org.digitalmonks.automenu;

import java.awt.Component;

import javax.swing.JComponent;

// Referenced classes of package AutoMenu:
//            AutoMenu

public class APopupMenu extends AutoMenu
{

    public APopupMenu(String xmlFile, JComponent holder, Object actionTarget)
    {
        super(xmlFile, holder, actionTarget);
    }

    public APopupMenu(String xmlFile, Component holder, Object actionTarget)
    {
        super(xmlFile, holder, actionTarget);
    }

    public void addHolder(JComponent holder)
    {
        super.addHolder(holder);
    }
}
