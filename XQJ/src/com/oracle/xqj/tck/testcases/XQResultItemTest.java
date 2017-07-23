// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.xquery.*;

public class XQResultItemTest extends XQJTestCase {
  
  public void testGetConnection() throws XQException {
    XQExpression xqe;
    XQSequence xqs;
    XQItem xqi;
    XQResultItem xqri;
    XQConnection returned_xqc = null;
    
    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("1,2,3,4");
    xqs.next();
    xqi = xqs.getItem();
    junit.framework.Assert.assertTrue("A-XQS-12.5: Item must be XQResultItem", xqi instanceof XQResultItem);   
    xqri = (XQResultItem)xqi;
    xqri.close();
    try {
      xqri.getConnection();
      junit.framework.Assert.fail("A-XQRI-1.1: Getting the connection on a closed result item fails.");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqe.close();

    xqe = xqc.createExpression();
    xqs = xqe.executeQuery("1,2,3,4");
    xqs.next();
    xqi = xqs.getItem();
    junit.framework.Assert.assertTrue("A-XQS-12.5: Item must be XQResultItem", xqi instanceof XQResultItem);   
    xqri = (XQResultItem)xqi;
    try {
      returned_xqc = xqri.getConnection();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQRI-1.2: getConnection() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertSame("A-XQRI-1.2: Successfully get the connection of a result item.", xqc, returned_xqc);
    xqe.close(); 
  }
}
