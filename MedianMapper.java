import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MedianMapper extends
Mapper<LongWritable, Text, Text, DoubleWritable> {
	static int counter = 1;
	private Text text = new Text();
	private final static DoubleWritable tempWritable = new DoubleWritable(0.0);

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		int rate = Integer.parseInt(context.getConfiguration().get("samplingRate"));
	
	
		if(key.get() % rate == 0 ) {
			String[] strs = value.toString().split("\t");
			if (strs.length == 6 && (isDouble(strs[4]) && (!strs[4].isEmpty()))) {
				
				int n;
				if(Median.hm.containsKey(strs[3]))
					 n = Median.hm.get(strs[3]);
				else
					 n = 0;
				
				if(n <= Median.numBins){
				String product = strs[3];
				text.set(product);
				double temp = Double.parseDouble(strs[4]);
				tempWritable.set(temp);
				context.write(text, tempWritable);
				n++;
				Median.hm.put(strs[3], n);
				}
			}
		}
	}
	/*
	 * To check if the value in price column 
	 * double value or not
	 */
	public boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}