# Author      : Yue Liu
# Organization: Northeastern University
# Date        : Feb. 3, 2015
#./test  HADOOP_HOME HADOOP_VERSION  CODE_VERSION
#./test.sh  /usr/local/hadoop-2.6.0  2.6.0   v4 1 1000
set -e

home_path=$1
hadoop_version=$2
code_version=$3
sample_rate=$4
bin_size=$5

#/usr/local/hadoop-2.6.0/share/hadoop/common/hadoop-common-2.6.0.jar:/usr/local/hadoop-2.6.0/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.6.0.jar:/usr/local/hadoop-2.6.0/share/hadoop/common/lib/commons-cli-1.2.jar A2.java -d A2_Class

if [[ $code_version = "v2" ]]; then

 	hdfs dfs -mkdir /v2input

	hdfs dfs -put purchases4.txt /v2input

	mkdir A1V2_Class
	classpath=${home_path}"/share/hadoop/common/hadoop-common-"${hadoop_version}".jar:"${home_path}"/share/hadoop/mapreduce/hadoop-mapreduce-client-core-"${hadoop_version}".jar:"${home_path}"/share/hadoop/common/lib/commons-cli-1.2.jar A1V2.java -d A1V2_Class" 
	
	javac -classpath $classpath
 	
 	jar cvf A1V2.jar -C A1V2_Class/ .

 	hadoop jar A1V2.jar A1V2 /v2input /v2output

 	rm -r A1V2_Class
 	rm A1V2.jar
elif [[ $code_version = "v3" ]]; then
	hdfs dfs -mkdir /v3input

	hdfs dfs -put purchases4.txt /v3input

	mkdir A1V3_Class
	classpath=${home_path}"/share/hadoop/common/hadoop-common-"${hadoop_version}".jar:"${home_path}"/share/hadoop/mapreduce/hadoop-mapreduce-client-core-"${hadoop_version}".jar:"${home_path}"/share/hadoop/common/lib/commons-cli-1.2.jar MyKey.java MyPartitioner.java MyGroupComparator.java MyKeyComparator.java A1V3.java -d A1V3_Class" 
	javac -classpath $classpath
 	
 	jar cvf A1V3.jar -C A1V3_Class/ .

 	hadoop jar A1V3.jar A1V3 /v3input /v3output

  	rm -r A1V3_Class
 	rm A1V3.jar
elif [[ $code_version = "v4" ]]; then

hdfs dfs -mkdir /v4input

hdfs dfs -put purchases4.txt /v4input

mkdir a2_group_class

classpath=${home_path}"/share/hadoop/common/hadoop-common-"${hadoop_version}".jar:"${home_path}"/share/hadoop/mapreduce/hadoop-mapreduce-client-core-"${hadoop_version}".jar:"${home_path}"/share/hadoop/common/lib/commons-cli-1.2.jar MedianCombiner.java MedianMapper.java MedianReducer.java Median.java -d a2_group_class" 

javac -classpath $classpath

jar cvf a2group.jar -C a2_group_class/ .

hadoop jar a2group.jar Median /v4input /v4output $sample_rate $bin_size

rm -r a2_group_class
rm a2group.jar
 elif [[ $code_version = "v1" ]]; then
 	javac A1V1.java
 	java A1V1
 	rm A1V1.class
fi

