// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck;

import com.oracle.xqj.tck.testcases.*;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


public class AllTests {

  public static void main(String[] args) {
   TestRunner.run(suite());
 /*  TestResult result =  new TestRunner().doRun(suite());
   System.out.print(result.toString());
   result = null;
  */}
  
  public static Test suite() {
    TestSuite suite = new TestSuite("JSR 225 (XQJ) Technology Compatibility Kit");
    suite.addTestSuite(SignatureTest.class);
    suite.addTestSuite(XQConnectionTest.class);
    suite.addTestSuite(XQDataFactoryTest.class);
    suite.addTestSuite(XQDataSourceTest.class);
    suite.addTestSuite(XQDynamicContextTest.class);
    suite.addTestSuite(XQExpressionTest.class);
    suite.addTestSuite(XQItemAccessorTest.class);
    suite.addTestSuite(XQItemTest.class);
    suite.addTestSuite(XQItemTypeTest.class);
    suite.addTestSuite(XQMetaDataTest.class);
    suite.addTestSuite(XQPreparedExpressionTest.class);
    suite.addTestSuite(XQResultItemTest.class);
    suite.addTestSuite(XQResultSequenceTest.class);
    suite.addTestSuite(XQSequenceTest.class);
    suite.addTestSuite(XQSequenceTypeTest.class);
    suite.addTestSuite(XQStaticContextTest.class);
    suite.addTestSuite(XQExceptionTest.class);
    suite.addTestSuite(XQQueryExceptionTest.class);
    suite.addTestSuite(XQCancelledExceptionTest.class);
    suite.addTestSuite(XQStackTraceVariableTest.class);
    suite.addTestSuite(XQStackTraceElementTest.class);
    return suite;
  }
}
