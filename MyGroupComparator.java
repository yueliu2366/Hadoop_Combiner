
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroupComparator extends WritableComparator {
	protected MyGroupComparator() {
		super(MyKey.class, true);
	}

	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {
		MyKey m1 = (MyKey) w1;
		MyKey m2 = (MyKey) w2;
		return m1.first.compareTo(m2.first);
	}

}