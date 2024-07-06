package cfg

import zio.*
import zio.config.magnolia.deriveConfig

case class KafkaConfiguration(
  address: String,
  topic: String,
  group: String,
  client: String)

object KafkaConfiguration {
  lazy val live: Layer[Config.Error, KafkaConfiguration] = ZLayer.fromZIO(ZIO.config(deriveConfig[KafkaConfiguration].nested("client", "kafka")))
}
