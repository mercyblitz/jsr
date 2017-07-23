// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.xquery.XQException;

public class XQExceptionTest extends XQJTestCase {

  public void testConstructor1() {
    try {
      XQException ex = new XQException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQEX-1.1: XQException successfully created", "Hello world!", ex.getMessage());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-1.1: Creating XQException failed with message: " + e.getMessage());
    }
  }

  public void testConstructor2() {
    try {
      XQException ex = new XQException("Hello world!", "VendorCode");
      junit.framework.Assert.assertEquals("A-XQEX-1.1: XQException successfully created", "Hello world!", ex.getMessage());
      junit.framework.Assert.assertEquals("A-XQEX-1.1: XQException successfully created", "VendorCode", ex.getVendorCode());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-1.1: Creating XQException failed with message: " + e.getMessage());
    }
  }

  public void testGetVendorCode() {
    try {
      XQException ex = new XQException("Hello world!", "VendorCode");
      junit.framework.Assert.assertEquals("A-XQEX-2.1: Retrieve vendor code from an XQException", "VendorCode", ex.getVendorCode());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-2.1: Retrieve vendor code from an XQException failed with message: " + e.getMessage());
    }
    
    try {
      XQException ex = new XQException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQEX-2.2: Vendor code is null when not available", null, ex.getVendorCode());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-2.2: Retrieve vendor code from an XQException failed with message: " + e.getMessage());
    }
  }

  public void testGetNextException() {
    try {
      XQException ex = new XQException("Hello world!", "VendorCode");
      junit.framework.Assert.assertNull("A-XQEX-3.1: getNextException returns null when last in chain", ex.getNextException());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-3.1: getNextException failed with message: " + e.getMessage());
    }
    
    try {
      XQException ex1 = new XQException("Hello world!", "VendorCode");
      XQException ex2 = new XQException("Hello world!", "VendorCode");
      ex1.setNextException(ex2);
      junit.framework.Assert.assertEquals("A-XQEX-3.2: getNextException returns next exception", ex2, ex1.getNextException());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-3.2: getNextException failed with message: " + e.getMessage());
    }   
  }

  public void testSetNextException() {
    try {
      XQException ex1 = new XQException("Hello world!", "VendorCode");
      XQException ex2 = new XQException("Hello world!", "VendorCode");
      ex1.setNextException(ex2);
      junit.framework.Assert.assertEquals("A-XQEX-4.1: setNextException sets the next exception in the chain", ex2, ex1.getNextException());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQEX-4.1 : setNextException failed with message: " + e.getMessage());
    }   
  }
  
}
