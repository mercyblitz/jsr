// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.SimpleTimeZone;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQItem;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQSequence;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.oracle.xqj.tck.TestXMLFilter;

public class XQDynamicContextTest extends XQJTestCase {

  public void testGetImplicitTimeZone() throws XQException {
    XQExpression xqe;
    
    xqe = xqc.createExpression();
    xqe.close();
    try {
      xqe.getImplicitTimeZone();
      junit.framework.Assert.fail("A-XQDC-1.1: getImplicitTimeZone() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqe = xqc.createExpression();
    try {
      // implementation dependent value is returned, don't test it
      xqe.getImplicitTimeZone();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-2.1: getImplicitTimeZone() failed with message: " + e.getMessage());
    }
    xqe.close(); 
  }

  public void testBindAtomicValue() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindAtomicValue(new QName("v"), "Hello world!", null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindAtomicValue() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindAtomicValue(null, "Hello world!", null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindAtomicValue(new QName("v"), "Hello world!", xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindAtomicValue(new QName("v"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_DECIMAL));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindAtomicValue(new QName("foo"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindAtomicValue(new QName("v"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindAtomicValue(new QName("v"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindAtomicValue() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello world!", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }

  public void testBindString() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindString(new QName("v"), "Hello world!", null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindString() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindString(null, "Hello world!", null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindString(new QName("v"), "Hello world!", xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindString(new QName("v"), "123", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindString(new QName("foo"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindString(new QName("v"), "Hello world!", null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindString(new QName("v"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindString() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello world!", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindString(new QName("v"), "Hello", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindString() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_NCNAME, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello", xqs.getObject());
    xqpe.close(); 
  }
  
  public void testBindDocument_String() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDocument(new QName("v"), "<e>Hello world!</e>", null, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDocument() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(null, "<e>Hello world!</e>", null, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), "<e>Hello world!</e>", null, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), "<e>", null, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("foo"), "<e>Hello world!</e>", null, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindDocument(new QName("v"), "<e>Hello world!</e>", null, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), "<e>Hello world!</e>", null, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindDocument_Reader() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDocument(new QName("v"), new StringReader("<e>Hello world!</e>"), null, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDocument() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(null, new StringReader("<e>Hello world!</e>"), null, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StringReader("<e>Hello world!</e>"), null, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StringReader("<e>"), null, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("foo"), new StringReader("<e>Hello world!</e>"), null, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StringReader("<e>Hello world!</e>"), null, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StringReader("<e>Hello world!</e>"), null, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindDocument_InputStream() throws XQException, UnsupportedEncodingException {
    
    // We don't expect this method ever to throw UnsupportedEncodingException, as we only request the "UTF-8" encoding.
    // However, in order to make the compiler happy, and to play it safe, add UnsupportedEncodingException to the throws clause.
    
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDocument(new QName("v"), new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDocument() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(null, new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>".getBytes("UTF-8")), null, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("foo"), new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?><e>Hello world!</e>".getBytes("UTF-8")), null, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindDocument_XMLReader() throws XQException, SAXException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDocument(new QName("v"), new TestXMLFilter("<e>Hello world!</e>"), null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDocument() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(null, new TestXMLFilter("<e>Hello world!</e>"), null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new TestXMLFilter("<e>Hello world!</e>"), xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new TestXMLFilter("<e>"), null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("foo"), new TestXMLFilter("<e>Hello world!</e>"), null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new TestXMLFilter("<e>Hello world!</e>"), null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new TestXMLFilter("<e>Hello world!</e>"), null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindDocument_XMLStreamReader() throws XQException {
    XQPreparedExpression xqpe;
    
    // expression used to create the input XMLStreamReader objects
    XQExpression xqe = xqc.createExpression();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDocument(new QName("v"), xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDocument() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(null, xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("foo"), xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindDocument(new QName("v"), xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), xqe.executeQuery("<e>Hello world!</e>").getSequenceAsStream(), null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqe.close();
  }
  
  public void testBindDocument_Source() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDocument(new QName("v"), new StreamSource(new StringReader("<e>Hello world!</e>")), null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDocument() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(null, new StreamSource(new StringReader("<e>Hello world!</e>")), null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StreamSource(new StringReader("<e>Hello world!</e>")), xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StreamSource(new StringReader("<e>")), null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("foo"), new StreamSource(new StringReader("<e>Hello world!</e>")), null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StreamSource(new StringReader("<e>Hello world!</e>")), null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDocument(new QName("v"), new StreamSource(new StringReader("<e>Hello world!</e>")), null);
    } catch (XQException e) {
      e.printStackTrace();
      junit.framework.Assert.fail("A-XQDC-1.7: bindDocument() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

  }
  
  public void testSetImplicitTimeZone() throws XQException {
    XQExpression xqe;
    
    xqe = xqc.createExpression();
    xqe.close();
    try {
      xqe.setImplicitTimeZone(new SimpleTimeZone(-28800000,"America/Los_Angeles"));
      junit.framework.Assert.fail("A-XQDC-1.1: setImplicitTimeZone() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqe = xqc.createExpression();
    xqe.close();
    try {
      xqe.setImplicitTimeZone(null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
    
    xqe = xqc.createExpression();
    try {
      xqe.setImplicitTimeZone(new SimpleTimeZone(-28800000,"America/Los_Angeles"));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-2.1: setImplicitTimeZone() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-2.1: setImplicitTimeZone() sets the timezone for this dynamic context.", new SimpleTimeZone(-28800000,"America/Los_Angeles"), xqe.getImplicitTimeZone());
    xqe.close(); 
  }
  
  public void testBindItem() throws XQException {
    XQPreparedExpression xqpe;
    
    // Create an XQItem, which we will use subsequently to test bindItem()
    XQItem xqi = xqc.createItemFromString("Hello world!", null);
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindItem(new QName("v"), xqi);
      junit.framework.Assert.fail("A-XQDC-1.1: bindItem() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindItem(null, xqi);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindItem(new QName("foo"), xqi);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindItem(new QName("v"), xqi);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindItem(new QName("v"),xqi);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindItem() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello world!", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindSequence() throws XQException {
    XQPreparedExpression xqpe;
    
    // Create an XQSequence, which we will use subsequently to test bindSequence()
    XQExpression xqe = xqc.createExpression();
    XQSequence xqs = xqc.createSequence(xqe.executeQuery("'Hello world!'"));
    xqe.close();
    
    xqs.beforeFirst();
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindSequence(new QName("v"), xqs);
      junit.framework.Assert.fail("A-XQDC-1.1: bindSequence() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqs.beforeFirst();
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindSequence(null, xqs);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqs.beforeFirst();
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindSequence(new QName("foo"), xqs);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqs.beforeFirst();
    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindSequence(new QName("v"), xqs);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqs.beforeFirst();
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindSequence(new QName("v"),xqs);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindSequence() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello world!", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }

  public void testBindObject() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindObject(new QName("v"), "Hello world!", null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindObject() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindObject(null, "Hello world!", null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindObject(new QName("v"), "Hello world!", xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindObject(new QName("v"), "123", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindObject(new QName("foo"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindObject(new QName("v"), "Hello world!", null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindObject(new QName("v"), "Hello world!", xqc.createAtomicType(XQItemType.XQBASETYPE_STRING));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindObject() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello world!", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindObject(new QName("v"), "Hello", xqc.createAtomicType(XQItemType.XQBASETYPE_NCNAME));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindObject() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_NCNAME, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "Hello", xqs.getObject());
    xqpe.close(); 
  }

  public void testBindObject_AllTypes() throws Exception {
    XQExpression xqe;
    XQSequence xqs;

    
    String msg = "A-XQDC-4.1: bindObject implements casting rules of '14.2 Mapping a Java Data Type to an XQuery Data Type'";

    boolean jdk14;
    try {
      Class.forName("javax.xml.datatype.XMLGregorianCalendar");
      jdk14 = false;
    } catch (Exception e) {
      // assume JDK 1.4
      jdk14 = true;
    }

    xqe = xqc.createExpression();
    
    xqe.bindObject(new QName("v"), Boolean.valueOf(true), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:boolean");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());

    xqe.bindObject(new QName("v"), new Byte((byte)1), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:byte");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), new Float(1), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:float");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), new Double(1), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:double");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), new Integer(1), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:int");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), new Long(1), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:long");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), new Short((short)1), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:short");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), "Hello world!", null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:string");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
   
    xqe.bindObject(new QName("v"), new BigDecimal("1"), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:decimal");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), new BigInteger("1"), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:integer");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    if (!jdk14) {
      XQExpression xqe_temp;
      XQSequence xqs_temp;
      
      xqe_temp = xqc.createExpression();      
      xqs_temp = xqe_temp.executeQuery("xs:dayTimeDuration('PT5H'), " +
                                       "xs:yearMonthDuration('P1M'), " +
                                       "xs:date('2000-12-31')," +
                                       "xs:dateTime('2000-12-31T12:00:00')," +
                                       "xs:gDay('---11')," +
                                       "xs:gMonth('--11')," +
                                       "xs:gMonthDay('--01-01')," +
                                       "xs:gYear('2000')," +
                                       "xs:gYearMonth('2000-01')," +
                                       "xs:time('12:12:12')");

      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:dayTimeDuration");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:yearMonthDuration");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:date");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:dateTime");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:gDay");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:gMonth");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:gMonthDay");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:gYear");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:gYearMonth");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqs_temp.next();
      xqe.bindObject(new QName("v"), xqs_temp.getObject(), null);
      xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:time");
      xqs.next();
      junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
      
      xqe_temp.close();
    }
    
    xqe.bindObject(new QName("v"), new QName("abc"), null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of xs:QName");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();          
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.newDocument();
    Element element = document.createElement("e");
    document.appendChild(element);
    DocumentFragment documentFragment = document.createDocumentFragment();
    Attr attribute = document.createAttribute("a");
    Comment comment = document.createComment("comment");
    ProcessingInstruction pi = document.createProcessingInstruction("target", "data");
    Text text = document.createTextNode("text");
    
    xqe.bindObject(new QName("v"), document, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of document-node()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), documentFragment, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of document-node()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), element, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of element()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), attribute, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of attribute()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), comment, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of comment()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), pi, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of processing-instruction()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.bindObject(new QName("v"), text, null);
    xqs = xqe.executeQuery("declare variable $v external; $v instance of text()");
    xqs.next();
    junit.framework.Assert.assertTrue(msg, xqs.getBoolean());
    
    xqe.close();
  }
  
  public void testBindBoolean() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindBoolean(new QName("v"), true, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindBoolean() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindBoolean(null, true, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindBoolean(new QName("v"), true, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindBoolean(new QName("foo"), true, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindBoolean(new QName("v"), true, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindBoolean(new QName("v"), true, xqc.createAtomicType(XQItemType.XQBASETYPE_BOOLEAN));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindBoolean() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "true", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindByte() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindByte(new QName("v"), (byte)1, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindByte() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindByte(null, (byte)1, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindByte(new QName("v"), (byte)1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    // Can't think of a way to verify A-XQDC-1.4 with the bindByte() method

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindByte(new QName("foo"), (byte)1, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:string external; $v");
    try {
      xqpe.bindByte(new QName("v"), (byte)1, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindByte(new QName("v"), (byte)1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindByte() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindByte(new QName("v"), (byte)1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindByte() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_INTEGER, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqs.getAtomicValue());
    xqpe.close(); 
  }

  public void testBindDouble() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindDouble(new QName("v"), 1d, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindDouble() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDouble(null, 1d, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDouble(new QName("v"), 1d, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    // Can't think of a way to verify A-XQDC-1.4 with the bindDouble() method

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDouble(new QName("foo"), 1d, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:string external; $v");
    try {
      xqpe.bindDouble(new QName("v"), 1d, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindDouble(new QName("v"), 1d, xqc.createAtomicType(XQItemType.XQBASETYPE_DOUBLE));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindDouble() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_DOUBLE, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertTrue("A-XQDC-1.7: Successful bindXXX.", 1d == xqs.getDouble());
    xqpe.close(); 
  }
  
  public void testBindFloat() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindFloat(new QName("v"), 1f, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindFloat() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindFloat(null, 1f, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindFloat(new QName("v"), 1f, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    // Can't think of a way to verify A-XQDC-1.4 with the bindFloat() method

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindFloat(new QName("foo"), 1f, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:string external; $v");
    try {
      xqpe.bindFloat(new QName("v"), 1f, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindFloat(new QName("v"), 1f, xqc.createAtomicType(XQItemType.XQBASETYPE_FLOAT));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindFloat() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_FLOAT, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertTrue("A-XQDC-1.7: Successful bindXXX.", 1f == xqs.getFloat());
    xqpe.close(); 
  }
  
  public void testBindInt() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindInt(new QName("v"), 1, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindInt() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindInt(null, 1, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindInt(new QName("v"), 1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindInt(new QName("v"), 128, xqc.createAtomicType(XQItemType.XQBASETYPE_BYTE));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindInt(new QName("foo"), 1, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:string external; $v");
    try {
      xqpe.bindInt(new QName("v"), 1, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindInt(new QName("v"), 1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindInt() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindInt(new QName("v"), 1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindInt() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_INTEGER, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqs.getAtomicValue());
    xqpe.close(); 
  }
  
  public void testBindLong() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindLong(new QName("v"), 1, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindLong() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindLong(null, 1, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindLong(new QName("v"), 1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindLong(new QName("v"), 128, xqc.createAtomicType(XQItemType.XQBASETYPE_BYTE));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindLong(new QName("foo"), 1, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:string external; $v");
    try {
      xqpe.bindLong(new QName("v"), 1, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindLong(new QName("v"), 1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindLong() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindLong(new QName("v"), 1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindLong() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_INTEGER, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqs.getAtomicValue());
    xqpe.close(); 
  }

  public void testBindNode() throws XQException, IOException, SAXException, ParserConfigurationException {
    XQPreparedExpression xqpe;

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder parser = factory.newDocumentBuilder();
    Document document = parser.parse(new InputSource(new StringReader("<e>Hello world!</e>")));
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindNode(new QName("v"), document, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindNode() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindNode(null, document, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindNode(new QName("v"), document, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindNode(new QName("foo"), document, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:decimal external; $v");
    try {
      xqpe.bindNode(new QName("v"), document, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindNode(new QName("v"), document, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindNode() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "<e>Hello world!</e>", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 
  }
  
  public void testBindShort() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.bindShort(new QName("v"), (short)1, null);
      junit.framework.Assert.fail("A-XQDC-1.1: bindShort() throws an XQException when the dynamic context is in closed state.");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindShort(null, (short)1, null);
      junit.framework.Assert.fail("A-XQDC-1.2: null argument is invalid and throws an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindShort(new QName("v"), (short)1, xqc.createCommentType());
      junit.framework.Assert.fail("A-XQDC-1.3: An invalid type of the value to be bound must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindShort(new QName("v"), (short)128, xqc.createAtomicType(XQItemType.XQBASETYPE_BYTE));
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.4: The conversion of the value to an XDM instance must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindShort(new QName("foo"), (short)1, null);
      junit.framework.Assert.fail("A-XQDC-1.5: The bound variable must be declared external in the prepared expression.");
    } catch (XQException e) {
      // Expect an XQException
    }  
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v as xs:string external; $v");
    try {
      xqpe.bindShort(new QName("v"), (short)1, null);
      xqpe.executeQuery().getSequenceAsString(null);
      junit.framework.Assert.fail("A-XQDC-1.6: The dynamic type of the bound value is not compatible with the static type of the variable and must fail.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    xqpe.close();

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindShort(new QName("v"), (short)1, null);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindShort() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqpe.executeQuery().getSequenceAsString(null));
    xqpe.close(); 

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.bindShort(new QName("v"), (short)1, xqc.createAtomicType(XQItemType.XQBASETYPE_INTEGER));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDC-1.7: bindShort() failed with message: " + e.getMessage());
    }
    XQSequence xqs = xqpe.executeQuery();
    xqs.next();
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQITEMKIND_ATOMIC, xqs.getItemType().getItemKind());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", XQItemType.XQBASETYPE_INTEGER, xqs.getItemType().getBaseType());
    junit.framework.Assert.assertEquals("A-XQDC-1.7: Successful bindXXX.", "1", xqs.getAtomicValue());
    xqpe.close(); 
  }
}
