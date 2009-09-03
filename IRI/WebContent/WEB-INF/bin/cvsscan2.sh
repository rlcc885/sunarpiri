#!/bin/sh
CVSROOT=:pserver:javacorp@javacorporate.com:/home/javacorp/.cvs/expresso; export CVSROOT
cd /home/expresso/expresso/expresso-web
runsocks cvs -z9 status * > /tmp/xx
date > /tmp/cvsresults
cat /tmp/xx | awk 'BEGIN { inrecord = 0; }
/=========================/ { if (inrecord = 1) 
	{ inrecord = 0; print FILENAME "|" PATHNAME "|" CVSSTATUS ; }
else inrecord = 1; }
/File:/ { FILENAME = $2; CVSSTATUS = $4 " " $5 }
/Repository revision:/ { PATHNAME = $4 }
END { print $FILENAME "|" $PATHNAME "|" $CVSSTATUS }' | grep -v "Up-to-date" | grep -v "Attic" >> /tmp/cvsresults
echo "See /tmp/cvsresults for results"

