// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import java.io.FileInputStream;
import java.util.Properties;

import junit.framework.TestCase;
import javax.xml.xquery.*;

public class XQJTestCase extends TestCase{
  protected XQDataSource xqds;
  protected XQConnection xqc;
  
  protected void setUp() throws Exception{
    // Get the file name of the properties file
    String fileName = System.getProperty("com.oracle.xqj.tck.datasource");
    if (fileName == null)
      throw new Exception("The property 'com.oracle.xqj.tck.datasource' must be set.");
    // load the properties file
    Properties p = new Properties();
    p.load(new FileInputStream(fileName));
    // create an XQDataSource instance using reflection
    String xqdsClassName = p.getProperty("XQDataSourceClassName");
    Class xqdsClass = Class.forName(xqdsClassName);
    // create the XQDataSource instance
    xqds = (XQDataSource)xqdsClass.newInstance();
    // remove the XQDataSourceClassName property
    // as the XQJ implementation doesn't know about it and raise an error
    p.remove("XQDataSourceClassName");
    if (!p.isEmpty()) 
      // set the remaining properties
      xqds.setProperties(p);

    xqc = xqds.getConnection();
  }

  protected void tearDown() throws Exception {
   xqc.close(); 
  }
}
