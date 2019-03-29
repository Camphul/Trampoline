#!/bin/bash
# This script generates a collection of javadoc
JAVADOC_DIR="/target/apidocs"
echo "Generating static javadoc html for Trampoline..."
mvn clean install -DskipTests -Dgpg.skip
rm -rf site
mkdir -p site
for i in $(ls | grep trampoline- | grep -v 'starter$'); do
    CURDIR="$i$JAVADOC_DIR"
    TODIR="site/$i"
    echo ${CURDIR}
    echo ${TODIR}
    cp -ar ${CURDIR} ${TODIR}
done
echo "Generated javadoc into /sites/ directory"