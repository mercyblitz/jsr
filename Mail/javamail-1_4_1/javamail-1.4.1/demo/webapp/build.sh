mkdir src/docroot/WEB-INF/classes
mkdir src/docroot/WEB-INF/lib
cd src/classes
echo "compiling classes directory"
javac -d ../docroot/WEB-INF/classes demo/*.java
cd ../taglib
echo "compiling lib directroy"
javac -classpath ../docroot/WEB-INF/classes:$CLASSPATH demo/*.java
echo "creating tag library archive"
jar cvf ../docroot/WEB-INF/lib/jtl.jar META-INF demo/*.class
rm demo/*.class
cd ../docroot
echo "creating web archive"
jar cvf ../../javamail.war index.html *.jsp WEB-INF
rm -r WEB-INF/classes
rm -r WEB-INF/lib
cd ../..
