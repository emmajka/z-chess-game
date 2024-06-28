import sbt.*
import sbt.librarymanagement.ModuleID

object Dependencies {

  private val config = Seq(
    "com.typesafe"           % "config"     % "1.4.3",
    "com.github.pureconfig" %% "pureconfig" % "0.17.6"
  )
  private lazy val Versions = new {
    val tapir     = "1.10.6"
    val htt4s     = "0.23.27"
    val jsonZio   = "0.6.2"
    val configZio = "4.0.1"
  }

  lazy val zio: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio"       % "2.0.21",
    "dev.zio" %% "zio-kafka" % "2.7.4"
  )

  lazy val zioTest: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-test"     % "2.0.9" % "test",
    "dev.zio" %% "zio-test-sbt" % "2.0.9" % "test"
  )

  lazy val serde: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-json" % Versions.jsonZio
  )

  lazy val tapir: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-zio"               % Versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio" % Versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % Versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-json-zio"          % Versions.tapir
  )

  lazy val http4s: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-dsl"          % Versions.htt4s,
    "org.http4s" %% "http4s-ember-server" % Versions.htt4s,
    "dev.zio"    %% "zio-interop-cats"    % "23.1.0.2"
  )

  lazy val configZio: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-config"          % Versions.configZio,
    "dev.zio" %% "zio-config-typesafe" % Versions.configZio,
    "dev.zio" %% "zio-config-magnolia" % Versions.configZio
  )

  lazy val mysql: Seq[ModuleID] = Seq(
//    "io.getquill" %% "quill-jdbc-zio"       % "4.8.4",
    "io.getquill" %% "quill-jdbc-zio"       % "4.6.0",
    "mysql"        % "mysql-connector-java" % "8.0.33"
  )
  val application: Seq[ModuleID]    = zio ++ tapir ++ http4s ++ serde ++ configZio
  val infrastructure: Seq[ModuleID] = zio ++ zioTest ++ configZio ++ mysql
  val domain: Seq[ModuleID]         = zio
  val client: Seq[ModuleID]         = config ++ zio

}
