// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import javax.xml.namespace.QName;
import javax.xml.xquery.*;

public class XQPreparedExpressionTest extends XQJTestCase {

  public void testCancel() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    try {
      xqpe.cancel();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-1.1: cancellation of prepared expression failed with message: " + e.getMessage());
    }

    
    xqpe.close();
    try {
      xqpe.cancel();
      junit.framework.Assert.fail("A-XQPE-1.2: closed prepared expression supports cancel()");
    } catch (XQException e) {
      // Expect an XQException
    }
  }
  
  public void testIsClosed() throws XQException {
    XQPreparedExpression xqpe;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqpe.executeQuery();
    
    junit.framework.Assert.assertEquals("A-XQPE-2.1: isClosed() on open prepared expression", false, xqpe.isClosed());
    xqpe.close();
    junit.framework.Assert.assertEquals("A-XQPE-2.2: isClosed() on closed prepared expressions", true, xqpe.isClosed());
  }

  public void testClose() throws XQException {
    XQPreparedExpression xqpe;
    XQSequence xqs;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqs = xqpe.executeQuery();
    try {
      xqpe.close(); 
      xqpe.close();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-3.1: closing prepared expression failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertEquals("A-XQPE-3.2: Closing an expression, closes any result sequences obtained from this expression", true, xqs.isClosed());
  }
 
  public void testExecuteQuery() throws XQException {
    XQPreparedExpression xqpe;
    XQSequence xqs = null;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqpe.close();
    try {
      xqpe.executeQuery();
      junit.framework.Assert.fail("A-XQPE-8.1: closed prepared expression supports executeQuery()");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("'Hello world!'");
    try {
      xqs = xqpe.executeQuery();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-8.2: executeQuery() failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertNotNull("A-XQPE-8.2: executeQuery() failed", xqs);
    xqpe.close(); 
  }
  
  public void testGetAllExternalVariables() throws XQException {
    XQPreparedExpression xqpe;
    QName[] extVars = null;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqpe.close();
    try {
      xqpe.getAllExternalVariables();
      junit.framework.Assert.fail("A-XQPE-4.1: closed prepared expression supports getAllExternalVariables()");
    } catch (XQException e) {
      // Expect an XQException
    }
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    try {
      extVars = xqpe.getAllExternalVariables();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-4.2: getAllExternalVariables on prepared expression without external variables failed with message: " + e.getMessage());
    }    
    junit.framework.Assert.assertNotNull("A-XQPE-4.2: getAllExternalVariables on prepared expression without external variables", extVars);
    junit.framework.Assert.assertEquals("A-XQPE-4.2: getAllExternalVariables on prepared expression without external variables", 0, extVars.length);
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      extVars = xqpe.getAllExternalVariables();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-4.3: getAllExternalVariables on prepared expression with external variables failed with message: " + e.getMessage());
    }    
    junit.framework.Assert.assertNotNull("A-XQPE-4.3: getAllExternalVariables on prepared expression with external variables", extVars);
    junit.framework.Assert.assertEquals("A-XQPE-4.3: getAllExternalVariables on prepared expression with external variables", 1, extVars.length);
    junit.framework.Assert.assertEquals("A-XQPE-4.3: getAllExternalVariables on prepared expression with external variables", "v", extVars[0].getLocalPart());
    junit.framework.Assert.assertEquals("A-XQPE-4.3: getAllExternalVariables on prepared expression with external variables", "", extVars[0].getNamespaceURI());
    xqpe.close();
  }

  public void testGetAllUnboundExternalVariables() throws XQException {
    XQPreparedExpression xqpe;
    QName[] extVars = null;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqpe.close();
    try {
      xqpe.getAllUnboundExternalVariables();
      junit.framework.Assert.fail("A-XQPE-9.1: closed prepared expression supports getAllUnboundExternalVariables()");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    
    try {
      extVars = xqpe.getAllUnboundExternalVariables();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-9.2: getAllUnboundExternalVariables on prepared expression with unbound variables failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertNotNull("A-XQPE-9.2: getAllUnboundExternalVariables on prepared expression with unbound variables", extVars);
    junit.framework.Assert.assertEquals("A-XQPE-9.2: getAllUnboundExternalVariables on prepared expression with unbound variables", 1, extVars.length);
    junit.framework.Assert.assertEquals("A-XQPE-9.2: getAllUnboundExternalVariables on prepared expression with unbound variables", "v", extVars[0].getLocalPart());
    junit.framework.Assert.assertEquals("A-XQPE-9.2: getAllUnboundExternalVariables on prepared expression with unbound variables", "", extVars[0].getNamespaceURI());
    xqpe.bindString(new QName("v"), "Hello world!", null);
    try {
      extVars = xqpe.getAllUnboundExternalVariables();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-9.3: getAllUnboundExternalVariables on prepared expression without unbound variables failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertNotNull("A-XQPE-9.3: getAllUnboundExternalVariables on prepared expression without unbound variables", extVars);
    junit.framework.Assert.assertEquals("A-XQPE-9.3: getAllUnboundExternalVariables on prepared expression without unbound variables", 0, extVars.length);
     
    xqpe.close(); 
  }
  
  public void testGetStaticResultType() throws XQException {
    XQPreparedExpression xqpe;
    XQSequenceType xqtype = null;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqpe.close();
    try {
      xqpe.getStaticResultType();
      junit.framework.Assert.fail("A-XQPE-5.1: closed prepared expression supports getStaticResultType()");
    } catch (XQException e) {
      // Expect an XQException
    }
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqtype = xqpe.getStaticResultType();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-5.2: getStaticResultType() failed with message: " + e.getMessage());
    }    
    junit.framework.Assert.assertNotNull("A-XQPE-5.2: getStaticResultType() failed", xqtype);
    junit.framework.Assert.assertEquals("A-XQPE-5.2: getStaticResultType() failed", XQItemType.OCC_ZERO_OR_MORE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQPE-5.2: getStaticResultType() failed", XQItemType.XQITEMKIND_ITEM, xqtype.getItemType().getItemKind());
    xqpe.close();  
  }
  
  public void testGetStaticVariableType() throws XQException {
    XQPreparedExpression xqpe;
    XQSequenceType xqtype = null;
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    xqpe.close();
    try {
      xqpe.getStaticVariableType(new QName("v"));
      junit.framework.Assert.fail("A-XQPE-6.1: closed prepared expression supports getStaticVariableType()");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.getStaticVariableType(new QName("foo"));
      junit.framework.Assert.fail("A-XQPE-6.2: getStaticVariableType() specifying undeclared variable");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqpe.close();
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqpe.getStaticVariableType(null);
      junit.framework.Assert.fail("A-XQPE-6.3: getStaticVariableType() specifying null");
    } catch (XQException e) {
      // Expect an XQException
    }
    xqpe.close(); 
    
    xqpe = xqc.prepareExpression("declare variable $v external; $v");
    try {
      xqtype = xqpe.getStaticVariableType(new QName("v"));
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-6.4: getStaticVariableType() failed with message: " + e.getMessage());
    }    
    junit.framework.Assert.assertNotNull("A-XQPE-6.4: getStaticVariableType() failed", xqtype);
    junit.framework.Assert.assertEquals("A-XQPE-6.4: getStaticResultType() failed", XQItemType.OCC_ZERO_OR_MORE, xqtype.getItemOccurrence());
    junit.framework.Assert.assertEquals("A-XQPE-6.4: getStaticResultType() failed", XQItemType.XQITEMKIND_ITEM, xqtype.getItemType().getItemKind());
    xqpe.close();  
  }
  
  public void testGetStaticContext() throws XQException {
    XQPreparedExpression xqpe;
    XQStaticContext xqsc = null;
    
    xqpe = xqc.prepareExpression("'Hello world!'");
    xqpe.close();
    try {
      xqpe.getStaticContext();
      junit.framework.Assert.fail("A-XQPE-7.1: closed prepared expression supports getStaticContext()");
    } catch (XQException e) {
      // Expect an XQException
    }

    xqpe = xqc.prepareExpression("'Hello world!'");
    try {
      xqsc = xqpe.getStaticContext();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQPE-7.2: getting static context failed with message: " + e.getMessage());
    }
    junit.framework.Assert.assertNotNull("A-XQPE-7.2: getting static context failed", xqsc);
    xqpe.close(); 
  }
}