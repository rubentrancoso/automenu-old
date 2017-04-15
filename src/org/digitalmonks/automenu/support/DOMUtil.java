package org.digitalmonks.automenu.support;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMUtil
{

    public DOMUtil()
    {
    }

    public static Document parse(String fileName, boolean validating)
    {
    	Document doc;
    	doc = null;
    	File f = new File(fileName);
    	if (f.exists())
    	{
    		doc = parseFile(fileName,validating);
    	}
    	else
    	{
        	doc = parseResource("/" + fileName,validating);
    	}
        return doc;
    }

    public static Document parseResource(String fileName, boolean validating)
    {
    	InputStream is = DOMUtil.class.getResourceAsStream(fileName);
    	if ( is != null ) 
    	{
	    	try
	        {
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            Document doc;
	            doc = factory.newDocumentBuilder().parse(is);
	            return doc;
	        }
	        catch(SAXException saxexception) { }
	        catch(ParserConfigurationException parserconfigurationexception) { }
	        catch(IOException ioexception) { }
    	}
        return null;
    }

    public static Document parseFile(String fileName, boolean validating)
    {

    	try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(validating);
            Document doc;
            doc = factory.newDocumentBuilder().parse(new File(fileName));
            return doc;
        }
        catch(SAXException saxexception) { }
        catch(ParserConfigurationException parserconfigurationexception) { }
        catch(IOException ioexception) { }
        return null;
    }
    
    public static void writeXmlToFile(String filename, Document document)
    {
        try
        {
            javax.xml.transform.Source source = new DOMSource(document);
            File file = new File(filename);
            javax.xml.transform.Result result = new StreamResult(file);
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        }
        catch(TransformerConfigurationException e)
        {
            System.out.println("TransformerConfigurationException: " + e);
        }
        catch(TransformerException e)
        {
            System.out.println("TransformerException: " + e);
        }
    }

    public static int countByTagName(String tag, Document document)
    {
        NodeList list = document.getElementsByTagName(tag);
        return list.getLength();
    }
}
