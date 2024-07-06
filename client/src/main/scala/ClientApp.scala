import cfg.{ConfigProvider, KafkaConfiguration}
import kafka.consumer.EventsKafkaConsumer
import zio.{Fiber, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

/*
  This is only test code, made to make testing easier. Please do not use it in production or use it as an example.
 */
object ClientApp extends ZIOAppDefault {
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = ConfigProvider.hocon

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for
      eventsConsumer <- ZIO.service[EventsKafkaConsumer]
      f1 <- eventsConsumer.consume().fork
      _ <- Fiber.collectAll(List(f1)).join
    yield ()

  }.provide(KafkaConfiguration.live, EventsKafkaConsumer.live)

//  private val consumerLayer = ZLayer.scoped {
//    for
//      kafkaCfg <- ZIO.service[KafkaConfiguration]
//      consumerSettings = ConsumerSettings(List(kafkaCfg.address))
//        .withGroupId(kafkaCfg.group)
//        .withClientId(kafkaCfg.client)
//        .withOffsetRetrieval(OffsetRetrieval.Auto(AutoOffsetStrategy.Earliest))
//      consumer <- Consumer.make(consumerSettings)
//    yield consumer
//  }

}
