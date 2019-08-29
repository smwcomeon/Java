package com.atguigu.mapreduce.wholefile;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class WholeFileInputformat extends FileInputFormat<NullWritable, BytesWritable>{

	//是否可以切割
	@Override
	protected boolean isSplitable(JobContext context, Path filename) {
		return false; //false表示文件不能切割 读取完整的文件
	}
	
	@Override
	public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		WholeRecordReader reader = new WholeRecordReader();
		
		//调用初始化方法
		reader.initialize(split, context);
		
		return reader ;
	}

}
