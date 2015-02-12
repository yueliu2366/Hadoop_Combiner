import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Median {
	public static HashMap<String, Integer> hm = new HashMap<String, Integer>();
	public static int numBins = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		final long start, end;
		start = System.currentTimeMillis();
		// validate number of arguments
		if (args.length != 4) {
			System.err.println("Usage: <input path> <output path> <SR> <Bin>");
			System.exit(-1);
		}
		// parse the sampling rate and number of bins to verify their format
		int samplingRate = 1;
		
		try {
			samplingRate = Integer.parseInt(args[2]);
			 numBins = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			System.err
					.println("Failed to parse sampling rate and/or number of bins.");
			System.exit(-1);
		}
		Configuration conf = new Configuration();
		conf.set("samplingRate", String.valueOf(samplingRate));
		// create a new hadoop job
		Job Job = new Job(conf);
		Job.setJarByClass(Median.class);
		Job.setJobName("Median");
		// configure the input and output paths
		FileInputFormat.addInputPath(Job, new Path(args[0]));
		FileOutputFormat.setOutputPath(Job, new Path(args[1]));
		// configure the map and reduce tasks
		Job.setMapperClass(MedianMapper.class);
		Job.setReducerClass(MedianReducer.class);
		// set the combiner
		Job.setCombinerClass(MedianCombiner.class);
		// set the number of "bins"
		// Job.setNumReduceTasks(numBins);
		// configure the output settings
		Job.setOutputKeyClass(Text.class);
		Job.setOutputValueClass(DoubleWritable.class);
		// run it!
		Job.waitForCompletion(true);
		end = System.currentTimeMillis();
		System.out.println(end - start);
		
		System.out.println(hm.size());
		System.out.println(hm.get("Baby"));
	}
}