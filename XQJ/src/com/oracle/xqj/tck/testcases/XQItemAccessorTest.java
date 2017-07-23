// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xquery.*;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.oracle.xqj.tck.TestContentHandler;

public class XQItemAccessorTest extends XQJTestCase {

  public void testGetBoolean() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'true'");
    xqs.next();
    try {
      xqs.getBoolean();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to boolean should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:boolean('true')");
    try {
      xqs.getBoolean();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:boolean('true')");
    xqs.next();
    xqs.close();
    try {
      xqs.getBoolean();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:boolean('true')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getBoolean();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:boolean('true')");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getBoolean on xs:boolean failed", true, xqs.getBoolean());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getBoolean on xs:boolean failed with message: " + e.getMessage());
    }
    xqe.close();
  }  
  
  public void testGetByte() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getByte();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to byte should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:byte('1')");
    try {
      xqs.getByte();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:byte('1')");
    xqs.next();
    xqs.close();
    try {
      xqs.getByte();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:byte('1')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getByte();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:decimal('1.1')");
    xqs.next();
    try {
      xqs.getByte();
      junit.framework.Assert.fail("A-XQIA-1.5: getByte on xs:decimal out of value space of byte should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:byte('1'), 10.0");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getByte on xs:byte failed", 1, xqs.getByte());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getByte on xs:byte failed with message: " + e.getMessage());
    }
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getByte on xs:decimal failed", 10, xqs.getByte());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getByte on xs:decimal failed with message: " + e.getMessage());
    }
    xqe.close();
  }  

  public void testGetDouble() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getDouble();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to double should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:double('1')");
    try {
      xqs.getDouble();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:double('1')");
    xqs.next();
    xqs.close();
    try {
      xqs.getDouble();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:double('1')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getDouble();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:double('1')");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getDouble on xs:double failed", 1E0, xqs.getDouble(),0);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6:  getDouble on xs:double failed with message: " + e.getMessage());
    }
    xqe.close();
  }    

  public void testGetFloat() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getFloat();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to float should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:float('1')");
    try {
      xqs.getFloat();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:float('1')");
    xqs.next();
    xqs.close();
    try {
      xqs.getFloat();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:float('1')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getFloat();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:float('1')");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getFloat on xs:float failed", 1E0, xqs.getFloat(),0);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getFloat on xs:float failed with message: " + e.getMessage());
    }
    xqe.close();
  }  

  public void testGetInt() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getInt();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to int should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:int('1')");
    try {
      xqs.getInt();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:int('1')");
    xqs.next();
    xqs.close();
    try {
      xqs.getInt();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:int('1')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getInt();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:decimal('1.1')");
    xqs.next();
    try {
      xqs.getInt();
      junit.framework.Assert.fail("A-XQIA-1.5: getInt on xs:decimal out of value space of int should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:int('1'), 10.0");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getInt on xs:int failed", 1, xqs.getInt());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getInt on xs:int failed with message: " + e.getMessage());
    }
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getInt on xs:decimal failed", 10, xqs.getInt());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getInt on xs:decimal failed with message: " + e.getMessage());
    }
    xqe.close();
  }  
  
  public void testGetItemType() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    XQItemType xqtype;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    try {
      xqs.getItemType();
      junit.framework.Assert.fail("A-XQIA-6.1: getItemType() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    xqs.close();
    try {
      xqs.getItemType();
      junit.framework.Assert.fail("A-XQIA-6.2: closed item accessor supports getItemType()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'Hello world!'");
    xqs.next();
    try {
      xqtype = xqs.getItemType();
      junit.framework.Assert.assertNotNull("A-XQIA-6.3: getItemType() failed", xqtype);
      junit.framework.Assert.assertEquals("A-XQIA-6.3: getItemType() failed", XQItemType.XQITEMKIND_ATOMIC, xqtype.getItemKind());
      junit.framework.Assert.assertEquals("A-XQIA-6.3: getItemType() failed", XQItemType.XQBASETYPE_STRING, xqtype.getBaseType());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-6.3: getItemType() failed with message: " + e.getMessage());
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("1");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getItemType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-6.4: getItemType() failed with message: " + e.getMessage());
    }
    xqe.close();
  }
  
  public void testGetAtomicValue() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    try {
      xqs.getAtomicValue();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    xqs.close();
    try {
      xqs.getAtomicValue();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getAtomicValue();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("1, 1.1");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-2.1: getAtomicValue on xs:string failed", "1", xqs.getAtomicValue());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-2.1: getAtomicValue on xs:string failed with message: " + e.getMessage());
    }
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-2.1: getAtomicValue on xs:decimal failed", "1.1", xqs.getAtomicValue());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-2.1: getAtomicValue on xs:decimal failed with message: " + e.getMessage());
    }
    xqe.close();
  }
  
  public void testGetLong() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getLong();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to long should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:long('1')");
    try {
      xqs.getLong();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:long('1')");
    xqs.next();
    xqs.close();
    try {
      xqs.getLong();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:long('1')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getLong();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:decimal('1.1')");
    xqs.next();
    try {
      xqs.getLong();
      junit.framework.Assert.fail("A-XQIA-1.5: getLong on xs:decimal out of value space of long should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:long('1'), 10.0");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getLong on xs:long failed", 1, xqs.getLong());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getLong on xs:long failed with message: " + e.getMessage());
    }
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getLong on xs:decimal failed", 10, xqs.getLong());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getLong on xs:decimal failed with message: " + e.getMessage());
    }
    xqe.close();
  }    
  
  public void testGetNode() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    org.w3c.dom.Node node = null;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getNode();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to org.w3c.dom.Node should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    try {
      xqs.getNode();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    xqs.close();
    try {
      xqs.getNode();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getNode();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>, <e a=''/>/@*");
    xqs.next();
    try {
      node = xqs.getNode();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-3.1: getNode on element() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQIA-3.1: getNode on element() failed", true, node instanceof org.w3c.dom.Element);
    junit.framework.Assert.assertEquals("A-XQIA-3.1: getNode on element() failed", "e", node.getLocalName());
    xqs.next();
    try {
      node = xqs.getNode();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-3.1: getNode on attribute() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQIA-3.1: getNode on attribute() failed", true, node instanceof org.w3c.dom.Attr);
    junit.framework.Assert.assertEquals("A-XQIA-3.1: getNode on attribute() failed", "a", node.getLocalName());
    xqe.close();
  }

  public void testGetNodeUri() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getNodeUri();
      junit.framework.Assert.fail("A-XQIA-5.1: getNodeUri() should fail if current item is not a node");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    try {
      xqs.getNodeUri();
      junit.framework.Assert.fail("A-XQIA-5.2: getNodeUri() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    xqs.close();
    try {
      xqs.getNodeUri();
      junit.framework.Assert.fail("A-XQIA-5.3: closed item accessor supports getNodeUri()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    try {
      xqs.getNodeUri(); // returned value is implementation defined
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-5.4: getNodeUri() failed with message: " + e.getMessage());
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getNodeUri(); // returned value is implementation defined
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-5.5: getNodeUri() failed with message: " + e.getMessage());
    }
    xqe.close();
  }

  public void testGetObject() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    Object object = null;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    try {
      xqs.getObject();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    xqs.close();
    try {
      xqs.getObject();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getObject();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e/>, 'Hello world!'");
    xqs.next();
    try {
      object = xqs.getObject();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-4.1: getObject on element() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQIA-4.1: getObject on element() failed", true, object instanceof org.w3c.dom.Element);
    junit.framework.Assert.assertEquals("A-XQIA-4.1: getObject on element() failed", "e", ((org.w3c.dom.Node)object).getLocalName());
    xqs.next();
    try {
      object = xqs.getObject();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-4.1: getObject on xs:string failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQIA-4.1: getObject on xs:string failed", true, object instanceof String);
    junit.framework.Assert.assertEquals("A-XQIA-4.1: getObject on xs:string failed", "Hello world!", (String)object);
    xqe.close();
  }

  public void testGetObject_AllTypes() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:anyURI('http://www.foo.org')," +
                           "xs:base64Binary('AAAA')," +
                           "xs:boolean('true')," +
                           "xs:byte('1')," +
                           "xs:date('2000-12-31')," +
                           "xs:dateTime('2000-12-31T12:00:00')," +
                           "xs:decimal('1')," +
                           "xs:double('1')," +
//                           "xs:duration()" +
                           "xs:ENTITY('AAA')," +
                           "xs:float('1')," +
                           "xs:gDay('---11')," +
                           "xs:gMonth('--11')," +
                           "xs:gMonthDay('--01-01')," +
                           "xs:gYear('2000')," +
                           "xs:gYearMonth('2000-01')," +
                           "xs:hexBinary('AA')," +
                           "xs:ID('AA')," +
                           "xs:IDREF('AA')," +
                           "xs:int('1')," +
                           "xs:integer('1')," +
                           "xs:language('en-US')," +
                           "xs:long('1')," +
                           "xs:Name('AAA')," +
                           "xs:NCName('AAA')," +
                           "xs:negativeInteger('-1')," +
                           "xs:NMTOKEN('AAA')," +
                           "xs:nonNegativeInteger('1')," +
                           "xs:nonPositiveInteger('-1')," +
                           "xs:normalizedString('AAA')," +
//                           "xs:NOTATION()," +
                           "xs:positiveInteger('1')," +
                           "xs:QName('AAA')," +
                           "xs:short('1')," +
                           "xs:string('AAA')," +
                           "xs:time('12:12:12')," +
                           "xs:token('AAA')," +
                           "xs:unsignedByte('1')," +
                           "xs:unsignedInt('1')," +
                           "xs:unsignedLong('1')," +
                           "xs:unsignedShort('1')," +
                           "xs:dayTimeDuration('PT5H')," +
                           "xs:untypedAtomic('AAA')," +
                           "xs:yearMonthDuration('P1M')," +
                           "<e a=\"{''}\"/>/@a," +
                           "<!-- comment -->, " +
                           "document{<e/>}," +
                           "<e/>, " +
                           "processing-instruction {'a'} {'b'}," +
                           "<e>text</e>/text()");
    Class xmlGregorianCalendar = null;
    Class duration = null;
    try {
      xmlGregorianCalendar = Class.forName("javax.xml.datatype.XMLGregorianCalendar");
      duration = Class.forName("javax.xml.datatype.Duration");
    } catch (Exception e) {
      // assume JDK 1.4
    }
    try {
      String msg = "A-XQIA-4.1: getObject implements casting rules of '14.4 Mapping an XQuery Atomic Value to a Java Object Type' ";
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:anyURI", xqs.getObject() instanceof String);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:base64Binary", xqs.getObject() instanceof byte[]);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:boolean", xqs.getObject() instanceof Boolean);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:byte", xqs.getObject() instanceof Byte);
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:date", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:dateTime", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:decimal", xqs.getObject() instanceof BigDecimal);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:double", xqs.getObject() instanceof Double);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:ENTITY", xqs.getObject() instanceof String);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:float", xqs.getObject() instanceof Float);
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:gDay", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:gMonth", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:MonthDay", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:Year", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:YearMonth", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:hexBinary", xqs.getObject() instanceof byte[]); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:ID", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:IDREF", xqs.getObject() instanceof String);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:int", xqs.getObject() instanceof Integer);  
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:integer", xqs.getObject() instanceof BigInteger); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:language", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:long", xqs.getObject() instanceof Long); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:Name", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:NCName", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:negativeInteger", xqs.getObject() instanceof BigInteger); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:NMTOKEN", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:nonNegativeInteger", xqs.getObject() instanceof BigInteger); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:nonPostiveInteger", xqs.getObject() instanceof BigInteger); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:normalizedString", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:postiveInteger", xqs.getObject() instanceof BigInteger); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:QName", xqs.getObject() instanceof QName); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:short", xqs.getObject() instanceof Short);  
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:string", xqs.getObject() instanceof String); 
      xqs.next();
      if (xmlGregorianCalendar != null) junit.framework.Assert.assertTrue(msg + "for xs:time", xmlGregorianCalendar.isInstance(xqs.getObject()));
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:token", xqs.getObject() instanceof String); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:unsignedByte", xqs.getObject() instanceof Short);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:unsignedInt", xqs.getObject() instanceof Long); 
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:unsignedLong", xqs.getObject() instanceof BigInteger);    
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:unsignedShort", xqs.getObject() instanceof Integer); 
      xqs.next();
      if (duration != null) junit.framework.Assert.assertTrue(msg + "for xs:dayTimeDuration", duration.isInstance(xqs.getObject()));
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for xs:untypedAtomic", xqs.getObject() instanceof String); 
      xqs.next();
      if (duration != null) junit.framework.Assert.assertTrue(msg + "for xs:yearMonthDuration", duration.isInstance(xqs.getObject()));
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for attribute", xqs.getObject() instanceof org.w3c.dom.Attr);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for comment", xqs.getObject() instanceof org.w3c.dom.Comment);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for document", xqs.getObject() instanceof org.w3c.dom.Document);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for element", xqs.getObject() instanceof org.w3c.dom.Element);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for processing instruction", xqs.getObject() instanceof org.w3c.dom.ProcessingInstruction);
      xqs.next();
      junit.framework.Assert.assertTrue(msg + "for text", xqs.getObject() instanceof org.w3c.dom.Text);
      } catch (XQException e) {
        e.printStackTrace();
       junit.framework.Assert.fail("A-XQIA-4.1: getObject on element() failed with message: " + e.getMessage());
    }
  }
  
  public void testGetItemAsStream() throws XQException { 
    XQExpression xqe;
    XQSequence xqs;
    XMLStreamReader xmlReader = null;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    try {
      xqs.getItemAsStream();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.close();
    try {
      xqs.getItemAsStream();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getItemAsStream();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e a='Hello world!'/>/@*");
    xqs.next();
    try {
      xmlReader = xqs.getItemAsStream();
      while (xmlReader.hasNext())
        xmlReader.next();
      junit.framework.Assert.fail("A-XQIA-8.1: serialization process fails when sequence contains a top-level attribute");
    } catch (XQException xq) {
      // Expect an XQException or XMLStreamException
    } catch (XMLStreamException xml) {
      // Expect an XQException or XMLStreamException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    try {
      xmlReader = xqs.getItemAsStream();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-9.1: getItemAsStream failed with message: " + e.getMessage());
    }
    try {
      junit.framework.Assert.assertNotNull("A-XQIA-9.1: getItemAsStream returned a null XMLStreamReader", xmlReader);
      junit.framework.Assert.assertEquals("A-XQIA-9.1: unexpected first event returned by XMLStreamReader", XMLStreamReader.START_DOCUMENT, xmlReader.getEventType());
      junit.framework.Assert.assertEquals("A-XQIA-9.1: unexpected second event returned by XMLStreamReader", XMLStreamReader.START_ELEMENT, xmlReader.next());
      junit.framework.Assert.assertEquals("A-XQIA-9.1: unexpected third event returned by XMLStreamReader", XMLStreamReader.CHARACTERS, xmlReader.next());
      junit.framework.Assert.assertEquals("A-XQIA-9.1: unexpected fourth event returned by XMLStreamReader", XMLStreamReader.END_ELEMENT, xmlReader.next());
      junit.framework.Assert.assertEquals("A-XQIA-9.1: unexpected fifth event returned by XMLStreamReader", XMLStreamReader.END_DOCUMENT, xmlReader.next());
    } catch (XMLStreamException e) {
      junit.framework.Assert.fail("A-XQIA-9.1: XMLStreamReader.next() failed with message: " + e.getMessage());
    }    
    xqe.close();    
  }
  
  public void testGetItemAsString() throws XQException { 
    XQExpression xqe;
    XQSequence xqs;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    try {
      xqs.getItemAsString(new Properties());
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.close();
    try {
      xqs.getItemAsString(new Properties());
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getItemAsString(new Properties());
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e a='Hello world!'/>/@*");
    xqs.next();
    try {
      xqs.getItemAsString(new Properties());
      junit.framework.Assert.fail("A-XQIA-8.1: serialization process fails when sequence contains a top-level attribute");
    } catch (XQException xq) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    String result = null;
    try {
      result = xqs.getItemAsString(new Properties());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-10.1: getItemAsString failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertTrue("A-XQIA-10.1: Expects serialized result contains '<e>Hello world!</e>', but it is '" + result + "'", result.indexOf("<e>Hello world!</e>") != -1);
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-10.2: null properties argument is equivalent to empty properties argument", result, xqs.getItemAsString(null));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-10.2: getItemAsString failed with message: " + e.getMessage());
    }
    xqe.close();
  }
  
  public void testGetShort() throws XQException {
    XQExpression xqe;
    XQSequence xqs;

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    try {
      xqs.getShort();
      junit.framework.Assert.fail("A-XQIA-1.1: conversion to short should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:short('1')");
    try {
      xqs.getShort();
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:short('1')");
    xqs.next();
    xqs.close();
    try {
      xqs.getShort();
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:short('1')");
    xqs.next();
    xqs.getItem();
    try {
      xqs.getShort();
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:decimal('1.1')");
    xqs.next();
    try {
      xqs.getShort();
      junit.framework.Assert.fail("A-XQIA-1.5: getShort on xs:decimal out of value space of short should fail");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("xs:short('1'), 10.0");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getShort on xs:short failed", 1, xqs.getShort());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getShort on xs:short failed with message: " + e.getMessage());
    }
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-1.6: getShort on xs:decimal failed", 10, xqs.getShort());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-1.6: getShort on xs:decimal failed with message: " + e.getMessage());
    }
    xqe.close();
  }
  
  public void testInstanceOf() throws XQException { 
    XQExpression xqe;
    XQSequence xqs;
    XQItemType xqstringtype;
    XQItemType xqinttype;
    
    xqstringtype = xqc.createAtomicType(XQItemType.XQBASETYPE_STRING);
    xqinttype = xqc.createAtomicType(XQItemType.XQBASETYPE_INT);

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    try {
      xqs.instanceOf(xqstringtype);
      junit.framework.Assert.fail("A-XQIA-7.1: instanceOf() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'1'");
    xqs.next();
    xqs.close();
    try {
      xqs.instanceOf(xqstringtype);
      junit.framework.Assert.fail("A-XQIA-7.2: closed item accessor supports instanceOf()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'Hello world!'");
    xqs.next();
    try {
      junit.framework.Assert.assertEquals("A-XQIA-7.3: instanceOf failed", true, xqs.instanceOf(xqstringtype));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-7.3: instanceOf() failed with message: " + e.getMessage());
    }
    try {
      junit.framework.Assert.assertEquals("A-XQIA-7.4: instanceOf failed", false, xqs.instanceOf(xqinttype));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-7.4: instanceOf() failed with message: " + e.getMessage());
    }
    xqe.close();
  }

  public void testWriteItem_OutputStream() throws XQException, UnsupportedEncodingException {

    // We don't expect this method ever to throw UnsupportedEncodingException, as we only request the "UTF-8" encoding.
    // However, in order to make the compiler happy, and to play it safe, add UnsupportedEncodingException to the throws clause.
    
    XQExpression xqe;
    XQSequence xqs;
    
    Properties prop = new Properties();
    prop.setProperty("encoding", "UTF-8");
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    try {
      xqs.writeItem(new ByteArrayOutputStream(), prop);
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.close();
    try {
      xqs.writeItem(new ByteArrayOutputStream(), prop);
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.writeItem(new ByteArrayOutputStream(), prop);
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e a='Hello world!'/>/@*");
    xqs.next();
    try {
      xqs.writeItem(new ByteArrayOutputStream(), prop);
      junit.framework.Assert.fail("A-XQIA-8.1: serialization process fails when sequence contains a top-level attribute");
    } catch (XQException xq) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    try {
      xqs.writeItem((OutputStream)null, prop);
      junit.framework.Assert.fail("A-XQIA-11.3: writeItem accepts a null buffer as first argument");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    try {
      xqs.writeItem(result, prop);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-11.1: writeItem failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertTrue("A-XQIA-11.1: Expects serialized result contains '<e>Hello world!</e>', but it is '" + result.toString("UTF-8") + "'", result.toString("UTF-8").indexOf("<e>Hello world!</e>") != -1);
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    ByteArrayOutputStream otherResult = new ByteArrayOutputStream();
    try {
      xqs.writeItem(otherResult, prop);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-11.2: writeItem failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQIA-11.2: null properties argument is equivalent to empty properties argument", result.toString("UTF-8"), otherResult.toString("UTF-8"));
    xqe.close();
  }

  public void testWriteItem_Writer() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    try {
      xqs.writeItem(new StringWriter(), new Properties());
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.close();
    try {
      xqs.writeItem(new StringWriter(), new Properties());
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.writeItem(new StringWriter(), new Properties());
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e a='Hello world!'/>/@*");
    xqs.next();
    try {
      xqs.writeItem(new StringWriter(), new Properties());
      junit.framework.Assert.fail("A-XQIA-8.1: serialization process fails when sequence contains a top-level attribute");
    } catch (XQException xq) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    try {
      xqs.writeItem((Writer)null, new Properties());
      junit.framework.Assert.fail("A-XQIA-11.3: writeItem accepts a null buffer as first argument");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    StringWriter result = new StringWriter();
    try {
      xqs.writeItem(result, new Properties());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-11.1: writeItem failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertTrue("A-XQIA-11.1: Expects serialized result contains '<e>Hello world!</e>', but it is '" + result.toString() + "'", result.toString().indexOf("<e>Hello world!</e>") != -1);
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    StringWriter otherResult = new StringWriter();
    try {
      xqs.writeItem(otherResult, new Properties());
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-11.2: writeItem failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQIA-11.2: null properties argument is equivalent to empty properties argument", result.toString(), otherResult.toString());
    xqe.close();
  }
  
  public void testWriteItemToSAX() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    try {
      xqs.writeItemToSAX(new DefaultHandler());
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.close();
    try {
      xqs.writeItemToSAX(new DefaultHandler());
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.writeItemToSAX(new DefaultHandler());
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e a='Hello world!'/>/@*");
    xqs.next();
    try {
      xqs.writeItemToSAX(new DefaultHandler());
      junit.framework.Assert.fail("A-XQIA-8.1: serialization process fails when sequence contains a top-level attribute");
    } catch (XQException xq) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    try {
      xqs.writeItemToSAX((ContentHandler)null);
      junit.framework.Assert.fail("A-XQIA-11.3: writeItem accepts a null buffer as first argument");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    TestContentHandler result = new TestContentHandler();
    try {
      xqs.writeItemToSAX(result);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-11.1: writeItem failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertTrue("A-XQIA-11.1: Expects serialized result contains '<e>Hello world!</e>', but it is '" + result.buffer.toString() + "'", result.buffer.toString().indexOf("<e>Hello world!</e>") != -1);
    xqe.close();
  }
  
  public void testWriteItemToResult() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    try {
      xqs.writeItemToResult(new StreamResult(new StringWriter()));
      junit.framework.Assert.fail("A-XQIA-1.2: getXXX() should fail when not positioned on an item");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.close();
    try {
      xqs.writeItemToResult(new StreamResult(new StringWriter()));
      junit.framework.Assert.fail("A-XQIA-1.3: closed item accessor supports getXXX()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    xqs.getItem();
    try {
      xqs.writeItemToResult(new StreamResult(new StringWriter()));
      junit.framework.Assert.fail("A-XQIA-1.4: SCROLLTYPE_FORWARD_ONLY sequence supports getting item twice()");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

 // The spec doesn't say serialization is performed according to XSLT 2.0 and XQuery 1.0 serialization. 
 // As such we don't test here if the serialization process can fail.
 //   
 //   xqe = xqc.createExpression();
 //   xqs = xqe.executeQuery("<e a='Hello world!'/>/@*");
 //   xqs.next();
 //   try {
 //     xqs.writeItemToResult(new StreamResult(new StringWriter()));
 //     junit.framework.Assert.fail("A-XQIA-8.1: serialization process fails when sequence contains a top-level attribute");
 //   } catch (XQException xq) {
 //     // Expect an XQException
 //   }
 //   xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    try {
      xqs.writeItemToResult((Result)null);
      junit.framework.Assert.fail("A-XQIA-11.3: writeItemToResult accepts a null buffer as first argument");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("<e>Hello world!</e>");
    xqs.next();
    StringWriter result = new StringWriter();
    try {
      xqs.writeItemToResult(new StreamResult(result));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQIA-11.1: writeItemToResult failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertTrue("A-XQIA-11.1: Expects serialized result contains '<e>Hello world!</e>', but it is '" + result.toString() + "'", result.toString().indexOf("<e>Hello world!</e>") != -1);
    xqe.close();
  }

}
