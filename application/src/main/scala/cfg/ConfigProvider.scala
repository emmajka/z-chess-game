package cfg

import zio.config.*
import zio.config.typesafe.*
import zio.{Runtime, ZIO, ZLayer}

object ConfigProvider {
  val hocon = Runtime.setConfigProvider(TypesafeConfigProvider.fromResourcePath())
}
