// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQStackTraceVariable;
import javax.xml.xquery.XQStackTraceElement;


public class XQStackTraceElementTest extends XQJTestCase {
  public void testConstructor() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), new XQStackTraceVariable[] {new XQStackTraceVariable(new QName("v"), "abc")});
      junit.framework.Assert.assertEquals("A-XQSTE-1.1: XQStackTraceElement successfully created", "moduleuri", xqste.getModuleURI());
      junit.framework.Assert.assertEquals("A-XQSTE-1.1: XQStackTraceElement successfully created", 1, xqste.getLineNumber());
      junit.framework.Assert.assertEquals("A-XQSTE-1.1: XQStackTraceElement successfully created", 2, xqste.getColumnNumber());
      junit.framework.Assert.assertEquals("A-XQSTE-1.1: XQStackTraceElement successfully created", 3, xqste.getPosition());
      junit.framework.Assert.assertEquals("A-XQSTE-1.1: XQStackTraceElement successfully created", new QName("function"), xqste.getFunctionQName());
      junit.framework.Assert.assertEquals("A-XQSTE-1.1: XQStackTraceElement successfully created", 1, xqste.getVariables().length);
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTE-1.1: Creating XQStackTraceElement faild with message: " + e.getMessage());
    }
  }
  
  public void testGetModuleURI() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), null);
      junit.framework.Assert.assertEquals("A-XQSTV-2.1: Retrieve the module uri from an XQStackTraceElement", "moduleuri", xqste.getModuleURI());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-2.1: Retrieve module uri from an XQStackTraceElement failed with message: " + e.getMessage());
    }
  }
  
  public void testGetLineNumber() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), null);
      junit.framework.Assert.assertEquals("A-XQSTV-3.1: Retrieve the line number from an XQStackTraceElement", 1, xqste.getLineNumber());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-3.1: Retrieve line number from an XQStackTraceElement failed with message: " + e.getMessage());
    }
  }
  
  public void testGetColumnNumber() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), null);
      junit.framework.Assert.assertEquals("A-XQSTV-4.1: Retrieve the column number from an XQStackTraceElement", 2, xqste.getColumnNumber());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-4.1: Retrieve column number from an XQStackTraceElement failed with message: " + e.getMessage());
    }
  }
  
  public void testGetPosition() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), null);
      junit.framework.Assert.assertEquals("A-XQSTV-5.1: Retrieve the position from an XQStackTraceElement", 3, xqste.getPosition());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-5.1: Retrieve position from an XQStackTraceElement failed with message: " + e.getMessage());
    }
  }
  
  public void testGetFunction() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), null);
      junit.framework.Assert.assertEquals("A-XQSTV-6.1: Retrieve the function name from an XQStackTraceElement", new QName("function"), xqste.getFunctionQName());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-6.1: Retrieve function name from an XQStackTraceElement failed with message: " + e.getMessage());
    }
  }
  
  public void testGetVariables() {
    try {
      XQStackTraceElement xqste = new XQStackTraceElement("moduleuri",1,2,3, new QName("function"), new XQStackTraceVariable[] {new XQStackTraceVariable(new QName("v"), "abc")});
      junit.framework.Assert.assertEquals("A-XQSTV-7.1: Retrieve the variables from an XQStackTraceElement", 1, xqste.getVariables().length);
      junit.framework.Assert.assertEquals("A-XQSTV-7.1: Retrieve the variables from an XQStackTraceElement", new QName("v"), xqste.getVariables()[0].getQName());
      junit.framework.Assert.assertEquals("A-XQSTV-7.1: Retrieve the variables from an XQStackTraceElement", "abc",  xqste.getVariables()[0].getValue());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-7.1: Retrieve variables from an XQStackTraceElement failed with message: " + e.getMessage());
    }
  }
}