package cfg

import zio.*
import zio.config.magnolia.deriveConfig

case class KafkaConfig(producer: KafkaProducers, consumer: KafkaConsumers, bootstrapServer: String, client: String)
case class KafkaProducers(events: KafkaProducerConfig)
case class KafkaProducerConfig(topic: String)
case class KafkaConsumers(events: KafkaConsumerConfig)
case class KafkaConsumerConfig(topic: String, groupId: String)

object KafkaConfig {
  lazy val live: Layer[Config.Error, KafkaConfig] = ZLayer.fromZIO(ZIO.config(deriveConfig[KafkaConfig].nested("kafka")))
}
