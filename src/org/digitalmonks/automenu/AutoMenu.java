package org.digitalmonks.automenu;


import java.awt.Component;
import java.awt.Font;
import java.util.Stack;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.digitalmonks.automenu.control.Control;
import org.digitalmonks.automenu.model.ClassMap;
import org.digitalmonks.automenu.model.ModelEntity;
import org.digitalmonks.automenu.support.DOMUtil;
import org.digitalmonks.automenu.view.View;
import org.digitalmonks.automenu.view.automenu.ABarMenuView;
import org.digitalmonks.automenu.view.automenu.APopupMenuView;
import org.digitalmonks.automenu.view.automenu.AToolBarMenuView;
import org.w3c.dom.*;

// Referenced classes of package AutoMenu:
//            APopupMenu

public abstract class AutoMenu
{

    public AutoMenu(String xmlFile, JFrame holder, Object actionTarget)
    {
        classMap = null;
        buildStack = new Stack();
        if(this instanceof ABarMenu)
        {
        	document = getDocument(xmlFile);
        	translateDocument();
        	control = new Control(actionTarget, this);
        	view = new ABarMenuView(rootEntity, (JFrame)holder, control);
        }
        if(this instanceof AToolBarMenu)
        {
        	document = getDocument(xmlFile);
        	translateDocument();
        	control = new Control(actionTarget, this);
        	view = new AToolBarMenuView(rootEntity, (JFrame)holder, control);
        }
    }

    public AutoMenu(String xmlFile, JComponent holder, Object actionTarget)
    {
        classMap = null;
        buildStack = new Stack();
        if(this instanceof APopupMenu)
        {
            document = getDocument(xmlFile);
            translateDocument();
            control = new Control(actionTarget, this);
            view = new APopupMenuView(rootEntity, holder, control);
        }
    }

    public AutoMenu(String xmlFile, Component holder, Object actionTarget)
    {
        classMap = null;
        buildStack = new Stack();
        if(this instanceof APopupMenu)
        {
            document = getDocument(xmlFile);
            translateDocument();
            control = new Control(actionTarget, this);
            view = new APopupMenuView(rootEntity, holder, control);
        }
    }

    private Document getDocument(String xmlFile)
    {
        return DOMUtil.parse(xmlFile, false);
    }

    public void loadMenu(String xmlFile) {
    	document = getDocument(xmlFile);
    	translateDocument();
    	view.loadView(rootEntity);
    }

    /*
    private void saveDocument(String xmlFile, Document _document)
    {
        DOMUtil.writeXmlToFile(xmlFile, _document);
    }
    */

    private void translateDocument()
    {
        renderModel(document);
        menuLevel = -1;
    }

    public void renderModel(Document document)
    {
        NodeList _document = document.getChildNodes();
        _renderModel(_document);
    }

    private void _renderModel(NodeList nodelist)
    {
        if(nodelist != null)
        {
            int z = nodelist.getLength();
            for(int i = 0; i < z; i++)
            {
                short nodeType = nodelist.item(i).getNodeType();
                switch(nodeType)
                {
                default:
                    break;

                case 10: // '\n'
                    String classMapType = nodelist.item(i).getNodeName();
                    String classMapName = "ClassMap_" + classMapType;
                    try
                    {
                        classMap = (ClassMap)getClass().getClassLoader().loadClass("org.digitalmonks.automenu.model.automenu." + classMapName).newInstance();
                    }
                    catch(ClassNotFoundException e)
                    {
                        System.out.println("ClassNotFound \"" + classMapName + "\" - " + e.getMessage() + " - " + e.getLocalizedMessage());
                    }
                    catch(InstantiationException e)
                    {
                        System.out.println("InstantiationException \"" + classMapName + "\" - " + e.getMessage() + " - " + e.getLocalizedMessage());
                    }
                    catch(IllegalAccessException e)
                    {
                        System.out.println("IllegalAccess \"" + classMapName + "\" - " + e.getMessage() + " - " + e.getLocalizedMessage());
                    }
                    break;

                case 1: // '\001'
                    ModelEntity entity = makeModelfromDocumentNode(nodelist.item(i), classMap);
                    if(entity == null)
                        break;
                    if(buildStack.empty())
                        rootEntity = entity;
                    else
                        addIntoModel(entity);
                    NodeList nodeList = nodelist.item(i).getChildNodes();
                    if(nodeList != null)
                    {
                        buildStack.push(entity);
                        menuLevel++;
                        _renderModel(nodelist.item(i).getChildNodes());
                        menuLevel--;
                        buildStack.pop();
                    }
                    break;
                }
            }

        }
    }

    private ModelEntity makeModelfromDocumentNode(Node node, ClassMap classMap)
    {
        ModelEntity entity = null;
        String name = node.getNodeName();
        String className = (String)classMap.get(name);
        if(className != null)
        {
            try
            {
                entity = (ModelEntity)getClass().getClassLoader().loadClass(className).newInstance();
                entity.setAttribute("entityType", name);
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("ClassNotFound \"" + className + "\" - " + e.getMessage() + " - " + e.getLocalizedMessage());
            }
            catch(InstantiationException e)
            {
                System.out.println("InstantiationException \"" + className + "\" - " + e.getMessage() + " - " + e.getLocalizedMessage());
            }
            catch(IllegalAccessException e)
            {
                System.out.println("IllegalAccess \"" + className + "\" - " + e.getMessage() + " - " + e.getLocalizedMessage());
            }
            NamedNodeMap attributes = node.getAttributes();
            putAttributes(entity, attributes);
        }
        return entity;
    }

    private void putAttributes(ModelEntity entity, NamedNodeMap attributes)
    {
        String value;
        String name = value = null;
        int total = attributes.getLength();
        for(int i = 0; i < total; i++)
        {
            name = attributes.item(i).getNodeName();
            try
            {
                value = attributes.item(i).getNodeValue();
            }
            catch(DOMException d)
            {
                System.out.println("Erro ao obter atributo: " + d.getMessage());
            }
            entity.setAttribute(name, value);
        }

    }

    private void addIntoModel(ModelEntity entity)
    {
        ModelEntity holder = (ModelEntity)buildStack.lastElement();
        holder.add(entity);
    }

    protected void addHolder(JComponent holder)
    {
        if(view instanceof APopupMenuView)
            ((APopupMenuView)view).addHolder(holder);
    }

    
    public Font getFont() {
    	Font font = null;
        if(view instanceof APopupMenuView)
            font = ((APopupMenuView)view).getFont();
        if(view instanceof ABarMenuView)
            font = ((ABarMenuView)view).getFont();
        return font;
    }
    
    public void disableMapEnabled(String name, boolean state)
    {
        if(view instanceof APopupMenuView)
            ((APopupMenuView)view).disableMapEnabled(name, state);
        if(view instanceof ABarMenuView)
            ((ABarMenuView)view).disableMapEnabled(name, state);
    }
    
	public void setActionTarget(Object _actionTarget) {
		control.setActionTarget(_actionTarget);
	}

    public void setItemState(String xpath, boolean checked) {
    	view.setItemState(xpath,checked);
    }

    public void clickItem(String xpath) {
    	view.clickItem(xpath);
    }

    public void setItemVisible(String xpath, boolean visible) {
    	view.setItemVisible(xpath,visible);
    }

    private ModelEntity rootEntity;
    private Control control;
    private Document document;
    private View view;
    private ClassMap classMap;
    private static int menuLevel = -1;
    private Stack buildStack;

}
