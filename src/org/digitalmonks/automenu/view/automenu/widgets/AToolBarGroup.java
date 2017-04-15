package org.digitalmonks.automenu.view.automenu.widgets;

import javax.swing.JComponent;

public class AToolBarGroup extends JComponent
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6860384863633936215L;

	public AToolBarGroup()
    {
    }

    public AToolBarItem add( AToolBarItem item )
    {
        super.add(item);
        return item;
    }
}
