package com.atguigu.mapreduce.filter;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FilterReducer extends Reducer<Text, NullWritable, Text, NullWritable>{
	private Text k =new Text();
	@Override
	protected void reduce(Text key, Iterable<NullWritable> value,
			Context context) throws IOException, InterruptedException {
		
		//在key上添加回车和换行符
		k.set(key.toString() + "\r\n");
		context.write(k, NullWritable.get());
	
	}
}
