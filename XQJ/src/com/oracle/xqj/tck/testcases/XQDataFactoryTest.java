// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQItem;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQSequence;
import javax.xml.xquery.XQSequenceType;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.oracle.xqj.tck.TestXMLFilter;

public class XQDataFactoryTest extends XQJTestCase {

  public void testCreateItemFromAtomicValue() throws XQException {

    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromAtomicValue("Hello world!", null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromAtomicValue() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromAtomicValue(null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
      e = null;
    }    

    try {
      xqc.createItemFromAtomicValue("Hello world!", xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromAtomicValue("Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_DECIMAL));
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromAtomicValue("Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromAtomicValue() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "Hello world!", xqi.getItemAsString(null));
  }

  public void testCreateItemFromString() throws XQException {
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromString("Hello world!", null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromString() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromString(null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromString("Hello world!", xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromString("123", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromString("Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromString() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "Hello world!", xqi.getItemAsString(null));

    try {
      xqi = xqc.createItemFromString("Hello", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromString() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_NCNAME, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "Hello", xqi.getObject());
  }
  
  public void testCreateItemFromDocument_String() throws XQException {

    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDocument("<e>Hello world!</e>", null, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDocument() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDocument((String)null, null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument("<e>Hello world!</e>", null, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument("<e>", null, null);
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDocument("<e>Hello world!</e>", null, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));
  }
  
  public void testCreateItemFromDocument_Reader() throws XQException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDocument(new StringReader("<e>Hello world!</e>"), null, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDocument() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDocument((Reader)null, null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new StringReader("<e>Hello world!</e>"), null, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new StringReader("<e>"), null, null);
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDocument(new StringReader("<e>Hello world!</e>"), null, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));
  }
  
  public void testCreateItemFromDocument_InputStream() throws XQException, UnsupportedEncodingException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDocument(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDocument() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDocument((InputStream)null, null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>".getBytes("UTF-8")), null, null);
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDocument(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));
  }
  
  public void testCreateItemFromDocument_XMLReader() throws XQException, SAXException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDocument(new TestXMLFilter("<e>Hello world!</e>"), null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDocument() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDocument((XMLReader)null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new TestXMLFilter("<e>Hello world!</e>"), xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new TestXMLFilter("<e>"), null);
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDocument(new TestXMLFilter("<e>Hello world!</e>"), null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));
  }
  
  public void testCreateItemFromDocument_XMLStreamReader() throws XQException {
    
    // expression used to create the input XMLStreamReader objects
    XQExpression xqe = xqc.createExpression();
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDocument(xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDocument() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDocument((XMLStreamReader)null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDocument(xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));
    
    xqe.close();
  }
  
  public void testCreateItemFromDocument_Source() throws XQException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDocument(new StreamSource(new StringReader("<e>Hello world!</e>")), null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDocument() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDocument((Source)null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new StreamSource(new StringReader("<e>Hello world!</e>")), xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromDocument(new StreamSource(new StringReader("<e>")), null);
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDocument(new StreamSource(new StringReader("<e>Hello world!</e>")), null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));

  }

  public void testCreateItemFromObject() throws XQException {
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromObject("Hello world!", null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromObject() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromObject(null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromObject("Hello world!", xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromObject("123", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    
    try {
      xqi = xqc.createItemFromObject("Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromObject() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful bindXXX.", "Hello world!", xqi.getItemAsString(null));

    try {
      xqi = xqc.createItemFromObject("Hello", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromObject() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_NCNAME, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "Hello", xqi.getObject());
  }

  public void testCreateItemFromBoolean() throws XQException {
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromBoolean(true, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromBoolean() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromBoolean(true, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromBoolean(true, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromBoolean() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "true", xqi.getItemAsString(null));
  }
  
  public void testCreateItemFromByte() throws XQException {
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromByte((byte)1, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromByte() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromByte((byte)1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    // Can't think of a way to verify A-XQDF-1.4 with the createItemFromByte() method

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromByte((byte)1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromByte() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "1", xqi.getItemAsString(null));

    try {
      xqi = xqc.createItemFromByte((byte)1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromByte() failed with message: " + e.getMessage());
    }

    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_INTEGER, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "1", xqi.getAtomicValue());
  }

  public void testCreateItemFromDouble() throws XQException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromDouble(1d, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromDouble() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromDouble(1d, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    // Can't think of a way to verify A-XQDF-1.4 with the createItemFromDouble() method

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromDouble(1d, xqc.createAtomicType(XQItemType.XQBASETYPE_DOUBLE));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDouble() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_DOUBLE, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertTrue("A-XQDF-1.5: Successful createItemFromXXX.", 1d == xqi.getDouble());
  }
  
  public void testCreateItemFromFloat() throws XQException {
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromFloat(1f, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromFloat() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromFloat(1f, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    // Can't think of a way to verify A-XQDF-1.4 with the createItemFromDouble() method

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromFloat(1f, xqc.createAtomicType(XQItemType.XQBASETYPE_FLOAT));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromDouble() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_FLOAT, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertTrue("A-XQDF-1.5: Successful createItemFromXXX.", 1d == xqi.getFloat());
}

  public void testCreateItemFromInt() throws XQException {
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromInt(1, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromInt() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromInt(1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromInt(128, xqc.createAtomicType(XQItemType.XQBASETYPE_BYTE));
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromInt(1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromInt() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful bindXXX.", "1",xqi.getItemAsString(null));

    try {
      xqi = xqc.createItemFromInt(1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromInt() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_INTEGER, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "1", xqi.getAtomicValue());
  }
  
  public void testCreateItemFromLong() throws XQException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromLong(1, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromLong() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromLong(1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromLong(128, xqc.createAtomicType(XQItemType.XQBASETYPE_BYTE));
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromLong(1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromLong() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful bindXXX.", "1",xqi.getItemAsString(null));

    try {
      xqi = xqc.createItemFromLong(1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromLong() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_INTEGER, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "1", xqi.getAtomicValue()); 
  }

  public void testCreateItemFromNode() throws XQException, IOException, SAXException, ParserConfigurationException {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder parser = factory.newDocumentBuilder();
    Document document = parser.parse(new InputSource(new StringReader("<e>Hello world!</e>")));

    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromNode(document, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromNode() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromNode(null, null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromNode(document, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    
    try {
      xqi = xqc.createItemFromNode(document, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromNode() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "<e>Hello world!</e>", xqi.getItemAsString(null));
  }
  
  public void testCreateItemFromShort() throws XQException {
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemFromShort((short)1, null);
      junit.framework.Assert.fail("A-XQDF-1.1: createItemFromShort() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItemFromShort((short)1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createItemFromShort((short)128, xqc.createAtomicType(XQItemType.XQBASETYPE_BYTE));
      junit.framework.Assert.fail("A-XQDF-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqi = null;
    try {
      xqi = xqc.createItemFromShort((short)1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromShort() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful bindXXX.", "1",xqi.getItemAsString(null));

    try {
      xqi = xqc.createItemFromShort((short)1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createItemFromLong() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQITEMKIND_ATOMIC, xqi.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", XQItemType.XQBASETYPE_INTEGER, xqi.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createItemFromXXX.", "1", xqi.getAtomicValue()); 
  }

  public void testCreateItem() throws XQException {

    // Create an XQItem, which we will use subsequently to test bindItem()
    XQItem xqi = xqc.createItemFromString("Hello world!", null);
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItem(xqi);
      junit.framework.Assert.fail("A-XQDF-1.1: createItem() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createItem(null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQItem xqresult = null;
    try {
      xqresult = xqc.createItem(xqi);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.7: createItem() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.7: Successful createItem().", "Hello world!", xqresult.getItemAsString(null));
  }
  
  public void testCreateSequence_FromSequence() throws XQException {
    XQSequence xqs;
    
    // prepared expression we use to create sequences
    XQPreparedExpression xqpe = xqc.prepareExpression("'Hello world!'");

    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    xqs = xqpe.executeQuery();
    try {
      xqdf.createSequence(xqs);
      junit.framework.Assert.fail("A-XQDF-1.1: createItem() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqdf.createSequence((XQSequence)null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQSequence xqsresult = null;
    try {
      xqsresult = xqc.createSequence(xqs);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createSequence() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createSequence.", "Hello world!", xqsresult.getSequenceAsString(null));
    
    xqpe.close(); 
  }
  
  public void testCreateSequence_FromIterator() throws XQException {
    
    List list = new LinkedList();
    list.add("Hello world!");
    list.add(xqc.createItemFromString("Hello world!", null));
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createSequence(list.iterator());
      junit.framework.Assert.fail("A-XQDF-1.1: createItem() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqdf.createSequence((Iterator)null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    XQSequence xqsresult = null;
    try {
      xqsresult = xqc.createSequence(list.iterator());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-1.5: createSequence() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-1.5: Successful createSequence.", "Hello world! Hello world!", xqsresult.getSequenceAsString(null));
  }

  public void testCreateAtomicType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createAtomicType(XQItemType.XQBASETYPE_UNTYPEDATOMIC);
      junit.framework.Assert.fail("A-XQDF-1.1: createAtomicType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqdf.createAtomicType(XQItemType.XQBASETYPE_UNTYPED);
      junit.framework.Assert.fail("A-XQDF-2.1: createAtomicType() detects invalid xqbasetype arguments.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqtype = xqc.createAtomicType(XQItemType.XQBASETYPE_UNTYPEDATOMIC);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-2.2: createAtomicType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", XQItemType.XQITEMKIND_ATOMIC, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", XQItemType.XQBASETYPE_UNTYPEDATOMIC, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", "untypedAtomic", xqtype.getTypeName().getLocalPart());
    
    try {
      xqtype = xqc.createAtomicType(XQItemType.XQBASETYPE_UNTYPEDATOMIC,
                                    new QName("http://www.w3.org/2001/XMLSchema", "untypedAtomic"),
                                    null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-2.2: createAtomicType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", XQItemType.XQITEMKIND_ATOMIC, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", XQItemType.XQBASETYPE_UNTYPEDATOMIC, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-2.2: Successful createAtomicType.", "untypedAtomic", xqtype.getTypeName().getLocalPart());
  }
  
  public void testCreateAttributeType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createAttributeType(new QName("a"), XQItemType.XQBASETYPE_INTEGER);
      junit.framework.Assert.fail("A-XQDF-1.1: createAttributeType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createAttributeType(new QName("a"), XQItemType.XQBASETYPE_UNTYPED);
      junit.framework.Assert.fail("A-XQDF-3.1: createAtomicType() detects invalid xqbasetype arguments.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqtype = xqc.createAttributeType(new QName("a"), XQItemType.XQBASETYPE_INTEGER);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-3.2: createAttributeType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", XQItemType.XQITEMKIND_ATTRIBUTE, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", XQItemType.XQBASETYPE_INTEGER, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", new QName("a"), xqtype.getNodeName());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", "integer", xqtype.getTypeName().getLocalPart());
    
    try {
      xqtype = xqc.createAttributeType(new QName("a"),
                                       XQItemType.XQBASETYPE_INTEGER,
                                       new QName("http://www.w3.org/2001/XMLSchema", "integer"),
                                       null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-3.2: createAtomicType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", XQItemType.XQITEMKIND_ATTRIBUTE, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", XQItemType.XQBASETYPE_INTEGER, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", new QName("a"), xqtype.getNodeName());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-3.2: Successful createAttributeType.", "integer", xqtype.getTypeName().getLocalPart());
  }
  
  public void testCreateSchemaAttributeType() throws XQException {
    // Optional feature, not tested
  }

  public void testCreateCommentType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createCommentType();
      junit.framework.Assert.fail("A-XQDF-1.1: createCommentType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createCommentType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-4.1: createCommentType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-4.1: Successful createCommentType.", XQItemType.XQITEMKIND_COMMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-4.1: Successful createCommentType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
  }
  
  public void testCreateDocumentElementType() throws XQException {
    XQItemType xqtype = null;

    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createDocumentElementType(xqc.createElementType(new QName("e"), XQItemType.XQBASETYPE_UNTYPED));
      junit.framework.Assert.fail("A-XQDF-1.1: createDocumentElementType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createDocumentElementType(xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDF-5.1: createDocumentElementType() detects invalid elementType argument.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createDocumentElementType(null);
      junit.framework.Assert.fail("A-XQDF-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }   

    try {
      xqtype = xqc.createDocumentElementType(xqc.createElementType(new QName("e"), XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-5.2: createDocumentElementType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-5.2: Successful createDocumentElementType.", XQItemType.XQITEMKIND_DOCUMENT_ELEMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-5.2: Successful createDocumentElementType.", XQItemType.XQBASETYPE_INTEGER, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-5.2: Successful createDocumentElementType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-5.2: Successful createDocumentElementType.", new QName("e"), xqtype.getNodeName());
    junit.framework.Assert.assertEquals("A-XQDF-5.2: Successful createDocumentElementType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-5.2: Successful createDocumentElementType.", "integer", xqtype.getTypeName().getLocalPart());
  }

  public void testCreateDocumentSchemaElementType() throws XQException {
    // Optional feature, not tested
  }

  public void testDocumentType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createDocumentType();
      junit.framework.Assert.fail("A-XQDF-1.1: createDocumentType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createDocumentType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-6.1: createDocumentType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-6.1: Successful createDocumentType.", XQItemType.XQITEMKIND_DOCUMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-6.1: Successful createDocumentType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
  }  

  public void testCreateElementType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createElementType(new QName("a"), XQItemType.XQBASETYPE_INTEGER);
      junit.framework.Assert.fail("A-XQDF-1.1: createElementType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createElementType(new QName("a"), XQItemType.XQBASETYPE_INTEGER);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-7.1: createElementType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", XQItemType.XQITEMKIND_ELEMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", XQItemType.XQBASETYPE_INTEGER, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", new QName("a"), xqtype.getNodeName());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", "integer", xqtype.getTypeName().getLocalPart());
    
    try {
      xqtype = xqc.createElementType(new QName("a"),
                                       XQItemType.XQBASETYPE_INTEGER,
                                       new QName("http://www.w3.org/2001/XMLSchema", "integer"),
                                       null,
                                       true);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-7.1: createElementType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", XQItemType.XQITEMKIND_ELEMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", XQItemType.XQBASETYPE_INTEGER, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", new QName("a"), xqtype.getNodeName());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-7.1: Successful createElementType.", "integer", xqtype.getTypeName().getLocalPart());
  }
  
  public void testCreateSchemaElementType() throws XQException {
    // Optional feature, not tested
  }

  public void testCreateItemType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createItemType();
      junit.framework.Assert.fail("A-XQDF-1.1: createItemType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createItemType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-8.1: createItemType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-8.1: Successful createItemType.", XQItemType.XQITEMKIND_ITEM, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-8.1: Successful createItemType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
  }  

  public void testCreateNodeType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createNodeType();
      junit.framework.Assert.fail("A-XQDF-1.1: createNodeType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createNodeType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-9.1: createNodeType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createNodeType.", XQItemType.XQITEMKIND_NODE, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createNodeType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
  }   

  public void testCreateProcessingInstructionType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createProcessingInstructionType(null);
      junit.framework.Assert.fail("A-XQDF-1.1: createProcessingInstructionType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createProcessingInstructionType(null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-9.1: createProcessingInstructionType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createProcessingInstructionType.", XQItemType.XQITEMKIND_PI, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createProcessingInstructionType.", (String)null, xqtype.getPIName());
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createProcessingInstructionType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    
    try {
      xqtype = xqc.createProcessingInstructionType("Hello");
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-9.1: createProcessingInstructionType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createProcessingInstructionType.", XQItemType.XQITEMKIND_PI, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createProcessingInstructionType.", "Hello", xqtype.getPIName());
    junit.framework.Assert.assertEquals("A-XQDF-9.1: Successful createProcessingInstructionType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
  }  
  
  public void testCreateTextType() throws XQException {
    XQItemType xqtype = null;
    
    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createTextType();
      junit.framework.Assert.fail("A-XQDF-1.1: createTextType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqtype = xqc.createTextType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-10.1: createTextType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-10.1: Successful createTextType.", XQItemType.XQITEMKIND_TEXT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-10.1: Successful createTextType.", XQItemType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
  }    

  public void testCreateSequenceType() throws XQException {
    XQSequenceType xqtype = null;

    XQConnection xqdf = xqds.getConnection();
    xqdf.close();
    try {
      xqdf.createSequenceType(xqc.createElementType(new QName("e"), XQItemType.XQBASETYPE_UNTYPED), XQSequenceType.OCC_EXACTLY_ONE);
      junit.framework.Assert.fail("A-XQDF-1.1: createSequenceType() throws an XQException when the data factory is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    try {
      xqc.createSequenceType(xqc.createElementType(new QName("e"), XQItemType.XQBASETYPE_UNTYPED), XQSequenceType.OCC_EMPTY);
      junit.framework.Assert.fail("A-XQDF-11.1: item type parameter of createSequenceType() must be null with occurrence indicator empty.");
    } catch (XQException e) {
      // Expect an XQException
    }    

    try {
      xqc.createSequenceType(null, XQSequenceType.OCC_EXACTLY_ONE);
      junit.framework.Assert.fail("A-XQDF-11.2: item type parameter of createSequenceType() must be not null with occurrence indicator different from empty.");
    } catch (XQException e) {
      // Expect an XQException
    }   

    try {
      xqc.createSequenceType(xqc.createElementType(new QName("e"), XQItemType.XQBASETYPE_UNTYPED), 777);
      junit.framework.Assert.fail("A-XQDF-11.3: createSequenceType() detects invalid occurrence indicator.");
    } catch (XQException e) {
      // Expect an XQException
    }  

    try {
      xqtype = xqc.createSequenceType(xqc.createElementType(new QName("e"), XQItemType.XQBASETYPE_INTEGER), XQSequenceType.OCC_ONE_OR_MORE);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDF-11.4: createSequenceType() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDF-11.4: Successful createSequenceType.", XQItemType.XQITEMKIND_ELEMENT, xqtype.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDF-11.4: Successful createSequenceType.", XQItemType.XQBASETYPE_INTEGER, xqtype.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDF-11.4: Successful createSequenceType.", XQItemType.OCC_ONE_OR_MORE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQDF-11.4: Successful createSequenceType.", new QName("e"), xqtype.getItemType().getNodeName());
    junit.framework.Assert.assertEquals("A-XQDF-11.4: Successful createSequenceType.", "http://www.w3.org/2001/XMLSchema", xqtype.getItemType().getTypeName().getNamespaceURI());
    junit.framework.Assert.assertEquals("A-XQDF-11.4: Successful createSequenceType.", "integer", xqtype.getItemType().getTypeName().getLocalPart());
  }
}
