#!/bin/bash

inotifywait -e modify /vagrant/sample-data.json --format "%w%f" -q -m| while read file; do
     cat /vagrant/sample-data.json | /usr/local/lib/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Inbound
done