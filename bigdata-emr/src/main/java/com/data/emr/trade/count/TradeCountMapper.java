package com.data.emr.trade.count;

import com.data.emr.trade.common.TradeLogParser;
import com.data.emr.trade.model.TradeLog;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringJoiner;


/**
 * 对指定日志文件的所有记录进行过滤
 * <p>
 * Created by candy on 2016/11/13.
 */
public class TradeCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        TradeLog tradeLog = TradeLogParser.parse(value.toString());
        Text text = new Text();
        StringJoiner sb = new StringJoiner("\t");
        //日期
        String date = tradeLog.getTime().substring(0, 10);
        sb.add(date).add(tradeLog.getFrom()).add(tradeLog.getPaid());
        text.set(sb.toString());
        context.write(text, one);
    }

}
