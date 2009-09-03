set JAVA_HOME=c:\jdk1.3
set APPDIR=d:\orion\applications\expresso\expresso-web
echo "Creating javadoc documentation into %APPDIR%\javadoc"
cd %APPDIR%\javadoc
set CLASSPATH=%APPDIR%\WEB-INF\classes
%JAVA_HOME%\bin\javadoc -J-Xmx64m -J-Xms64m -doclet JP.co.esm.caddies.doclets.UMLDoclet -private -d %APPDIR%\javadoc -classpath %CLASSPATH% @%APPDIR%\bin\packages

