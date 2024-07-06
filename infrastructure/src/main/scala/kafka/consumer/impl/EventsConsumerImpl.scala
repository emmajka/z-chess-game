package kafka.consumer.impl

import cfg.KafkaConfig
import kafka.consumer.EventsConsumer
import zio.*
import zio.kafka.consumer.*
import zio.kafka.consumer.Consumer.{AutoOffsetStrategy, OffsetRetrieval}
import zio.kafka.serde.Serde

case class EventsConsumerImpl(consumer: Consumer, topic: String) extends EventsConsumer {
  override def consume: UIO[Unit] = {
    consumer
      .plainStream(
        Subscription.topics(topic),
        Serde.long,
        Serde.string
      )
      .tap { record => ZIO.log(s"Received ${record.key}: ${record.value}") }
      .mapZIO(_.offset.commit)
      .runDrain
      .orDie
  }
}

object EventsConsumerImpl {
  lazy val live = ZLayer.scoped {
    for
      kafkaCfg <- ZIO.service[KafkaConfig]
      consumerSettings = ConsumerSettings(List(kafkaCfg.bootstrapServer))
        .withGroupId(kafkaCfg.consumer.events.groupId)
        .withOffsetRetrieval(OffsetRetrieval.Auto(AutoOffsetStrategy.Earliest))
      consumer <- Consumer.make(consumerSettings)
    yield EventsConsumerImpl(consumer = consumer, topic = kafkaCfg.consumer.events.topic)
  }
}
