package org.digitalmonks.automenu.view.automenu.widgets;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AMenuBar extends JMenuBar
{

	private static final long serialVersionUID = 6860384863633936215L;

	HashMap subMenus = new HashMap();
	
	public AMenuBar()
    {
    }

	public JMenuItem add(JMenuItem item)
    {
        super.add(item);
        subMenus.put(item.getText(), item);
        return item;
    }
	
	public Map getSubmenu() {
		return subMenus;
	}
}
