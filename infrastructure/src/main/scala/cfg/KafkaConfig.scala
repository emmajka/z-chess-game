package cfg

import zio.*
import zio.config.magnolia.deriveConfig

case class KafkaConfig(producer: KafkaProducers, bootstrapServer: String, client: String)
case class KafkaProducers(events: KafkaProducerConfig)
case class KafkaProducerConfig(topic: String)

object KafkaConfig {
  lazy val live: Layer[Config.Error, KafkaConfig] = ZLayer.fromZIO(ZIO.config(deriveConfig[KafkaConfig].nested("kafka")))
}
