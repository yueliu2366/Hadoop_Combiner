import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class MyKey implements WritableComparable<MyKey> {
	public final Text first;
	public final FloatWritable second;

	public MyKey() {
		first = new Text();
		second = new FloatWritable();
	}

	public MyKey(Text first, FloatWritable second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		first.write(out);
		second.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		first.readFields(in);
		second.readFields(in);
	}

	@Override
	public String toString() {
		return first + "\t" + second;
	}

	@Override
	public int compareTo(MyKey tp) {
//		int cmp = first.compareTo(tp.first);
//		if (cmp != 0) {
//			return cmp;
//		}
//		return second.compareTo(tp.second);
		return first.compareTo(tp.first);
	}
}