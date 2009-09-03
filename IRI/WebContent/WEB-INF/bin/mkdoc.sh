PATH=$PATH:$HOME/bin:/usr/java/jdk1.2/bin:/usr/java/bin
CLASSPATH=/usr/java/jdk1.2/lib:/usr/java/doclet:/usr/java/lib:/usr/java/JSDK2.0/lib/jsdk.jar:/usr/java/lib/activation.jar:/usr/java/lib/mail.jar
export CLASSPATH PATH

. settings.sh
echo "Creating javadoc documentation into $WEBDIR/javadoc"
rm -rf $WEBDIR/javadoc/*
cd $WEBDIR/javadoc
/usr/java/jdk1.2/bin/javadoc -J-Xmx32m -J-Xms32m -doclet JP.co.esm.caddies.doclets.UMLDoclet -private -d $WEBDIR/javadoc -classpath $CLASSPATH @/usr/java/bin/packages

cd $WEBDIR
find javadoc -print > thefiles
rm -f javadoc.tar.gz
tar cvzfT javadoc.tar.gz thefiles
mv javadoc.tar.gz $WEBDIR/downloads
echo "javadoc.tar.gz Moved to $WEBDIR/downloads"

