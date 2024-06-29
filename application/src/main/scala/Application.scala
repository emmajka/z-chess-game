import cfg.{ConfigProvider, HttpConfig}
import flow.impl.{AddPieceFlowImpl, CreateNewGameFlowImpl, GetGameDetailsFlowImpl}
import http.Http4sServer
import http.handler.impl.ChessGameHandlerImpl
import http.route.impl.{ChessGameRouteImpl, HealthRouteImpl, OpenApiRouteImpl}
import http.route.{ChessGameRoute, HealthRoute, HttpRoute}
import mysql.{MysqlConnection, MysqlCtx}
import repository.impl.ChessGameRepositoryImpl
import service.impl.{ChessGameServiceImpl, GameIdGeneratorImpl, PieceIdCreatorImpl, PieceIdGeneratorImpl}
import validator.impl.GamePieceValidatorImpl
import zio.{Fiber, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object Application extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = ConfigProvider.hocon

  override def run: ZIO[ZIOAppArgs, Any, Any] = {

    for
      _ <- ZIO.log("hello world")
      routes <- getAllRoutes
      httpServerPort <- ZIO.serviceWith[HttpConfig](_.port)
      _ <- ZIO.logInfo(s"Setting up http server on port $httpServerPort...")
      f1 <- Http4sServer.run(routes = routes, httpServerPort).fork
      _ <- ZIO.logInfo("Http server up & running")
      _ <- Fiber.collectAll(List(f1)).join
    yield ()

  }.provide(
    HttpConfig.live,
    HealthRouteImpl.live,
    ChessGameRouteImpl.live,
    ChessGameHandlerImpl.live,
    ChessGameRepositoryImpl.live,
    ChessGameServiceImpl.live,
    CreateNewGameFlowImpl.live,
    GetGameDetailsFlowImpl.live,
    AddPieceFlowImpl.live,
    GameIdGeneratorImpl.live,
    PieceIdGeneratorImpl.live,
    GamePieceValidatorImpl.live,
    PieceIdCreatorImpl.live,
    MysqlConnection.ctx,
    MysqlConnection.ds
  )

  private def getAllRoutes = {
    for
      healthRoute <- ZIO.service[HealthRoute]
      chessRoute <- ZIO.service[ChessGameRoute]
      appRoutes = Seq(healthRoute, chessRoute)
      openApiRoute = OpenApiRouteImpl(appRoutes)
    yield Seq(healthRoute, chessRoute, openApiRoute)
  }
}
