/*
Author      : Yue Liu
Organization: Northeastern University
Date        : Feb. 3, 2015
Reference   : http://blog.csdn.net/liangliyin/article/details/7474848
*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class A1V3 extends Configured implements Tool{
	public static class MyMaper extends MapReduceBase 
		 implements	Mapper<LongWritable, Text, MyKey, FloatWritable> {
	
		public boolean isFloat(String s){
			try
			   {
			      Float.parseFloat(s);
			      return true;
			   }
			   catch(NumberFormatException e)
			   {
			      return false;
			   }
		}
		@Override
		public void map(LongWritable key, Text value,
		        OutputCollector<MyKey, FloatWritable> output, Reporter reporter)
				throws IOException {
			String[] line = value.toString().split("	");
			if(line.length < 5)
				return;
			String tmp = line[0];
			if (tmp.equals("Date"))
				return;
			if(!isFloat(line[4]))
				return;
			
			Text first = new Text(line[3]);
			FloatWritable second = new FloatWritable(Float.parseFloat(line[4]));
			MyKey myKey = new MyKey(first, second);
			output.collect(myKey, second);
		}
	}

	public static class MyReducer extends MapReduceBase
            implements Reducer<MyKey, FloatWritable, Text, FloatWritable> {
	    @Override
		public void reduce(MyKey myKey, Iterator<FloatWritable> values,
		        OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
			ArrayList<Float> al = new ArrayList<Float>();
			Float median;
			while(values.hasNext()){
	    		al.add(values.next().get());
	    	}
			

			int len = al.size();
			if (len % 2 == 0)
				median = (al.get(len / 2) + al.get(len / 2 - 1)) / 2;
			else
				median = al.get(len / 2);

			System.out.println(myKey.first+ " " + len + " " + al.get(0) +" " +al.get(len - 1)+" "+median);
			output.collect(myKey.first, new FloatWritable(median));
		}
	}

	 @Override  
	  public int run(String[] args) throws Exception {  
	    JobConf conf = new JobConf(getConf(), A1V3.class);   
	    conf.setJobName("Median Version3");  
	   
	    conf.setOutputKeyClass(MyKey.class);  
	    conf.setOutputValueClass(FloatWritable.class);  
	      
	    conf.setMapperClass(MyMaper.class);          
	    conf.setReducerClass(MyReducer.class);  
	     
	    conf.setPartitionerClass(MyPartitioner.class);  
	    conf.setOutputValueGroupingComparator(MyGroupComparator.class); 
	    conf.setOutputKeyComparatorClass(MyKeyComparator.class);
	      
	    
	  
	    FileInputFormat.setInputPaths(conf, new Path(args[0]));  
	    FileOutputFormat.setOutputPath(conf, new Path(args[1]));  
	    JobClient.runJob(conf);  
	    return 0;  
	  }  
	    
	  public static void main(String[] args) throws Exception {   
	    int res = ToolRunner.run(new Configuration(), new A1V3(), args);  
	    System.exit(res);  
	  }  
}
