package com.atguigu.mapreduce.order;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class OrderReducer extends Reducer<OrderBean, NullWritable,OrderBean, NullWritable>{
	@Override
	protected void reduce(OrderBean bean, Iterable<NullWritable> values,
			Context context)
			throws IOException, InterruptedException {
		//values.iterator().next()= NullWritable.get() 表示第一个数据
		//写出
		context.write(bean, NullWritable.get());
		
	}
}
