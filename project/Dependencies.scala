import sbt.*
import sbt.librarymanagement.ModuleID

object Dependencies {

  private val config = Seq(
    "com.typesafe" % "config" % "1.4.3",
    "com.github.pureconfig" %% "pureconfig" % "0.17.6"
  )
  private lazy val Versions = new {
    val tapir = "1.10.6"
    val htt4s = "0.23.27"
    val jsonZio = "0.6.2"
  }

  lazy val zio: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio" % "2.0.21",
    "dev.zio" %% "zio-kafka" % "2.7.4"
  )

  lazy val serde: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-json" % Versions.jsonZio
  )

  lazy val tapir: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-zio" % Versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio" % Versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % Versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % Versions.tapir
  )

  lazy val http4s: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-dsl" % Versions.htt4s,
    "org.http4s" %% "http4s-ember-server" % Versions.htt4s,
    "dev.zio" %% "zio-interop-cats" % "23.1.0.2"
  )

  val application: Seq[ModuleID] = zio ++ tapir ++ http4s ++ serde
  val client: Seq[ModuleID] = config ++ zio
}
