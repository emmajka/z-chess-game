package cfg

import zio.*
import zio.config.magnolia.deriveConfig

case class HttpConfig(port: Int)

object HttpConfig {
  val live: Layer[Config.Error, HttpConfig] = ZLayer.fromZIO(ZIO.config(deriveConfig[HttpConfig].nested("http", "server")))
}
