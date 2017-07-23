@echo off
mkdir src\docroot\WEB-INF\classes
mkdir src\docroot\WEB-INF\classes\demo
mkdir src\docroot\WEB-INF\lib
cd src\classes
echo compiling classes directory
javac -d ..\docroot\WEB-INF\classes demo\*.java
cd ..\taglib
echo compiling lib directory
javac -classpath "..\docroot\WEB-INF\classes;%CLASSPATH%" demo\*.java
echo creating tag library archive
jar cvf ..\docroot\WEB-INF\lib\jtl.jar META-INF demo\*.class
del demo\*.class
cd ..\docroot
echo creating web archive
jar cvf ..\..\javamail.war index.html *.jsp WEB-INF
cd WEB-INF\classes\demo
del *.*
cd ..
rmdir demo
cd ..
rmdir classes
cd lib
del *.*
cd ..
rmdir lib
cd ..\..\..
