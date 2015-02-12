import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public  class MyKeyComparator extends WritableComparator {
	protected MyKeyComparator() {
		super(MyKey.class, true); 
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		MyKey o1 = (MyKey) a;
		MyKey o2 = (MyKey) b;
		if (!o1.first.equals(o2.first)) {
			return o1.first.compareTo(o2.first);
		} else {
			return o1.second.compareTo(o2.second);
		}
	}
}