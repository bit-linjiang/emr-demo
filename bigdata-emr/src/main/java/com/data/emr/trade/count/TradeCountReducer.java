package com.data.emr.trade.count;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;


/**
 * 日志的reducer
 *
 * Created by candy on 2016/11/13.
 */
public class TradeCountReducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable count = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Integer sum = 0;
        for (IntWritable i : values) {
            sum += i.get();
        }
        count.set(sum);
        context.write(key, count);
    }
}
