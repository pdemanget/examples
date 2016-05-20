JMH-DEMO
========

This project is based on the JMH tutorial
http://openjdk.java.net/projects/code-tools/jmh/

The first commit is the result or the tutorial. Some benchmark class have been added (from stackoverflow etc.)


Usage
-----
java -jar target/benchmark.jar your.test.ClassName

### for eclipse
run manually with run configuration/new... then: 
launch org.openjdk.jmh.Main your.test.ClassName

Debug
-----
Bench are launched in a "Worker" in a execution ThreadPool, while main iteration is done in main Thread.

Here I wanted to know where to parameter the iteration count:
Code of iteration is in BaseRunner.runBenchmark
We see then that the count of iteration is parametered in benchParams.getMeasurement().getCount(). I deduced then that it comes from @Measurement annotation?
Wrong, i Proceed my debug, Come from : Runner.newBenchmarkParams


