#SpringBoot整合kafka

#kafka介绍
Kafka是目前主流的流处理平台，同时作为消息队列家族的一员，其高吞吐性作为很多场景下的主流选择。同时作为流处理平台，在大数据开发中，作为黏合剂串联各个系统。

Kafka流处理平台特性：
1.	它是可以发布或订阅数据的流的系统，类似于消息队列
2.	数据流存储的平台，并且具备错误容忍
3.	当数据产生时就对数据进行处理
两类应用：
1.	构建实时数据流管道
2.	构建实时数据处理应用，转换或响应数据流
kafka是一个面向于数据流的生产、转换、存储、消费整体的流处理平台。
kafka是一个消息队列，但不仅仅是一个消息队列。

###Kafka基本概念
•	Producer:消息和数据的生产者，向Kafka的一个topic发布消息的进程/代码/服务

•	Consumer:消息和数据的消费者，订阅数据（Topic）并且处理其发布的消息的进程/代码/服务

•	Consumer Group:逻辑概念，对于同一个topic，会广播给不同的group，一个group中，只有一个consumer可以消费该消息

•	Broker:物理概念，Kafka集群中的每个Kafka节点

•	Topic:逻辑概念，Kafka消息的类别，对数据进行区分、隔离

•	Partition：物理概念，Kafka下数据存储的基本单元。一个Topic数据，会被分散存储到多个Partition，每一个Partition是有序的

•	Replication（副本、备份）:同一个Partition可能会有多个Replica，多个Replica之间数据是一样的

•	Replication Leader:一个Partitionn的多个Replica上，需要一个Leader负责该Partition上与Producer和Consumer交互

•	ReplicaManager:负责管理当前broker所有分区和副本的信息，处理KafkaController发起的一些请求，副本状态的切换、添加/读取消息、Leader的选举等

###kafka组成：
broker 节点；

topic 数据分类；

partition 分区；

replication 副本；

####Partition
    •	每一个Topic被切分为多个Partitions(Partition属于消费者存储的基本单位)
    •	消费者数目小于或等于Partition的数目（多个消费者若消费同个Partition会出现数据错误，所有Kafka如此设计）
    •	Broker Group中的每一个Broker保存Topic的一个或多个Partitions(一个Broker只会保存一个Partition,若Partition太大则多个Broker保存同个Partition)
    •	Consumer Group中的仅有一个Consumer读取Topic的一个或多个Partitions，并且是唯一的Consumer(避免同一个Partition被多个Consumer消费)


####Replication
    •	当集群中有Broker挂掉的情况，系统可以主动地使Replicas提供服务
    •	系统默认设置每一个Topic的replication系数为1（即默认没有副本，节省资源），可以在创建Topic时单独设置
    特点：
    1.	Replication的基本单位是Topic的Partition;
    2.	所有的读和写都从Leader进，Followers只是做为备份（只有Leader管理读写，其他的Replication只做备份）
    3.	Follower必须能够及时复制Leader的数据
    4.	增加容错性与可拓展性

####kafka消息结构：
    Offset: 消息的偏移量；
    Length: 消息的长度；
    CRC32 : 消息校验字段，校验信息的完整性；
    Magic:  用于判断该消息是不是kafka消息；
    attributes: 可选字段，存放当前消息的属性；
    Timestamp:  消费时间戳；
    Key Length: key的长度；
    Key : key的值；
    Value Length: 值的长度；
    Value: 消息内容；

####kafka应用场景
    1.	消息队列
    2.	行为跟踪
    3.	元信息监控
    4.	日志收集
    5.	流处理
    6.	事件源
    7.	持久性日志（commit log）

