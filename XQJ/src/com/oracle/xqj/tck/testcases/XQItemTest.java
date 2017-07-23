// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.xquery.*;

public class XQItemTest extends XQJTestCase {

  public void testClose() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    XQItem xqi;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'Hello world!'");
    xqs.next();
    xqi = xqs.getItem();
    try {
      xqi.close();
      xqi.close();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQE-2.1: closing item failed with message: " + e.getMessage());
    }
    xqe.close(); 
  }
  
  public void testIsClosed() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    XQItem xqi;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("'Hello world!'");
    xqs.next();
    xqi = xqs.getItem();
    
    junit.framework.Assert.assertEquals("A-XQE-1.1: isClosed() on open item", false, xqi.isClosed());
    xqi.close();
    junit.framework.Assert.assertEquals("A-XQE-1.2: isClosed() on closed item", true, xqi.isClosed());
    
    xqe.close(); 
  }
}

