package org.digitalmonks.automenu.control;

import java.awt.event.*;

import org.digitalmonks.automenu.AutoMenu;
import org.digitalmonks.automenu.control.events.AAutoMenuEvent;
import org.digitalmonks.automenu.control.events.AAutoMenuListener;
import org.digitalmonks.automenu.view.automenu.widgets.*;

public class Control
    implements ActionListener, ItemListener
{

    public Control(Object actionTarget, AutoMenu imain)
    {
        main = imain;
        if(actionTarget instanceof AAutoMenuListener)
            target = actionTarget;
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        Object invoker = null;
        int eventType = 0;
        int sourceType = 0;
        if(source instanceof AMenuHintItem )
        {
            invoker = ((AMenuHintItem)e.getSource()).getInvoker();
            if(invoker != null)
                eventType = 2;
        } else {
	        if(source instanceof AMenuItem )
	        {
	            invoker = ((AMenuItem)e.getSource()).getInvoker();
	            if(invoker != null)
	                eventType = 1;
	        }
        }
        dispatchEvent(new AAutoMenuEvent(source, invoker, eventType, sourceType, main));
    }

    public void itemStateChanged(ItemEvent e)
    {
        Object source = e.getSource();
        Object invoker = null;
        int eventType = 0;
        int sourceType = 0;
        if(source instanceof ACheckBoxMenuItem)
            if(source instanceof AInvokerStateCheckBoxItem)
            {
                //boolean selected = ((AInvokerStateCheckBoxItem)source).isSelected();
                invoker = ((AInvokerStateCheckBoxItem)e.getSource()).getInvoker();
                eventType = 1;
                sourceType = 4;
                dispatchEvent(new AAutoMenuEvent(source, invoker, eventType, sourceType, main));
            } else
            {
                //boolean selected = ((ACheckBoxMenuItem)source).isSelected();
                invoker = ((ACheckBoxMenuItem)e.getSource()).getInvoker();
                if(invoker != null)
                    eventType = 1;
                sourceType = 1;
                dispatchEvent(new AAutoMenuEvent(source, invoker, eventType, sourceType, main));
            }
        if(source instanceof ARadioButtonMenuItem)
        {
            boolean selected = ((ARadioButtonMenuItem)source).isSelected();
            if(selected)
            {
                invoker = ((ARadioButtonMenuItem)e.getSource()).getInvoker();
                if(invoker != null)
                    eventType = 1;
                sourceType = 2;
                dispatchEvent(new AAutoMenuEvent(source, invoker, eventType, sourceType, main));
            }
        }
    }

    public void dispatchEvent(AAutoMenuEvent e)
    {
        if(target != null)
            ((AAutoMenuListener)target).receiveAutoMenuEvents(e);
    }
    
    public AAutoMenuListener getTarget() {
    	return (AAutoMenuListener)target;
    }

    public void setActionTarget(Object _actionTarget) {
		target = _actionTarget;
	}

    Object target;
    AutoMenu main;
}
