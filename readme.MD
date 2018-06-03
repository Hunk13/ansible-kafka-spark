### Ansible KAfka + Spark pipeline

Basic steps to get started:

1. Run Vagrant machine

`Vagrant up`

2. Run ansible task:

`ansible-playbook kafka.yml`

This script will install, configure and run the necessary services.

3. Create topics:

`/usr/local/lib/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181/kafka --replication-factor 1 --partitions 1 --topic Inbound`

`/usr/local/lib/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181/kafka --replication-factor 1 --partitions 1 --topic Inbound`

4. Show running topics:

`/usr/local/lib/kafka/bin/kafka-topics.sh --list --zookeeper localhost:2181/kafka`

5. Compile scala script

`cd /vagrant/sp/`

`mvn clean install`

6. Run compiled script:

`java -jar target/spark-streaming-kafka-0-10-inbound-1.0.jar`

If you get an error "Error: A JNI error has occurred, please check your installation and try again" when starting the jar file you need to run command `zip -d target/spark-streaming-kafka-0-10-inbound-1.0.jar META-INF/*.RSA META-INF/*.DSA META-INF/*.SF`

After compile and run jar file you can push data in topic 'Inbound' and pull data from topic 'Outbound'

Push data:

`cat /vagrant/sample-data.json | /usr/local/lib/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Inbound`

Get data:

`/usr/local/lib/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic Outbound --from-beginning`