package com.kalinka.spark.streaming.kafka010.inbound

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.kafka.clients.consumer.ConsumerRecord
import com.github.benfradet.spark.kafka.writer._
import org.apache.kafka.clients.producer.{Callback, ProducerRecord, RecordMetadata}


object SparkStreamingKafkaInbound {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
          .setAppName("SparkStreamingKafka010Inbound")
          .setMaster("local[*]")
          .set("spark.streaming.kafka.maxRatePerPartition", "100")

    val sc = new SparkContext(conf)

    val streamingContext = new StreamingContext(sc, Seconds(10))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "kafka_inbound_group",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val in_topic = Array("Inbound")
    val out_topic = "Outbound"

    val producerConfig = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.serializer" -> classOf[StringSerializer].getName,
      "value.serializer" -> classOf[StringSerializer].getName
    )

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](in_topic, kafkaParams)
    )

    stream.writeToKafka(
      producerConfig,
      foo => new ProducerRecord[String, String](out_topic, foo.toString)
    )

    streamingContext.start()
    streamingContext.awaitTermination()
    }
}
