/*
 * Created on Oct 29, 2007
 */
package org.digitalmonks.automenu.support.I18N;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author pieter
 * 
 * Properties subclass with extra features : 
 * - if 'this' is packaged in a jarfile together with the propertyfiles, 
 *   then propertyfile lookups first
 * loads the defaults from the jarfile. Then properties can be overruled by
 * preference properties from the filesystem, if the propertyfile exists.
 * 
 * This way it's guaranteed that properties are always set, but can be overruled
 * at will.
 */
public class JarProperties {

	private static final long serialVersionUID = 1587915855035472311L;
	
	private Properties properties;
	String defaultPropertiesFilename = "properties/default.properties";
    String preferencePropertiesFilename = null;

    public JarProperties(String preferencePropertiesFilename, String defaultPropertiesFilename) {
        properties = new Properties();

        this.preferencePropertiesFilename = preferencePropertiesFilename;
        if (defaultPropertiesFilename != null)
            this.defaultPropertiesFilename = defaultPropertiesFilename;

        InputStream defaultPropertiesFile = null;
        InputStream preferencePropertiesFile = null;

        try {
            defaultPropertiesFile = openPropertyFile(defaultPropertiesFilename);
        } catch (FileNotFoundException e) {
            System.out.println("JarProperties: unable to open " + defaultPropertiesFilename);
        }

        try {
            if (preferencePropertiesFilename != null)
                preferencePropertiesFile = openPropertyFile(preferencePropertiesFilename);
        } catch (FileNotFoundException e) {
            System.out.println("JarProperties: unable to open " + preferencePropertiesFilename);
        }

        try {
        	if(defaultPropertiesFile != null)
        		properties.load(defaultPropertiesFile);
            if (preferencePropertiesFile != null)
            	properties.load(preferencePropertiesFile);
        } catch (IOException e) {
            System.out.println("JarProperties: IO exception on loading properties");
        }
    }

    public JarProperties(String preferencePropertiesFilename) {
        this(preferencePropertiesFilename, null);
    }

    public JarProperties() {
        this(null, null);
    }

    // The ResourceAnchor class is just here to identify the
    // Java archive that holds your resources.
    protected class ResourceAnchor {
        public ResourceAnchor() {
        }
    }

    protected InputStream openPropertyFile(String propertyFilename) throws FileNotFoundException {
        ClassLoader cl = ResourceAnchor.class.getClassLoader();
        InputStream in = null;

        try {
            in = new FileInputStream(propertyFilename);
        } catch (FileNotFoundException e) {
            in = cl.getResourceAsStream(propertyFilename);
            if (in == null)
                throw e;
        }

        return in;
    }

    public String getString(String key) {
    	if(key != null)
    		return properties.getProperty(key);
    	else
    		return null;
    }

//    public static Document parse(String fileName, boolean validating)
//    {
//    	Document doc;
//    	doc = null;
//    	File f = new File(fileName);
//    	if (f.exists())
//    	{
//    		doc = parseFile(fileName,validating);
//    	}
//    	else
//    	{
//        	doc = parseResource("/" + fileName,validating);
//    	}
//        return doc;
//    }
    
//    public static Document parseResource(String fileName, boolean validating)
//    {
//    	InputStream is = DOMUtil.class.getResourceAsStream(fileName);
//    	if ( is != null ) 
//    	{
//	    	try
//	        {
//	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	            Document doc;
//	            doc = factory.newDocumentBuilder().parse(is);
//	            return doc;
//	        }
//	        catch(SAXException saxexception) { }
//	        catch(ParserConfigurationException parserconfigurationexception) { }
//	        catch(IOException ioexception) { }
//    	}
//        return null;
//    }
//
//    public static Document parseFile(String fileName, boolean validating)
//    {
//
//    	try
//        {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setValidating(validating);
//            Document doc;
//            doc = factory.newDocumentBuilder().parse(new File(fileName));
//            return doc;
//        }
//        catch(SAXException saxexception) { }
//        catch(ParserConfigurationException parserconfigurationexception) { }
//        catch(IOException ioexception) { }
//        return null;
//    }

    
}