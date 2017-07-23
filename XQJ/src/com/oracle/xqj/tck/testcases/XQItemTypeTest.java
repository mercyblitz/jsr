// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQSequenceType;


public class XQItemTypeTest extends XQJTestCase {

  private void testSimpleType(XQItemType xqtype, int itemKind) throws XQException {
    try {
      xqtype.getBaseType();
      junit.framework.Assert.fail("A-XQIT-1.2: getBaseType() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
    junit.framework.Assert.assertEquals("A-XQIT-2.1: getItemKind() returns the correct item kind.", itemKind, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQIT-3.1: getItemOccurrence() returns OCC_EXACTLY_ONE.", XQSequenceType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    // Not much we can test for toString()
    xqtype.toString();
    try {
      xqtype.getNodeName();
      junit.framework.Assert.fail("A-XQIT-5.2: getNodeName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
    junit.framework.Assert.assertEquals("A-XQIT-6.2: getSchemaURI() returns null.", null, xqtype.getSchemaURI());
    try {
      xqtype.getTypeName();
      junit.framework.Assert.fail("A-XQIT-7.2: getTypeName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
    junit.framework.Assert.assertFalse("A-XQIT-8.1: isAnonymousType() reports if the type is anonymous.", xqtype.isAnonymousType());
    junit.framework.Assert.assertFalse("A-XQIT-9.1: isElementNillable() reports if the element is nillable.", xqtype.isElementNillable());
    try {
      xqtype.getPIName();
      junit.framework.Assert.fail("A-XQIT-10.2: getPIName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }    
  }
  
  public void testItemKindAtomic() throws XQException {
    XQItemType xqtype = xqc.createAtomicType(XQItemType.XQBASETYPE_STRING);
    
    junit.framework.Assert.assertEquals("A-XQIT-1.1: getBaseType() returns the correct base type.", XQItemType.XQBASETYPE_STRING, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQIT-2.1: getItemKind() returns the correct item kind.", XQItemType.XQITEMKIND_ATOMIC, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQIT-3.1: getItemOccurrence() returns OCC_EXACTLY_ONE.", XQSequenceType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    // Not much we can test for toString()
    xqtype.toString();
    try {
      xqtype.getNodeName();
      junit.framework.Assert.fail("A-XQIT-5.2: getNodeName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
    junit.framework.Assert.assertEquals("A-XQIT-6.2: getSchemaURI() returns null.", null, xqtype.getSchemaURI());
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "string", xqtype.getTypeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());  
    junit.framework.Assert.assertFalse("A-XQIT-8.1: isAnonymousType() reports if the type is anonymous.", xqtype.isAnonymousType());
    junit.framework.Assert.assertFalse("A-XQIT-9.1: isElementNillable() reports if the element is nillable.", xqtype.isElementNillable());
    try {
      xqtype.getPIName();
      junit.framework.Assert.fail("A-XQIT-10.2: getPIName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
  }

  public void testItemKindAttribute() throws XQException {
    XQItemType xqtype = xqc.createAttributeType(new QName("http://www.xqj.org", "a"), XQItemType.XQBASETYPE_STRING);
    
    junit.framework.Assert.assertEquals("A-XQIT-1.1: getBaseType() returns the correct base type.", XQItemType.XQBASETYPE_STRING, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQIT-2.1: getItemKind() returns the correct item kind.", XQItemType.XQITEMKIND_ATTRIBUTE, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQIT-3.1: getItemOccurrence() returns OCC_EXACTLY_ONE.", XQSequenceType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    // Not much we can test for toString()
    xqtype.toString();
    junit.framework.Assert.assertEquals("A-XQIT-5.1: getNodeName() returns the correct QName.", "a", xqtype.getNodeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-5.1: getNodeName() returns the correct QName.", "http://www.xqj.org", xqtype.getNodeName().getNamespaceURI());  
    junit.framework.Assert.assertEquals("A-XQIT-6.2: getSchemaURI() returns null.", null, xqtype.getSchemaURI());
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "string", xqtype.getTypeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());  
    junit.framework.Assert.assertFalse("A-XQIT-8.1: isAnonymousType() reports if the type is anonymous.", xqtype.isAnonymousType());
    junit.framework.Assert.assertFalse("A-XQIT-9.1: isElementNillable() reports if the element is nillable.", xqtype.isElementNillable());
    try {
      xqtype.getPIName();
      junit.framework.Assert.fail("A-XQIT-10.2: getPIName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
  }

  public void testItemKindComment() throws XQException {
    XQItemType xqtype = xqc.createCommentType() ;
    
    testSimpleType(xqtype, XQItemType.XQITEMKIND_COMMENT);
  }

  public void testItemKindDocument() throws XQException {
    XQItemType xqtype = xqc.createDocumentType() ;

    testSimpleType(xqtype, XQItemType.XQITEMKIND_DOCUMENT);
  }

  public void testItemKindDocumentElement() throws XQException {
    XQItemType xqtype_element = xqc.createElementType(new QName("http://www.xqj.org", "e"), XQItemType.XQBASETYPE_STRING);
    XQItemType xqtype = xqc.createDocumentElementType(xqtype_element);
    
    junit.framework.Assert.assertEquals("A-XQIT-1.1: getBaseType() returns the correct base type.", XQItemType.XQBASETYPE_STRING, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQIT-2.1: getItemKind() returns the correct item kind.", XQItemType.XQITEMKIND_DOCUMENT_ELEMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQIT-3.1: getItemOccurrence() returns OCC_EXACTLY_ONE.", XQSequenceType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    // Not much we can test for toString()
    xqtype.toString();
    junit.framework.Assert.assertEquals("A-XQIT-5.1: getNodeName() returns the correct QName.", "e", xqtype.getNodeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-5.1: getNodeName() returns the correct QName.", "http://www.xqj.org", xqtype.getNodeName().getNamespaceURI());  
    junit.framework.Assert.assertEquals("A-XQIT-6.2: getSchemaURI() returns null", null, xqtype.getSchemaURI());
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "string", xqtype.getTypeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());  
    junit.framework.Assert.assertFalse("A-XQIT-8.1: isAnonymousType() reports if the type is anonymous.", xqtype.isAnonymousType());
    junit.framework.Assert.assertFalse("A-XQIT-9.1: isElementNillable() reports if the element is nillable.", xqtype.isElementNillable());
    try {
      xqtype.getPIName();
      junit.framework.Assert.fail("A-XQIT-10.2: getPIName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
  }

  public void testItemKindDocumentSchemaElement() throws XQException {
    // optional feature, not tested.
  }

  public void testItemKindElement() throws XQException {
    XQItemType xqtype = xqc.createElementType(new QName("http://www.xqj.org", "e"), XQItemType.XQBASETYPE_STRING);
    
    junit.framework.Assert.assertEquals("A-XQIT-1.1: getBaseType() returns the correct base type.", XQItemType.XQBASETYPE_STRING, xqtype.getBaseType());
    junit.framework.Assert.assertEquals("A-XQIT-2.1: getItemKind() returns the correct item kind.", XQItemType.XQITEMKIND_ELEMENT, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQIT-3.1: getItemOccurrence() returns OCC_EXACTLY_ONE.", XQSequenceType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    // Not much we can test for toString()
    xqtype.toString();
    junit.framework.Assert.assertEquals("A-XQIT-5.1: getNodeName() returns the correct QName.", "e", xqtype.getNodeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-5.1: getNodeName() returns the correct QName.", "http://www.xqj.org", xqtype.getNodeName().getNamespaceURI());  
    junit.framework.Assert.assertEquals("A-XQIT-6.2: getSchemaURI() returns null", null, xqtype.getSchemaURI());
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "string", xqtype.getTypeName().getLocalPart());  
    junit.framework.Assert.assertEquals("A-XQIT-7.1: getTypeName() returns the correct QName.", "http://www.w3.org/2001/XMLSchema", xqtype.getTypeName().getNamespaceURI());  
    junit.framework.Assert.assertFalse("A-XQIT-8.1: isAnonymousType() reports if the type is anonymous.", xqtype.isAnonymousType());
    junit.framework.Assert.assertFalse("A-XQIT-9.1: isElementNillable() reports if the element is nillable.", xqtype.isElementNillable());
    try {
      xqtype.getPIName();
      junit.framework.Assert.fail("A-XQIT-10.2: getPIName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }
  }

  public void testItemKindItem() throws XQException {
    XQItemType xqtype = xqc.createItemType() ;

    testSimpleType(xqtype, XQItemType.XQITEMKIND_ITEM);
  }

  public void testItemKindNode() throws XQException {
    XQItemType xqtype = xqc.createNodeType() ;

    testSimpleType(xqtype, XQItemType.XQITEMKIND_NODE);
  }

  public void testItemKindPI() throws XQException {
    XQItemType xqtype = xqc.createProcessingInstructionType("pi");
    
    try {
      xqtype.getBaseType();
      junit.framework.Assert.fail("A-XQIT-1.2: getBaseType() must throw an XQException");
    } catch (XQException e) {
      // Expect an XQException
    }
    junit.framework.Assert.assertEquals("A-XQIT-2.1: getItemKind() returns the correct item kind.", XQItemType.XQITEMKIND_PI, xqtype.getItemKind());
    junit.framework.Assert.assertEquals("A-XQIT-3.1: getItemOccurrence() returns OCC_EXACTLY_ONE.", XQSequenceType.OCC_EXACTLY_ONE, xqtype.getItemOccurrence());
    // Not much we can test for toString()
    xqtype.toString();
    try {
      xqtype.getNodeName();
      junit.framework.Assert.fail("A-XQIT-5.2: getNodeName() must throw an XQException");
    } catch (XQException e) {
      // Expect an XQException
    }
    junit.framework.Assert.assertEquals("A-XQIT-6.2: getSchemaURI() returns null", null, xqtype.getSchemaURI());
    try {
      xqtype.getTypeName();
      junit.framework.Assert.fail("A-XQIT-7.2: getTypeName() must throw an XQException.");
    } catch (XQException e) {
      // Expect an XQException
    }   
    junit.framework.Assert.assertFalse("A-XQIT-8.1: isAnonymousType() reports if the type is anonymous.", xqtype.isAnonymousType());
    junit.framework.Assert.assertFalse("A-XQIT-9.1: isElementNillable() reports if the element is nillable.", xqtype.isElementNillable());
    junit.framework.Assert.assertEquals("A-XQIT-10.1: getPIName() returns the correct name.", "pi", xqtype.getPIName());  
  }

  public void testItemKindSchemaAttribute() throws XQException {
    // optional feature, not tested.
  }

  public void testItemKindSchemaElement() throws XQException {
    // optional feature, not tested.
  }

  public void testItemKindText() throws XQException {
    XQItemType xqtype = xqc.createTextType() ;

    testSimpleType(xqtype, XQItemType.XQITEMKIND_TEXT);
  }
}

