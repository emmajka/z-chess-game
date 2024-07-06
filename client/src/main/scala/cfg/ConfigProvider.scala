package cfg

import zio.Runtime
import zio.config.*
import zio.config.typesafe.*

object ConfigProvider {
  val hocon = Runtime.setConfigProvider(TypesafeConfigProvider.fromResourcePath())
}