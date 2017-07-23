public abstract interface javax.xml.xquery.ConnectionPoolXQDataSource
{
public abstract [Ljava.lang.String; getSupportedPropertyNames ()
public abstract int getLoginTimeout () throws javax.xml.xquery.XQException
public abstract java.io.PrintWriter getLogWriter () throws javax.xml.xquery.XQException
public abstract java.lang.String getProperty (java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.PooledXQConnection getPooledConnection () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.PooledXQConnection getPooledConnection (java.lang.String,java.lang.String) throws javax.xml.xquery.XQException
public abstract void setLogWriter (java.io.PrintWriter) throws javax.xml.xquery.XQException
public abstract void setLoginTimeout (int) throws javax.xml.xquery.XQException
public abstract void setProperties (java.util.Properties) throws javax.xml.xquery.XQException
public abstract void setProperty (java.lang.String,java.lang.String) throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.PooledXQConnection
{
public abstract javax.xml.xquery.XQConnection getConnection () throws javax.xml.xquery.XQException
public abstract void addConnectionEventListener (javax.xml.xquery.XQConnectionEventListener)
public abstract void close () throws javax.xml.xquery.XQException
public abstract void removeConnectionEventListener (javax.xml.xquery.XQConnectionEventListener)
}

public javax.xml.xquery.XQCancelledException extends javax.xml.xquery.XQQueryException
{
public javax.xml.xquery.XQCancelledException (java.lang.String,java.lang.String,javax.xml.namespace.QName,int,int,int,java.lang.String,javax.xml.xquery.XQSequence,[Ljavax.xml.xquery.XQStackTraceElement;)
}

public abstract interface javax.xml.xquery.XQConnection implements javax.xml.xquery.XQDataFactory
{
public abstract boolean getAutoCommit () throws javax.xml.xquery.XQException
public abstract boolean isClosed ()
public abstract javax.xml.xquery.XQExpression createExpression () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQExpression createExpression (javax.xml.xquery.XQStaticContext) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQMetaData getMetaData () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQPreparedExpression prepareExpression (java.io.InputStream) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQPreparedExpression prepareExpression (java.io.InputStream,javax.xml.xquery.XQStaticContext) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQPreparedExpression prepareExpression (java.io.Reader) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQPreparedExpression prepareExpression (java.io.Reader,javax.xml.xquery.XQStaticContext) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQPreparedExpression prepareExpression (java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQPreparedExpression prepareExpression (java.lang.String,javax.xml.xquery.XQStaticContext) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQStaticContext getStaticContext () throws javax.xml.xquery.XQException
public abstract void close () throws javax.xml.xquery.XQException
public abstract void commit () throws javax.xml.xquery.XQException
public abstract void rollback () throws javax.xml.xquery.XQException
public abstract void setAutoCommit (boolean) throws javax.xml.xquery.XQException
public abstract void setStaticContext (javax.xml.xquery.XQStaticContext) throws javax.xml.xquery.XQException
}

public javax.xml.xquery.XQConnectionEvent extends java.util.EventObject
{
public javax.xml.xquery.XQConnectionEvent (javax.xml.xquery.PooledXQConnection)
public javax.xml.xquery.XQConnectionEvent (javax.xml.xquery.PooledXQConnection,javax.xml.xquery.XQException)
public javax.xml.xquery.XQException getXQException ()
}

public abstract interface javax.xml.xquery.XQConnectionEventListener
{
public abstract void connectionClosed (javax.xml.xquery.XQConnectionEvent)
public abstract void connectionErrorOccurred (javax.xml.xquery.XQConnectionEvent)
}

public final javax.xml.xquery.XQConstants extends java.lang.Object
{
public static final int BINDING_MODE_DEFERRED
public static final int BINDING_MODE_IMMEDIATE
public static final int BOUNDARY_SPACE_PRESERVE
public static final int BOUNDARY_SPACE_STRIP
public static final int CONSTRUCTION_MODE_PRESERVE
public static final int CONSTRUCTION_MODE_STRIP
public static final int COPY_NAMESPACES_MODE_INHERIT
public static final int COPY_NAMESPACES_MODE_NO_INHERIT
public static final int COPY_NAMESPACES_MODE_NO_PRESERVE
public static final int COPY_NAMESPACES_MODE_PRESERVE
public static final int DEFAULT_ORDER_FOR_EMPTY_SEQUENCES_GREATEST
public static final int DEFAULT_ORDER_FOR_EMPTY_SEQUENCES_LEAST
public static final int HOLDTYPE_CLOSE_CURSORS_AT_COMMIT
public static final int HOLDTYPE_HOLD_CURSORS_OVER_COMMIT
public static final int LANGTYPE_XQUERY
public static final int LANGTYPE_XQUERYX
public static final int ORDERING_MODE_ORDERED
public static final int ORDERING_MODE_UNORDERED
public static final int SCROLLTYPE_FORWARD_ONLY
public static final int SCROLLTYPE_SCROLLABLE
public static final javax.xml.namespace.QName CONTEXT_ITEM
}

public abstract interface javax.xml.xquery.XQDataFactory
{
public abstract javax.xml.xquery.XQItem createItem (javax.xml.xquery.XQItem) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromAtomicValue (java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromBoolean (boolean,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromByte (byte,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDocument (java.io.InputStream,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDocument (java.io.Reader,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDocument (java.lang.String,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDocument (javax.xml.stream.XMLStreamReader,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDocument (javax.xml.transform.Source,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDocument (org.xml.sax.XMLReader,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromDouble (double,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromFloat (float,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromInt (int,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromLong (long,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromNode (org.w3c.dom.Node,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromObject (java.lang.Object,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromShort (short,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem createItemFromString (java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createAtomicType (int) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createAtomicType (int,javax.xml.namespace.QName,java.net.URI) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createAttributeType (javax.xml.namespace.QName,int) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createAttributeType (javax.xml.namespace.QName,int,javax.xml.namespace.QName,java.net.URI) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createCommentType () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createDocumentElementType (javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createDocumentSchemaElementType (javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createDocumentType () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createElementType (javax.xml.namespace.QName,int) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createElementType (javax.xml.namespace.QName,int,javax.xml.namespace.QName,java.net.URI,boolean) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createItemType () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createNodeType () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createProcessingInstructionType (java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createSchemaAttributeType (javax.xml.namespace.QName,int,java.net.URI) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createSchemaElementType (javax.xml.namespace.QName,int,java.net.URI) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType createTextType () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQSequence createSequence (java.util.Iterator) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQSequence createSequence (javax.xml.xquery.XQSequence) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQSequenceType createSequenceType (javax.xml.xquery.XQItemType,int) throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQDataSource
{
public abstract [Ljava.lang.String; getSupportedPropertyNames ()
public abstract int getLoginTimeout () throws javax.xml.xquery.XQException
public abstract java.io.PrintWriter getLogWriter () throws javax.xml.xquery.XQException
public abstract java.lang.String getProperty (java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQConnection getConnection () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQConnection getConnection (java.lang.String,java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQConnection getConnection (java.sql.Connection) throws javax.xml.xquery.XQException
public abstract void setLogWriter (java.io.PrintWriter) throws javax.xml.xquery.XQException
public abstract void setLoginTimeout (int) throws javax.xml.xquery.XQException
public abstract void setProperties (java.util.Properties) throws javax.xml.xquery.XQException
public abstract void setProperty (java.lang.String,java.lang.String) throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQDynamicContext
{
public abstract java.util.TimeZone getImplicitTimeZone () throws javax.xml.xquery.XQException
public abstract void bindAtomicValue (javax.xml.namespace.QName,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindBoolean (javax.xml.namespace.QName,boolean,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindByte (javax.xml.namespace.QName,byte,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDocument (javax.xml.namespace.QName,java.io.InputStream,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDocument (javax.xml.namespace.QName,java.io.Reader,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDocument (javax.xml.namespace.QName,java.lang.String,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDocument (javax.xml.namespace.QName,javax.xml.stream.XMLStreamReader,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDocument (javax.xml.namespace.QName,javax.xml.transform.Source,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDocument (javax.xml.namespace.QName,org.xml.sax.XMLReader,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindDouble (javax.xml.namespace.QName,double,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindFloat (javax.xml.namespace.QName,float,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindInt (javax.xml.namespace.QName,int,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindItem (javax.xml.namespace.QName,javax.xml.xquery.XQItem) throws javax.xml.xquery.XQException
public abstract void bindLong (javax.xml.namespace.QName,long,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindNode (javax.xml.namespace.QName,org.w3c.dom.Node,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindObject (javax.xml.namespace.QName,java.lang.Object,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindSequence (javax.xml.namespace.QName,javax.xml.xquery.XQSequence) throws javax.xml.xquery.XQException
public abstract void bindShort (javax.xml.namespace.QName,short,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void bindString (javax.xml.namespace.QName,java.lang.String,javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract void setImplicitTimeZone (java.util.TimeZone) throws javax.xml.xquery.XQException
}

public javax.xml.xquery.XQException extends java.lang.Exception
{
public javax.xml.xquery.XQException (java.lang.String)
public javax.xml.xquery.XQException (java.lang.String,java.lang.String)
public java.lang.String getVendorCode ()
public javax.xml.xquery.XQException getNextException ()
public void setNextException (javax.xml.xquery.XQException)
}

public abstract interface javax.xml.xquery.XQExpression implements javax.xml.xquery.XQDynamicContext
{
public abstract boolean isClosed ()
public abstract javax.xml.xquery.XQResultSequence executeQuery (java.io.InputStream) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQResultSequence executeQuery (java.io.Reader) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQResultSequence executeQuery (java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQStaticContext getStaticContext () throws javax.xml.xquery.XQException
public abstract void cancel () throws javax.xml.xquery.XQException
public abstract void close () throws javax.xml.xquery.XQException
public abstract void executeCommand (java.io.Reader) throws javax.xml.xquery.XQException
public abstract void executeCommand (java.lang.String) throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQItem implements javax.xml.xquery.XQItemAccessor
{
public abstract boolean isClosed ()
public abstract void close () throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQItemAccessor
{
public abstract boolean getBoolean () throws javax.xml.xquery.XQException
public abstract boolean instanceOf (javax.xml.xquery.XQItemType) throws javax.xml.xquery.XQException
public abstract byte getByte () throws javax.xml.xquery.XQException
public abstract double getDouble () throws javax.xml.xquery.XQException
public abstract float getFloat () throws javax.xml.xquery.XQException
public abstract int getInt () throws javax.xml.xquery.XQException
public abstract java.lang.Object getObject () throws javax.xml.xquery.XQException
public abstract java.lang.String getAtomicValue () throws javax.xml.xquery.XQException
public abstract java.lang.String getItemAsString (java.util.Properties) throws javax.xml.xquery.XQException
public abstract java.net.URI getNodeUri () throws javax.xml.xquery.XQException
public abstract javax.xml.stream.XMLStreamReader getItemAsStream () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType getItemType () throws javax.xml.xquery.XQException
public abstract long getLong () throws javax.xml.xquery.XQException
public abstract org.w3c.dom.Node getNode () throws javax.xml.xquery.XQException
public abstract short getShort () throws javax.xml.xquery.XQException
public abstract void writeItem (java.io.OutputStream,java.util.Properties) throws javax.xml.xquery.XQException
public abstract void writeItem (java.io.Writer,java.util.Properties) throws javax.xml.xquery.XQException
public abstract void writeItemToResult (javax.xml.transform.Result) throws javax.xml.xquery.XQException
public abstract void writeItemToSAX (org.xml.sax.ContentHandler) throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQItemType implements javax.xml.xquery.XQSequenceType
{
public static final int XQBASETYPE_ANYATOMICTYPE
public static final int XQBASETYPE_ANYSIMPLETYPE
public static final int XQBASETYPE_ANYTYPE
public static final int XQBASETYPE_ANYURI
public static final int XQBASETYPE_BASE64BINARY
public static final int XQBASETYPE_BOOLEAN
public static final int XQBASETYPE_BYTE
public static final int XQBASETYPE_DATE
public static final int XQBASETYPE_DATETIME
public static final int XQBASETYPE_DAYTIMEDURATION
public static final int XQBASETYPE_DECIMAL
public static final int XQBASETYPE_DOUBLE
public static final int XQBASETYPE_DURATION
public static final int XQBASETYPE_ENTITIES
public static final int XQBASETYPE_ENTITY
public static final int XQBASETYPE_FLOAT
public static final int XQBASETYPE_GDAY
public static final int XQBASETYPE_GMONTH
public static final int XQBASETYPE_GMONTHDAY
public static final int XQBASETYPE_GYEAR
public static final int XQBASETYPE_GYEARMONTH
public static final int XQBASETYPE_HEXBINARY
public static final int XQBASETYPE_ID
public static final int XQBASETYPE_IDREF
public static final int XQBASETYPE_IDREFS
public static final int XQBASETYPE_INT
public static final int XQBASETYPE_INTEGER
public static final int XQBASETYPE_LANGUAGE
public static final int XQBASETYPE_LONG
public static final int XQBASETYPE_NAME
public static final int XQBASETYPE_NCNAME
public static final int XQBASETYPE_NEGATIVE_INTEGER
public static final int XQBASETYPE_NMTOKEN
public static final int XQBASETYPE_NMTOKENS
public static final int XQBASETYPE_NONNEGATIVE_INTEGER
public static final int XQBASETYPE_NONPOSITIVE_INTEGER
public static final int XQBASETYPE_NORMALIZED_STRING
public static final int XQBASETYPE_NOTATION
public static final int XQBASETYPE_POSITIVE_INTEGER
public static final int XQBASETYPE_QNAME
public static final int XQBASETYPE_SHORT
public static final int XQBASETYPE_STRING
public static final int XQBASETYPE_TIME
public static final int XQBASETYPE_TOKEN
public static final int XQBASETYPE_UNSIGNED_BYTE
public static final int XQBASETYPE_UNSIGNED_INT
public static final int XQBASETYPE_UNSIGNED_LONG
public static final int XQBASETYPE_UNSIGNED_SHORT
public static final int XQBASETYPE_UNTYPED
public static final int XQBASETYPE_UNTYPEDATOMIC
public static final int XQBASETYPE_YEARMONTHDURATION
public static final int XQITEMKIND_ATOMIC
public static final int XQITEMKIND_ATTRIBUTE
public static final int XQITEMKIND_COMMENT
public static final int XQITEMKIND_DOCUMENT
public static final int XQITEMKIND_DOCUMENT_ELEMENT
public static final int XQITEMKIND_DOCUMENT_SCHEMA_ELEMENT
public static final int XQITEMKIND_ELEMENT
public static final int XQITEMKIND_ITEM
public static final int XQITEMKIND_NODE
public static final int XQITEMKIND_PI
public static final int XQITEMKIND_SCHEMA_ATTRIBUTE
public static final int XQITEMKIND_SCHEMA_ELEMENT
public static final int XQITEMKIND_TEXT
public abstract boolean equals (java.lang.Object)
public abstract boolean isAnonymousType ()
public abstract boolean isElementNillable ()
public abstract int getBaseType () throws javax.xml.xquery.XQException
public abstract int getItemKind ()
public abstract int getItemOccurrence ()
public abstract int hashCode ()
public abstract java.lang.String getPIName () throws javax.xml.xquery.XQException
public abstract java.lang.String toString ()
public abstract java.net.URI getSchemaURI ()
public abstract javax.xml.namespace.QName getNodeName () throws javax.xml.xquery.XQException
public abstract javax.xml.namespace.QName getTypeName () throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQMetaData
{
public abstract boolean isFullAxisFeatureSupported () throws javax.xml.xquery.XQException
public abstract boolean isModuleFeatureSupported () throws javax.xml.xquery.XQException
public abstract boolean isReadOnly () throws javax.xml.xquery.XQException
public abstract boolean isSchemaImportFeatureSupported () throws javax.xml.xquery.XQException
public abstract boolean isSchemaValidationFeatureSupported () throws javax.xml.xquery.XQException
public abstract boolean isSerializationFeatureSupported () throws javax.xml.xquery.XQException
public abstract boolean isStaticTypingExtensionsSupported () throws javax.xml.xquery.XQException
public abstract boolean isStaticTypingFeatureSupported () throws javax.xml.xquery.XQException
public abstract boolean isTransactionSupported () throws javax.xml.xquery.XQException
public abstract boolean isUserDefinedXMLSchemaTypeSupported () throws javax.xml.xquery.XQException
public abstract boolean isXQueryEncodingDeclSupported () throws javax.xml.xquery.XQException
public abstract boolean isXQueryEncodingSupported (java.lang.String) throws javax.xml.xquery.XQException
public abstract boolean isXQueryXSupported () throws javax.xml.xquery.XQException
public abstract boolean wasCreatedFromJDBCConnection () throws javax.xml.xquery.XQException
public abstract int getMaxExpressionLength () throws javax.xml.xquery.XQException
public abstract int getMaxUserNameLength () throws javax.xml.xquery.XQException
public abstract int getProductMajorVersion () throws javax.xml.xquery.XQException
public abstract int getProductMinorVersion () throws javax.xml.xquery.XQException
public abstract int getXQJMajorVersion () throws javax.xml.xquery.XQException
public abstract int getXQJMinorVersion () throws javax.xml.xquery.XQException
public abstract java.lang.String getProductName () throws javax.xml.xquery.XQException
public abstract java.lang.String getProductVersion () throws javax.xml.xquery.XQException
public abstract java.lang.String getUserName () throws javax.xml.xquery.XQException
public abstract java.lang.String getXQJVersion () throws javax.xml.xquery.XQException
public abstract java.util.Set getSupportedXQueryEncodings () throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQPreparedExpression implements javax.xml.xquery.XQDynamicContext
{
public abstract [Ljavax.xml.namespace.QName; getAllExternalVariables () throws javax.xml.xquery.XQException
public abstract [Ljavax.xml.namespace.QName; getAllUnboundExternalVariables () throws javax.xml.xquery.XQException
public abstract boolean isClosed ()
public abstract javax.xml.xquery.XQResultSequence executeQuery () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQSequenceType getStaticResultType () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQSequenceType getStaticVariableType (javax.xml.namespace.QName) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQStaticContext getStaticContext () throws javax.xml.xquery.XQException
public abstract void cancel () throws javax.xml.xquery.XQException
public abstract void close () throws javax.xml.xquery.XQException
}

public javax.xml.xquery.XQQueryException extends javax.xml.xquery.XQException
{
public javax.xml.xquery.XQQueryException (java.lang.String)
public javax.xml.xquery.XQQueryException (java.lang.String,java.lang.String,javax.xml.namespace.QName,int,int,int)
public javax.xml.xquery.XQQueryException (java.lang.String,java.lang.String,javax.xml.namespace.QName,int,int,int,java.lang.String,javax.xml.xquery.XQSequence,[Ljavax.xml.xquery.XQStackTraceElement;)
public javax.xml.xquery.XQQueryException (java.lang.String,javax.xml.namespace.QName)
public javax.xml.xquery.XQQueryException (java.lang.String,javax.xml.namespace.QName,int,int,int)
public [Ljavax.xml.xquery.XQStackTraceElement; getQueryStackTrace ()
public int getColumnNumber ()
public int getLineNumber ()
public int getPosition ()
public java.lang.String getModuleURI ()
public javax.xml.namespace.QName getErrorCode ()
public javax.xml.xquery.XQSequence getErrorObject ()
}

public abstract interface javax.xml.xquery.XQResultItem implements javax.xml.xquery.XQItem
{
public abstract javax.xml.xquery.XQConnection getConnection () throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQResultSequence implements javax.xml.xquery.XQSequence
{
public abstract javax.xml.xquery.XQConnection getConnection () throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQSequence implements javax.xml.xquery.XQItemAccessor
{
public abstract boolean absolute (int) throws javax.xml.xquery.XQException
public abstract boolean first () throws javax.xml.xquery.XQException
public abstract boolean isAfterLast () throws javax.xml.xquery.XQException
public abstract boolean isBeforeFirst () throws javax.xml.xquery.XQException
public abstract boolean isClosed ()
public abstract boolean isFirst () throws javax.xml.xquery.XQException
public abstract boolean isLast () throws javax.xml.xquery.XQException
public abstract boolean isOnItem () throws javax.xml.xquery.XQException
public abstract boolean isScrollable () throws javax.xml.xquery.XQException
public abstract boolean last () throws javax.xml.xquery.XQException
public abstract boolean next () throws javax.xml.xquery.XQException
public abstract boolean previous () throws javax.xml.xquery.XQException
public abstract boolean relative (int) throws javax.xml.xquery.XQException
public abstract int count () throws javax.xml.xquery.XQException
public abstract int getPosition () throws javax.xml.xquery.XQException
public abstract java.lang.String getSequenceAsString (java.util.Properties) throws javax.xml.xquery.XQException
public abstract javax.xml.stream.XMLStreamReader getSequenceAsStream () throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItem getItem () throws javax.xml.xquery.XQException
public abstract void afterLast () throws javax.xml.xquery.XQException
public abstract void beforeFirst () throws javax.xml.xquery.XQException
public abstract void close () throws javax.xml.xquery.XQException
public abstract void writeSequence (java.io.OutputStream,java.util.Properties) throws javax.xml.xquery.XQException
public abstract void writeSequence (java.io.Writer,java.util.Properties) throws javax.xml.xquery.XQException
public abstract void writeSequenceToResult (javax.xml.transform.Result) throws javax.xml.xquery.XQException
public abstract void writeSequenceToSAX (org.xml.sax.ContentHandler) throws javax.xml.xquery.XQException
}

public abstract interface javax.xml.xquery.XQSequenceType
{
public static final int OCC_EMPTY
public static final int OCC_EXACTLY_ONE
public static final int OCC_ONE_OR_MORE
public static final int OCC_ZERO_OR_MORE
public static final int OCC_ZERO_OR_ONE
public abstract boolean equals (java.lang.Object)
public abstract int getItemOccurrence ()
public abstract int hashCode ()
public abstract java.lang.String toString ()
public abstract javax.xml.xquery.XQItemType getItemType ()
}

public javax.xml.xquery.XQStackTraceElement extends java.lang.Object implements java.io.Serializable
{
public javax.xml.xquery.XQStackTraceElement (java.lang.String,int,int,int,javax.xml.namespace.QName,[Ljavax.xml.xquery.XQStackTraceVariable;)
public [Ljavax.xml.xquery.XQStackTraceVariable; getVariables ()
public int getColumnNumber ()
public int getLineNumber ()
public int getPosition ()
public java.lang.String getModuleURI ()
public javax.xml.namespace.QName getFunctionQName ()
}

public javax.xml.xquery.XQStackTraceVariable extends java.lang.Object implements java.io.Serializable
{
public javax.xml.xquery.XQStackTraceVariable (javax.xml.namespace.QName,java.lang.String)
public java.lang.String getValue ()
public javax.xml.namespace.QName getQName ()
}

public abstract interface javax.xml.xquery.XQStaticContext
{
public abstract [Ljava.lang.String; getNamespacePrefixes ()
public abstract int getBindingMode ()
public abstract int getBoundarySpacePolicy ()
public abstract int getConstructionMode ()
public abstract int getCopyNamespacesModeInherit ()
public abstract int getCopyNamespacesModePreserve ()
public abstract int getDefaultOrderForEmptySequences ()
public abstract int getHoldability ()
public abstract int getOrderingMode ()
public abstract int getQueryLanguageTypeAndVersion ()
public abstract int getQueryTimeout ()
public abstract int getScrollability ()
public abstract java.lang.String getBaseURI ()
public abstract java.lang.String getDefaultCollation ()
public abstract java.lang.String getDefaultElementTypeNamespace ()
public abstract java.lang.String getDefaultFunctionNamespace ()
public abstract java.lang.String getNamespaceURI (java.lang.String) throws javax.xml.xquery.XQException
public abstract javax.xml.xquery.XQItemType getContextItemStaticType ()
public abstract void declareNamespace (java.lang.String,java.lang.String) throws javax.xml.xquery.XQException
public abstract void setBaseURI (java.lang.String) throws javax.xml.xquery.XQException
public abstract void setBindingMode (int) throws javax.xml.xquery.XQException
public abstract void setBoundarySpacePolicy (int) throws javax.xml.xquery.XQException
public abstract void setConstructionMode (int) throws javax.xml.xquery.XQException
public abstract void setContextItemStaticType (javax.xml.xquery.XQItemType)
public abstract void setCopyNamespacesModeInherit (int) throws javax.xml.xquery.XQException
public abstract void setCopyNamespacesModePreserve (int) throws javax.xml.xquery.XQException
public abstract void setDefaultCollation (java.lang.String) throws javax.xml.xquery.XQException
public abstract void setDefaultElementTypeNamespace (java.lang.String) throws javax.xml.xquery.XQException
public abstract void setDefaultFunctionNamespace (java.lang.String) throws javax.xml.xquery.XQException
public abstract void setDefaultOrderForEmptySequences (int) throws javax.xml.xquery.XQException
public abstract void setHoldability (int) throws javax.xml.xquery.XQException
public abstract void setOrderingMode (int) throws javax.xml.xquery.XQException
public abstract void setQueryLanguageTypeAndVersion (int) throws javax.xml.xquery.XQException
public abstract void setQueryTimeout (int) throws javax.xml.xquery.XQException
public abstract void setScrollability (int) throws javax.xml.xquery.XQException
}

