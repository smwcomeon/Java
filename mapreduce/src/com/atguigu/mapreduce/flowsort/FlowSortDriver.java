package com.atguigu.mapreduce.flowsort;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowSortDriver {
	public static void main(String[] args) throws Exception {
		//1.获取job信息
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		
		//2.获取jar的路径
		job.setJarByClass(FlowSortDriver.class);
		
		//3.关联map和reducer类
		job.setMapperClass(FlowSortMapper.class);
		job.setReducerClass(FlowSortReducer.class);
		
		//4.设置map阶段输出的key和value类型
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(Text.class);
		
		//5.设置最后输出的key和value
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		//6.设置输入数据的路径和输出路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//7.提交
		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1 );
	}
}
