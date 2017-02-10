package com.data.emr.trade.count;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URI;

/**
 * 日志转换调度
 * <p>
 * Created by candy on 2016/11/13.
 */
public class TradeCountManager extends Configured implements Tool {

    //一定要格式为s3://存储桶bucket名称/文件夹名称/
    private static final String INPUT_DIR = "s3://ytx-trade-logs/input";
    private static final String OUTPUT_DIR = "s3://ytx-trade-logs/count-output";

    public static void main(String[] args) {
        System.out.println("start task main...");
        try {
            ToolRunner.run(new TradeCountManager(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tradeTask(String input, String output) {
        System.out.println("start task...");

        try {
            Configuration conf = new Configuration();

            // 判断output文件夹是否存在，如果存在则删除
            FileSystem fileSystem = FileSystem.get(URI.create(output), conf);// 根据path找到这个文件
            Path outPath = new Path(output);
            if (fileSystem.exists(outPath)) {
                fileSystem.delete(outPath, true);// true的意思是，就算output有东西，也一带删除
            }

            Job job = Job.getInstance(conf, "trade-user-count");
            job.setJarByClass(TradeCountManager.class);
            job.setMapperClass(TradeCountMapper.class);
            job.setReducerClass(TradeCountReducer.class);
            job.setMapOutputKeyClass(Text.class);
            //此处要和Map的输出类型对应
            job.setMapOutputValueClass(IntWritable.class);
            job.setOutputKeyClass(Text.class);
            //此处要和reducer的输出类型对应
            job.setOutputValueClass(IntWritable.class);
            //设置输入路径和format类
            job.setInputFormatClass(TextInputFormat.class);
            //设置输出目录和输出的format类
            job.setOutputFormatClass(TextOutputFormat.class);

            //设置输入路径和format类
            FileInputFormat.setInputPaths(job, new Path(input));
            //设置输出目录和输出的format类
            FileOutputFormat.setOutputPath(job, new Path(output));

            //提交作业
            job.waitForCompletion(true);

        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("end task...");
    }


    @Override
    public int run(String[] strings) throws Exception {
        tradeTask(INPUT_DIR, OUTPUT_DIR);
        return 0;
    }
}
