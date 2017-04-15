package org.digitalmonks.automenu.model.automenu;

import org.digitalmonks.automenu.model.ClassMap;

public class ClassMap_abarmenu extends ClassMap
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8818029678868068095L;

	public ClassMap_abarmenu()
    {
        put("menu", "org.digitalmonks.automenu.model.automenu.MenuLevel");
        put("submenu", "org.digitalmonks.automenu.model.automenu.MenuLevel");
        put("item", "org.digitalmonks.automenu.model.automenu.Action");
        put("separator", "org.digitalmonks.automenu.model.automenu.Separator");
    }
}
