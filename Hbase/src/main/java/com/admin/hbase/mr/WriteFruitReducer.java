package com.admin.hbase.mr;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

public class WriteFruitReducer extends TableReducer<ImmutableBytesWritable, Put, NullWritable>{
	@Override
	protected void reduce(ImmutableBytesWritable key, Iterable<Put> value,Context context)
			throws IOException, InterruptedException {
		
		//不对数据进行任何处理直接写出去
		for (Put put : value) {
			context.write(NullWritable.get(), put);
		}
	}
}
