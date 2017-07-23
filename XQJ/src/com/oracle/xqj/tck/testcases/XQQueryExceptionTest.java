// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQQueryException;
import javax.xml.xquery.XQSequence;
import javax.xml.xquery.XQStackTraceElement;


public class XQQueryExceptionTest extends XQJTestCase {
  public void testConstructor1() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "Hello world!", ex.getMessage());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-1.1: Creating XQQueryException faild with message: " + e.getMessage());
    }
  }

  public void testConstructor2() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", new QName("foo"));
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "Hello world!", ex.getMessage());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", new QName("foo"), ex.getErrorCode());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-1.1: Creating XQQueryException faild with message: " + e.getMessage());
    }
  }

  public void testConstructor5() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", new QName("foo"), 7, 8, 56);
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "Hello world!", ex.getMessage());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", new QName("foo"), ex.getErrorCode());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 7, ex.getLineNumber());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 8, ex.getColumnNumber());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 56, ex.getPosition());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-1.1: Creating XQQueryException faild with message: " + e.getMessage());
    }
  }

  public void testConstructor6() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56);
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "Hello world!", ex.getMessage());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "VendorCode", ex.getVendorCode());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", new QName("foo"), ex.getErrorCode());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 7, ex.getLineNumber());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 8, ex.getColumnNumber());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 56, ex.getPosition());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-1.1: Creating XQQueryException faild with message: " + e.getMessage());
    }
  }

  public void testConstructor9() throws XQException {
    XQExpression xqe = xqc.createExpression();
    XQSequence xqs = xqe.executeQuery("1,2");
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56,
                                                 "moduleuri", xqs, (XQStackTraceElement[])null);
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "Hello world!", ex.getMessage());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "VendorCode", ex.getVendorCode());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", new QName("foo"), ex.getErrorCode());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 7, ex.getLineNumber());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 8, ex.getColumnNumber());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", 56, ex.getPosition());
      junit.framework.Assert.assertEquals("A-XQQEX-1.1: XQQueryException successfully created", "moduleuri", ex.getModuleURI());
      junit.framework.Assert.assertTrue("A-XQQEX-1.1: XQQueryException successfully created", xqs == ex.getErrorObject());
      junit.framework.Assert.assertTrue("A-XQQEX-1.1: XQQueryException successfully created", null == ex.getQueryStackTrace());
      
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-1.1: Creating XQQueryException faild with message: " + e.getMessage());
    }
    
    xqs.close();
  }
  
  public void testGetErrorCode() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", new QName("foo"));
      junit.framework.Assert.assertEquals("A-XQQEX-2.1: Retrieve error code from an XQQueryException", new QName("foo"), ex.getErrorCode());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-2.1: Retrieve error code from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-2.2: Error code is null when not available", null, ex.getErrorCode());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-2.2: Retrieve error code from an XQQueryException failed with message: " + e.getMessage());
    }
  }

  public void testGetErrorObject() throws XQException {
    XQExpression xqe = xqc.createExpression();
    XQSequence xqs = xqe.executeQuery("1,2");
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56,
                                                 "moduleuri", xqs, (XQStackTraceElement[])null);
      XQSequence errorObject = ex.getErrorObject();
      errorObject.next();
      junit.framework.Assert.assertEquals("A-XQQEX-3.1: Retrieve error object from an XQQueryException", 1, errorObject.getInt());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-3.1: Retrieve error object from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-3.2: Error object is null when not available", null, ex.getErrorObject());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-3.2: Retrieve error object from an XQQueryException failed with message: " + e.getMessage());
    }
    
    xqe.close();
  }

  public void testGetPosition() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56);
      junit.framework.Assert.assertEquals("A-XQQEX-4.1: Retrieve position from an XQQueryException", 56, ex.getPosition());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-4.1: Retrieve position from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-4.2: Position is -1 when not available", -1, ex.getPosition());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-4.2: Retrieve position from an XQQueryException failed with message: " + e.getMessage());
    }
  }

  public void testGetQueryStackTrace() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56,
                                                 "moduleuri", null, new XQStackTraceElement[] {});
      junit.framework.Assert.assertEquals("A-XQQEX-5.1: Retrieve query stack trace from an XQQueryException", 0, ex.getQueryStackTrace().length);
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-5.1: Retrieve query stack trace from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-5.2: Query stack trace is null when not available", null, ex.getQueryStackTrace());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-5.2: Retrieve Query stack trace from an XQQueryException failed with message: " + e.getMessage());
    }
  }

  public void testGetModuleURI() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56,
                                                 "moduleuri", null, null);
      junit.framework.Assert.assertEquals("A-XQQEX-6.1: Retrieve module uri from an XQQueryException", "moduleuri", ex.getModuleURI());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-6.1: Retrieve module uri from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-6.2: module uri is null when not available", null, ex.getModuleURI());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-6.2: Retrieve module uri from an XQQueryException failed with message: " + e.getMessage());
    }
  }
  
  public void testGetLineNumber() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56);
      junit.framework.Assert.assertEquals("A-XQQEX-7.1: Retrieve line number from an XQQueryException", 7, ex.getLineNumber());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-7.1: Retrieve line number from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-7.2: Line number is -1 when not available", -1, ex.getLineNumber());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-7.2: Retrieve line number from an XQQueryException failed with message: " + e.getMessage());
    }
  }

  public void testGetColumnNumber() {
    try {
      XQQueryException ex = new XQQueryException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56);
      junit.framework.Assert.assertEquals("A-XQQEX-8.1: Retrieve column number from an XQQueryException", 8, ex.getColumnNumber());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-8.1: Retrieve column number from an XQQueryException failed with message: " + e.getMessage());
    }
    
    try {
      XQQueryException ex = new XQQueryException("Hello world!");
      junit.framework.Assert.assertEquals("A-XQQEX-8.2: Column number is -1 when not available", -1, ex.getColumnNumber());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQQEX-8.2: Retrieve column number from an XQQueryException failed with message: " + e.getMessage());
    }
  }

}
