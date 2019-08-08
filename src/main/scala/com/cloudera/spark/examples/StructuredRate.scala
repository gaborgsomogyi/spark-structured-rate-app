/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera.spark.examples

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.OutputMode

object StructuredRate {
  def main(args: Array[String]) {
    if (args.length != 2) {
      System.err.println(s"""
                            |Usage: StructuredRate <checkpoint-location> <rows-per-second>
                            |Example: StructuredRate hdfs://quickstart.cloudera:8020/checkpoint 10
                            |  <checkpoint-location> Directory in which to create checkpoints.
                            |  <rows-per-second> How many rows per second should come from source side.
      """.stripMargin)
      System.exit(1)
    }

    val Array(checkpointLocation, rowsPerSecond) = args

    val spark = SparkSession
      .builder
      .appName("StructuredRate")
      .getOrCreate()

    val lines = spark
      .readStream
      .format("rate")
      .option("rowsPerSecond", rowsPerSecond)
      .load()

    val query = lines.writeStream
      .outputMode(OutputMode.Append)
      .format("console")
      .option("checkpointLocation", checkpointLocation)
      .start()

    query.awaitTermination()
  }
}
