准备环境
1.从官网下载kafka0.8.2
2.进入kafka根目录,启动zookeeper和kafka,命令如下
    bin/zookeeper-server-start.sh config/zookeeper.properties &
    bin/kafka-server-start.sh config/server.properties &
3.创建topic,名字是"test"
        bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

测试
1. 用maven的方式导入项目,如果没有maven,使用lib下面的jar包
2. 运行ConsumerTest测试consumer
3. 运行ProducerTest测试producer