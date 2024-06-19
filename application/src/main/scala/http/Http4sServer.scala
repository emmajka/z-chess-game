package http

import com.comcast.ip4s.{Port, ipv4}
import http.route.HttpRoute
import org.http4s.ember.server.EmberServerBuilder
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import zio.*
import zio.interop.catz.*

object Http4sServer {
  def run(routes: Seq[HttpRoute], port: Int): Task[Unit] =
    EmberServerBuilder
      .default[Task]
      .withHost(ipv4"0.0.0.0")
      .withPort(Port.fromInt(port).get)
      .withHttpApp {
        val appRoutes = routes.flatMap(_.routes).toList
        ZHttp4sServerInterpreter().from(appRoutes).toRoutes.orNotFound
      }
      .build
      .useForever
}