/*
Author      : Yue Liu
Organization: Northeastern University
Date        : Feb. 3, 2015
*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class A1V2 {
	public static class MpA1Mapper extends
			Mapper<LongWritable, Text, Text, FloatWritable> {
		private Text category = new Text();
		private static FloatWritable priceWritable = new FloatWritable(0);

		public boolean isFloat(String s) {
			try {
				Float.parseFloat(s);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] line = value.toString().split("	");
			if (line.length < 5)
				return;
			String tmp = line[0];
			if (tmp.equals("Date"))
				return;
			if (!isFloat(line[4]))
				return;
			category.set(line[3]);
			priceWritable.set(Float.parseFloat(line[4]));
			context.write(category, priceWritable);

		}
	}

	public static class MpA1Reducer extends
			Reducer<Text, FloatWritable, Text, FloatWritable> {
		ArrayList<Float> tmpList = new ArrayList<Float>();

		public void reduce(Text key, Iterable<FloatWritable> values,
				Context context) throws IOException, InterruptedException {

			Float median = new Float(0);
			for (FloatWritable value : values) {
				tmpList.add(value.get());
			}
			int len = tmpList.size();
			Collections.sort(tmpList);
			if (len % 2 == 0)
				median = (tmpList.get(len / 2) + tmpList.get(len / 2 - 1)) / 2;
			else
				median = tmpList.get(len / 2);

		
			context.write(key, new FloatWritable(median));
			tmpList.clear();

		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err
					.println("Usage: Median");
			System.exit(-1);
		}
		Job job = new Job();
		job.setJarByClass(A1V2.class);
		job.setJobName("Median Version2");
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(MpA1Mapper.class);
		job.setReducerClass(MpA1Reducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		job.waitForCompletion(true);
	}
}
