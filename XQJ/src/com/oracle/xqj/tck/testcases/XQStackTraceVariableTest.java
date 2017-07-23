// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQStackTraceVariable;


public class XQStackTraceVariableTest extends XQJTestCase {

  public void testConstructor() {
    try { 
      XQStackTraceVariable xqstv = new XQStackTraceVariable(new QName("v"), "abc");
      junit.framework.Assert.assertEquals("A-XQSTV-1.1: XQStackTraceVariable successfully created", new QName("v"), xqstv.getQName());
      junit.framework.Assert.assertEquals("A-XQSTV-1.1: XQStackTraceVariable successfully created", "abc", xqstv.getValue());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-1.1: Creating XQStackTraceVariable faild with message: " + e.getMessage());
    }
  }
  
  public void testGetQName() {
    try {
      XQStackTraceVariable xqstv = new XQStackTraceVariable(new QName("v"), "abc");
      junit.framework.Assert.assertEquals("A-XQSTV-2.1: Retrieve the QName from an XQStackTraceVariable", new QName("v"), xqstv.getQName());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-2.1: Retrieve QName from an XQStackTraceVariable failed with message: " + e.getMessage());
    }
  }
  
  public void testGetValue() {
    try {
      XQStackTraceVariable xqstv = new XQStackTraceVariable(new QName("v"), "abc");
      junit.framework.Assert.assertEquals("A-XQSTV-3.1: Retrieve the value from an XQStackTraceVariable", "abc", xqstv.getValue());
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQSTV-3.1: Retrieve value from an XQStackTraceVariable failed with message: " + e.getMessage());
    }
  }
}
