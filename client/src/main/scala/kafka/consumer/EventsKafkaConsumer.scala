package kafka.consumer

import cfg.KafkaConfiguration
import zio.kafka.consumer.Consumer.{AutoOffsetStrategy, OffsetRetrieval}
import zio.kafka.consumer.{Consumer, ConsumerSettings, Subscription}
import zio.kafka.serde.Serde
import zio.{URIO, ZIO, ZLayer}

case class EventsKafkaConsumer(consumer: Consumer, topic: String) {
  def consume(): URIO[Any, Unit] = {
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

object EventsKafkaConsumer {
//  lazy val live = ZLayer.derive[EventsKafkaConsumer]
  lazy val live = ZLayer.scoped {
    for
      kafkaCfg <- ZIO.service[KafkaConfiguration]
      consumerSettings = ConsumerSettings(List(kafkaCfg.address))
        .withGroupId(kafkaCfg.group)
        .withClientId(kafkaCfg.client)
        .withOffsetRetrieval(OffsetRetrieval.Auto(AutoOffsetStrategy.Earliest))
      consumer <- Consumer.make(consumerSettings)
    yield EventsKafkaConsumer(consumer = consumer, topic = kafkaCfg.topic)
  }
}
