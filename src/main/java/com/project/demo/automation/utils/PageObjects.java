package com.project.demo.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class PageObjects {

    private Document doc = null;
    private Node xmlElement = null;
    private Map<String, String> mapElement = new HashMap<String, String>();

    private String xmlElementNode = "element";
    private String fileName = "";
    String xmlPageNode = "page";

    //TODO
    //Remove the reference of this default constructor
    public PageObjects() {
    }

    public PageObjects(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getMapElement() {
        return mapElement;
    }

    Logger log = Logger.getLogger(PageObjects.class);

    public void getDOM(String xmlFileName) throws Exception {
        File fXmlFile = new File(xmlFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
    }

    public NodeList getAllElementsInModule(String strModuleName, String xmlPageNode) {

        NodeList nodeModules = null;
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            String strXpath = "//" + xmlPageNode + "[@name='" + strModuleName + "']//*";
            XPathExpression expr = xpath.compile(strXpath);
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            nodeModules = (NodeList) result;
        } catch (Exception e) {
            //log.error("getAllElementsInModule: ");
            //log.error(e.getMessage());
        }
        return nodeModules;
    }

    //Return xml element xpath
    public void getXMLElement(String strElementName, String xmlElementNode) {
        String strXpath;
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            if (strElementName.contains(".")) {
                String strPage = strElementName.split("\\.")[0];
                String strElement = strElementName.split("\\.")[1];
                strXpath = "//" + xmlElement + "[@name='" + strPage + "']/element[@name='" + strElement + "']";
            } else {
                strXpath = "//" + xmlElementNode + "[@name='" + strElementName + "']";
            }
            XPathExpression expr = xpath.compile(strXpath);
            Object result = expr.evaluate(doc, XPathConstants.NODE);
            xmlElement = (Node) result;
        } catch (Exception e) {
            log.error("getXMLElement: ");
            log.error(e.getMessage());
        }
    }

    public void getElementProperties() {
        try {
            if (xmlElement != null) {
                mapElement.put("name", xmlElement.getAttributes().getNamedItem("name").getNodeValue());
                if (xmlElement.getAttributes().getNamedItem("mandatory") != null) {
                    mapElement.put("mandatory", xmlElement.getAttributes().getNamedItem("mandatory").getNodeValue());
                }
                mapElement.put("by", xmlElement.getAttributes().getNamedItem("by").getNodeValue());
                mapElement.put("identifier", xmlElement.getAttributes().getNamedItem("identifier").getNodeValue());
            }
        } catch (Exception e) {
            log.error("getElementProperties: ");
            log.error(e.getMessage());
        }
    }

    public String getIdentifier(String xmlPageName, String strElementName) {
        String identifier = null;
        try {
            getDOM(fileName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        ElementsOnModule(xmlPageName, strElementName);
        String strIdentifier = null;
        if (!mapElement.isEmpty()) {
            strIdentifier = mapElement.get("identifier");
            mapElement.clear();
        }
        return strIdentifier;
    }

    private void ElementsOnModule(String xmlPageName, String strElementName) {
        getAllElementsInModule(xmlPageNode, xmlPageName);
        getAllElementsInModule(xmlPageName, xmlPageNode);
        getXMLElement(strElementName, xmlElementNode);
        getElementProperties();
    }

    public enum Identifier {
        css(1), xpath(2), name(3), linkText(4),
        id(5), partialLinkText(6), tagName(7), className(8);

        private int value;

        private Identifier(int value) {
            this.value = value;
        }
    }

    public By getByElement(String xmlPageName, String strElementName) {
        By by = null;
        try {
            getDOM(fileName);
            ElementsOnModule(xmlPageName, strElementName);
            if (!mapElement.isEmpty()) {
                String strBy = mapElement.get("by");

                Identifier id = Identifier.valueOf(strBy);
                switch (id) {
                    case css:
                        by = By.cssSelector(getIdentifier(xmlPageName, strElementName));
                        break;
                    case xpath:
                        by = By.xpath(getIdentifier(xmlPageName, strElementName));
                        break;
                    case name:
                        by = By.name(getIdentifier(xmlPageName, strElementName));
                        break;
                    case linkText:
                        by = By.linkText(getIdentifier(xmlPageName, strElementName));
                        break;
                    case id:
                        by = By.id(getIdentifier(xmlPageName, strElementName));
                        break;
                    case partialLinkText:
                        by = By.partialLinkText(getIdentifier(xmlPageName, strElementName));
                        break;
                    case tagName:
                        by = By.tagName(getIdentifier(xmlPageName, strElementName));
                        break;
                    case className:
                        by = By.className(getIdentifier(xmlPageName, strElementName));
                        break;
                    default:
                        break;
                }
                mapElement.clear();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return by;
    }

    public String getMandatoryIndicator(String xmlPageName, String strElementName) {
        try {
            getDOM(fileName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        ElementsOnModule(xmlPageName, strElementName);
        String strIndicator = null;
        if (!mapElement.isEmpty()) {
            strIndicator = mapElement.get("mandatory");
        }
        mapElement.clear();
        return strIndicator;
    }
}

