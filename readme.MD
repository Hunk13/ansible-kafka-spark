### Ansible KAfka + Spark pipeline

Basic steps to get started:

1. Run Vagrant machine
`Vagrant up`

2. Run ansible task:
`ansible-playbook kafka.yml`
This script will install, configure and run the necessary services.

3. Create topic:
`/usr/local/lib/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181/kafka --replication-factor 1 --partitions 1 --topic Inbound`

4. Show running topics:
`/usr/local/lib/kafka/bin/kafka-topics.sh --list --zookeeper localhost:2181/kafka`
