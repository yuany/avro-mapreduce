/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.topsoft.botspider.avro.mapreduce.output;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * A extends class for {@link FileOutputFormat}s that read from
 * {@link FileSystem}s.
 */
public abstract class ExtFileOutputFormat<K,V> extends FileOutputFormat<K,V> {
  protected static final String BASE_OUTPUT_NAME = "mapreduce.output.basename";
  protected static final String PART = "part";
  
  public Path getDefaultWorkFile(TaskAttemptContext context, String extension)
      throws IOException {
    FileOutputCommitter committer = (FileOutputCommitter) getOutputCommitter(context);
    return new Path(committer.getWorkPath(), getUniqueFile(context,
        getOutputName(context), extension));
  }
  
  /**
   * Get the base output name for the output file.
   */
  protected static String getOutputName(JobContext job) {
    return job.getConfiguration().get(BASE_OUTPUT_NAME, PART);
  }
  
  /**
   * Set the base output name for output file to be created.
   */
  public static void setOutputName(JobContext job, String name) {
    job.getConfiguration().set(BASE_OUTPUT_NAME, name);
  }
}
