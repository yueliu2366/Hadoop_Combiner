
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobConf;  
import org.apache.hadoop.mapred.Partitioner;  
  
  
public class MyPartitioner   
  implements Partitioner<MyKey, Writable> {  
  
  @Override  
  public void configure(JobConf job) {}  
  
  @Override  
  public int getPartition(MyKey key, Writable value, int numPartitions) {  
    return (key.first.hashCode() & Integer.MAX_VALUE) % numPartitions;  
  }  
}  