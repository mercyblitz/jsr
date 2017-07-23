// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;


public class TestXMLFilter extends XMLFilterImpl {
  
  InputSource inputSource;
  
  public TestXMLFilter(String document) throws SAXException {
    super(org.xml.sax.helpers.XMLReaderFactory.createXMLReader());
    inputSource = new InputSource(new StringReader(document));
  }
  
  public void parse(String systemId) throws IOException, SAXException {
     super.parse(inputSource);
    }       
}
