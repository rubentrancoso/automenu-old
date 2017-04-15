package org.digitalmonks.automenu.model.automenu;

import org.digitalmonks.automenu.model.ClassMap;

public class ClassMap_apopmenu extends ClassMap
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1467105104510650999L;

	public ClassMap_apopmenu()
    {
        put("menu", "org.digitalmonks.automenu.model.automenu.MenuLevel");
        put("submenu", "org.digitalmonks.automenu.model.automenu.MenuLevel");
        put("item", "org.digitalmonks.automenu.model.automenu.Action");
        put("separator", "org.digitalmonks.automenu.model.automenu.Separator");
    }
}
