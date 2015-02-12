1. Make sure hadoop is running(start-all.sh)

2. Put purchases4.txt in current folder(A1), please DO NOT rename purchases4.txt

3. Open Terminal, cd into current folder(A1)

4. run the command : chmod 777 test

5. run the command : ./test  HADOOP_HOME HADOOP_VERSION  CODE_VERSION [sample_rate] [bin_size]
   e.g.: My hadoop version is 2.6.0, the hadoop home directory is /usr/local/hadoop-2.6.0. So
     (1) Testing v1, please run ./test  /usr/local/hadoop-2.6.0  2.6.0   v1
     (2) Testing v2, please run ./test  /usr/local/hadoop-2.6.0  2.6.0   v2
     (3) Testing v3, please run ./test  /usr/local/hadoop-2.6.0  2.6.0   v3
     (4) Testing v4, please run ./test  /usr/local/hadoop-2.6.0  2.6.0   v4 1 1000

6. The results are stored on hdfs /v*output
   P.S.: * stands for code_version. (e.g.: The result of v2 can be found on hdfs /v2output)
         Beside, the result of v1, a1output, can be found on current folder(A1) directly, NOT on hdfs!