#!/bin/sh
java -classpath /home/tomcat/jakarta-tomcat-4.0-b5/common/lib/servlet.jar:/home/expresso/expresso/expresso-web/WEB-INF/classes:/home/expresso/expresso/expresso-web/WEB-INF/lib/mysql_comp.jar:/home/expresso/expresso/expresso-web/WEB-INF/lib/activation.jar:/home/expresso/expresso/expresso-web/WEB-INF/lib/mail.jar com.jcorporate.expresso.core.utility.ControllerRun $*
