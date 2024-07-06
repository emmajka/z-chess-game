package kafka.producer.impl

import cfg.KafkaConfig
import kafka.producer.EventsProducer
import org.apache.kafka.clients.producer.ProducerRecord
import zio.kafka.producer.{Producer, ProducerSettings}
import zio.kafka.serde.Serde
import zio.{Task, ZIO, ZLayer}

case class EventsProducerImpl(producer: Producer, topic: String) extends EventsProducer {
  def produce(
    message: String
  ): Task[Unit] =
    producer
      .produce(
        record = ProducerRecord(topic, message),
        keySerializer = Serde.long,
        valueSerializer = Serde.string
      )
      .catchAllCause(cause => ZIO.logWarningCause(s"Failed to produce an event to topic: [$topic]", cause).unit)
      .unit
}

object EventsProducerImpl {
  lazy val live = ZLayer.scoped {
    for
      cfg <- ZIO.service[KafkaConfig]
      producer <- Producer.make(ProducerSettings(bootstrapServers = List(cfg.bootstrapServer)))
    yield EventsProducerImpl(producer = producer, topic = cfg.producer.events.topic)
  }
}
