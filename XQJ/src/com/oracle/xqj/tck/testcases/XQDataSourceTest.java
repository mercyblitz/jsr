// Copyright (c) 2003, 2006, 2007, Oracle. All rights reserved.
package com.oracle.xqj.tck.testcases;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;

public class XQDataSourceTest extends XQJTestCase {

  public void testGetConnection() throws XQException {
    XQConnection my_xqc;
    
    try {
      my_xqc = xqds.getConnection();
      my_xqc.close();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDS-1.1: Creating an XQConnection failed with message: " + e.getMessage());
    }
  }
  
  public void testGetConnection_jdbc() throws XQException {
    // optional feature, not tested.
  }
  
  public void testGetConnection_uid_pwd() throws XQException {
    XQConnection my_xqc;
    
    // only test the getConnection() flavour with uid/pwd if the
    // implementation supports these properties
    boolean testIt = true;
    
    String uid = null;
    String pwd = null;
    
    try {
      uid = xqds.getProperty("user");
      pwd = xqds.getProperty("password");
    } catch (XQException e) {
      testIt = false;
    }
    
    if (testIt) {
      try {
        my_xqc = xqds.getConnection(uid, pwd);
        my_xqc.close();
      } catch (XQException e) {
        junit.framework.Assert.fail("A-XQDS-3.1: Creating an XQConnection specifying a uid/pwd failed with message: " + e.getMessage());
      }     
    }
  }

  public void testGetLoginTimeout() throws XQException {
    try {
      xqds.getLoginTimeout();
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDS-4.1: Retrieving the login timeout failed with message: " + e.getMessage());
    }
  }

  public void testGetLogWriter() throws XQException {
    try {
      Writer log = xqds.getLogWriter();
      junit.framework.Assert.assertNull("A-XQDS-5.2: The default log writer is null", log);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDS-5.1: Retrieving the log writer failed with message: " + e.getMessage());
    }
  }

  public void testGetSupportedPropertyNames() throws XQException {
    try {
      String propertyNames[] = xqds.getSupportedPropertyNames();
      junit.framework.Assert.assertNotNull("A-XQDS-6.2: getSupportedPropertyNames returns a non null array of String objects", propertyNames);
    } catch (Exception e) {
      junit.framework.Assert.fail("A-XQDS-6.1: Retrieving the supported property names failed with message: " + e.getMessage());
    }
  }

  public void testSetProperty() throws XQException {

    try {
      xqds.setProperty(null, "foo");
      junit.framework.Assert.fail("A-XQDS-7.1: setProperty() throws an XQException when a null value is specified for the name parameter.");
    } catch (XQException e) {
      // Expect an XQException
    } catch (Exception other_e) {
      junit.framework.Assert.fail("A-XQDS-7.1: setProperty() throws an XQException when a null value is specified for the name parameter.");
    }

    try {
      // OK, we could check the supported properties to make sure we're using an unknown property.
      // But, let's get reasonable... Use a hard coded name.
      xqds.setProperty("the_property_that_not_known_to_any_implementation", "foo");
      junit.framework.Assert.fail("A-XQDS-7.2: setProperty() throws an XQException when an invalid property name is specified.");
    } catch (XQException e) {
      // Expect an XQException
    } catch (Exception other_e) {
      junit.framework.Assert.fail("A-XQDS-7.2: setProperty() throws an XQException when an invalid property name is specified.");
    }
  }

  public void testGetProperty() throws XQException {
 
    try {
      xqds.getProperty(null);
      junit.framework.Assert.fail("A-XQDS-8.1: getProperty() throws an XQException when a null value is specified for the name parameter.");
    } catch (XQException e) {
      // Expect an XQException
    } catch (Exception other_e) {
      junit.framework.Assert.fail("A-XQDS-8.1: getProperty() throws an XQException when a null value is specified for the name parameter.");
    }

    try {
      // OK, we could check the supported properties to make sure we're using an unknown property.
      // But, let's get reasonable... Use a hard coded name.
      xqds.getProperty("the_property_that_not_known_to_any_implementation");
      junit.framework.Assert.fail("A-XQDS-8.2: getProperty() throws an XQException when an invalid property name is specified.");
    } catch (XQException e) {
      // Expect an XQException
    } catch (Exception other_e) {
      junit.framework.Assert.fail("A-XQDS-8.2: getProperty() throws an XQException when an invalid property name is specified.");
    }
    
    String propertyNames[] = xqds.getSupportedPropertyNames();
    
    for(int i = 0; i < propertyNames.length; i++) {
      try {
        xqds.getProperty(propertyNames[i]);
      } catch (XQException e) {
        junit.framework.Assert.fail("A-XQDS-8.3: getProperty() failed for property '" + propertyNames[i] + "' with message: " + e.getMessage());
      }
    }
  }

  public void testSetProperties() throws XQException {

    try {
      xqds.setProperties(null);
      junit.framework.Assert.fail("A-XQDS-9.1: setProperties() throws an XQException when a null value is specified for the properties.");
    } catch (XQException e) {
      // Expect an XQException
    } catch (Exception other_e) {
      junit.framework.Assert.fail("A-XQDS-9.1: setProperties() throws an XQException when a null value is specified for the propertiesr.");
    }

    try {
      // OK, we could check the supported properties to make sure we're using an unknown property.
      // But, let's get reasonable... Use a hard coded name.
      Properties p = new Properties();
      p.put("the_property_that_not_known_to_any_implementation", "foo");
      xqds.setProperties(p);
      junit.framework.Assert.fail("A-XQDS-9.2: setProperties() throws an XQException when an invalid property name is specified.");
    } catch (XQException e) {
      // Expect an XQException
    } catch (Exception other_e) {
      junit.framework.Assert.fail("A-XQDS-9.2: setProperties() throws an XQException when an invalid property name is specified.");
    }
  }

  public void testSetLoginTimeout() throws XQException {
    try {
      int timeout = xqds.getLoginTimeout();
      xqds.setLoginTimeout(timeout);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDS-10.1: setting the login timeout failed with message: " + e.getMessage());
    }
  }

  public void testSetLogWriter() throws XQException {
    try {
      xqds.setLogWriter(new PrintWriter(new StringWriter()));
      Writer log = xqds.getLogWriter();
      junit.framework.Assert.assertNotNull("A-XQDS-11.1: Successfully set the log writer.", log);
    } catch (XQException e) {
      junit.framework.Assert.fail("A-XQDS-11.1: Setting the log writer failed with message: " + e.getMessage());
    }
  }
}
