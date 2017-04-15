package org.digitalmonks.automenu.model;

import java.util.Hashtable;
import java.util.Vector;

public abstract class ModelEntity
{

    public ModelEntity()
    {
        //atributos = new Hashtable<String, Object>();
        //links = new Hashtable<Object, Integer>();
        //vlinks = new Vector<ModelEntity>();

        atributos = new Hashtable();
        links = new Hashtable();
        vlinks = new Vector();
    }

    public void setAttribute(String key, Object value)
    {
        atributos.put(key, value);
    }

    public Object getAttribute(String key)
    {
        Object attribute = atributos.get(key);
        return attribute;
    }

    public void add(ModelEntity entity)
    {
        vlinks.addElement(entity);
        int index = vlinks.indexOf(entity);
        links.put(entity.getAttribute("entityType"), Integer.getInteger(null, index));
    }

    public int linkCount()
    {
        return vlinks.size();
    }

    public Object getLink(int index)
    {
        return vlinks.elementAt(index);
    }

    //private Hashtable<String, Object> atributos;
    //private Hashtable<Object, Integer> links;
    //private Vector<ModelEntity> vlinks;

    private Hashtable atributos;
    private Hashtable links;
    private Vector vlinks;
}
