spark-structured-rate-app
============

### Build the app
To build, you need Scala 2.11, git and maven on the box.
Do a git clone of this repo and then run:
```
cd spark-structured-rate-app
mvn clean package
```
Then, take the generated uber jar from `target/spark-structured-rate-app-1.0-SNAPSHOT-jar-with-dependencies.jar` to the spark client node (where you are going to launch the query from). Let's assume you place this file in the home directory of this client machine.

### Running the app
### spark-submit
Now run the following command:
```
spark2-submit --num-executors 4 --master yarn --deploy-mode cluster --class com.cloudera.spark.examples.StructuredRate spark-structured-rate-app-1.0-SNAPSHOT-jar-with-dependencies.jar hdfs://quickstart.cloudera:8020/checkpoint 10
```
