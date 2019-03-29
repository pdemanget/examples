mvn install
cd target
java -cp jlink-example-0.0.1-SNAPSHOT.jar example.Example

jar --describe-module --file jlink-example-0.0.1-SNAPSHOT.jar
# it displays the module name: jlink.example

#run
java --module-path . -m jlink.example/example.Example 

#package with jlink
$JAVA_HOME/bin/jlink --module-path target/classes --add-modules jlinkExample --launcher startit=jlinkExample/example.Example  --output target/dist --strip-debug --compress 2 --no-header-files --no-man-pages
