
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MedianCombiner extends
		Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	public void reduce(Text key, Iterable<DoubleWritable> values,
			Context context) throws IOException, InterruptedException {
		List<Double> arr = new ArrayList<Double>();
		double median;
		int length;
		for (DoubleWritable value : values) {
			arr.add(value.get());
		}
		Collections.sort(arr);
		length = arr.size();
		if (length % 2 == 1)
			median = arr.get((length - 1) / 2);
		else
			median = (arr.get(length / 2) + arr.get(length / 2 - 1)) / 2;
		context.write(key, new DoubleWritable(median));
		arr.clear();
	}
}
