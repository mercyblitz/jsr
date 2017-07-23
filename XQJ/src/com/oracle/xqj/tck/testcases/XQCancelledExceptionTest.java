// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQCancelledException;
import javax.xml.xquery.XQStackTraceElement;

public class XQCancelledExceptionTest extends XQJTestCase {

  public void testConstructor() {
    try {
      XQCancelledException ex = new XQCancelledException("Hello world!", "VendorCode", new QName("foo"), 7, 8, 56,
                                                         "moduleuri", null, (XQStackTraceElement[])null);
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", "Hello world!", ex.getMessage());
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", "VendorCode", ex.getVendorCode());
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", new QName("foo"), ex.getErrorCode());
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", 7, ex.getLineNumber());
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", 8, ex.getColumnNumber());
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", 56, ex.getPosition());
      junit.framework.Assert.assertEquals("A-XQCEX-1.1: XQCancelledException successfully created", "moduleuri", ex.getModuleURI());
      junit.framework.Assert.assertTrue("A-XQCEX-1.1: XQCancelledException successfully created", null == ex.getQueryStackTrace());
      
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQCEX-1.1: Creating XQCancelledException faild with message: " + e.getMessage());
    }
    
  }

}
