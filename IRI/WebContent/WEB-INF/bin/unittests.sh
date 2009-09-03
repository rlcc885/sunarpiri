# Expresso Unit/Self tests
# JAVA_HOME must be set

# Set the below as appropriate for your installation
CONFIGDIR=/home/expresso/expresso/expresso-web/WEB-INF/config
WEBDIR=/home/expresso/expresso/expresso-web
DB=site
CLASSPATH=$WEBDIR/WEB-INF/classes:/home/tomcat/jakarta-tomcat-4.0-b5/common/lib/servlet.jar:$WEBDIR/WEB-INF/lib/log4j-core.jar:$WEBDIR/WEB-INF/lib/struts.jar:$WEBDIR/WEB-INF/lib/xerces.jar:$WEBDIR/WEB-INF/lib/hsql.jar; export CLASSPATH
$JAVA_HOME/bin/java com.jcorporate.expresso.services.test.SwingTestRunner configDir=$CONFIGDIR webAppDir=$WEBDIR

