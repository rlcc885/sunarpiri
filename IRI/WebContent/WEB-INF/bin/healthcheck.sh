#!/bin/sh
java -classpath /usr/orion/orion.jar:/usr/orion/applications/expresso/expresso-web/WEB-INF/classes:/usr/orion/applications/expresso/expresso-web/WEB-INF/lib/mysql_comp.jar:/usr/orion/applications/expresso/expresso-web/WEB-INF/activation.jar:/usr/orion/applications/expresso/expresso-web/WEB-INF/lib/mail.jar com.jcorporate.expresso.core.utility.HealthCheck configDir=/usr/orion/applications/expresso/expresso-web/config db=site
