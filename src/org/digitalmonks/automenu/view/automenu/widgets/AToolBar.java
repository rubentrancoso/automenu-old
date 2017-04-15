package org.digitalmonks.automenu.view.automenu.widgets;

import javax.swing.JToolBar;

public class AToolBar extends JToolBar
{

	private static final long serialVersionUID = 6860384863633936215L;

	public AToolBar()
    {
    }

    public AToolBarGroup add(AToolBarGroup item)
    {
        super.add(item);
        return item;
    }
}
