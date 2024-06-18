import http.route.impl.HealthRouteImpl
import http.{HealthRoute, Http4sServer}
import zio.{Fiber, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault}

object Application extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {

    (for _ <- ZIO.log("hello world")
         routes <- routes
         _ <- ZIO.logInfo("Setting up http server...")
         f1 <- Http4sServer.run(routes = routes, 8081).fork
         _ <- ZIO.logInfo("Http server up & running")
         _ <- Fiber.collectAll(List(f1)).join
    yield ()
      ).provide()
  }

  val routes: Task[Seq[HealthRoute]] = {
    for healthRoute <- ZIO.service[HealthRoute] yield Seq(healthRoute)
  }.provide(HealthRouteImpl.layer)
}
