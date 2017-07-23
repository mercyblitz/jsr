// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class TestContentHandler extends DefaultHandler {

  public StringBuffer buffer = new StringBuffer();
  
  public void characters(char[] ch, int start, int length) throws SAXException {
    for (int i = 0; i<length; i++)
      buffer.append(ch[start+i]);
  }

  public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    buffer.append("</");
    buffer.append(localName);
    buffer.append(">");
  }

  public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    buffer.append("<");
    buffer.append(localName);
    buffer.append(">");
  }
}