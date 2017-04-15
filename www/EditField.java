package automenu.demo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JTextField;

public class EditField extends JTextField
implements KeyListener
{

/**
 * 
 */
private static final long serialVersionUID = -4739413755974719567L;

public EditField(String text, int columns)
{
    super(text, columns);
    modified = false;
    addKeyListener(this);
}

public boolean isModified()
{
    return modified;
}

public void keyPressed(KeyEvent keyevent)
{
}

public Stack getHintList() {
	Stack list = new Stack();
	list.push(new String("They are"));
	list.push(new String("You are"));
	list.push(new String("I am"));
	return list;
}

public void keyReleased(KeyEvent keyevent)
{
}

public void keyTyped(KeyEvent e)
{
    modified = true;
}

private boolean modified;
}
