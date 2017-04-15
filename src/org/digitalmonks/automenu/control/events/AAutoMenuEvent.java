package org.digitalmonks.automenu.control.events;

import org.digitalmonks.automenu.AutoMenu;

public class AAutoMenuEvent
{

    public AAutoMenuEvent(Object mi, Object ik, int et, int st, AutoMenu imenu)
    {
        eventType = 0;
        sourceType = 0;
        eventType = et;
        source = mi;
        sourceType = st;
        invoker = ik;
        menu = imenu;
    }

    public Object getSource()
    {
        return source;
    }

    public Object getInvoker()
    {
        return invoker;
    }

    public int getType()
    {
        return eventType;
    }

    public int getSourceType()
    {
        return sourceType;
    }

    public AutoMenu getAutoMenu()
    {
        return menu;
    }

    public static final int EVENTTYPE_BARMENU = 0;
    public static final int EVENTTYPE_POPUPMENU = 1;
    public static final int EVENTTYPE_HINTLIST = 2;
    public static final int SOURCETYPE_MENUITEM = 0;
    public static final int SOURCETYPE_CHECKBOX = 1;
    public static final int SOURCETYPE_RADIOBUTTON = 2;
    public static final int SOURCETYPE_INVOKERSTATE = 3;
    public static final int SOURCETYPE_INVOKERSTATECHECK = 4;
    int eventType;
    int sourceType;
    Object source;
    Object invoker;
    AutoMenu menu;
}
