package kafka.producer

import zio.Task
import zio.kafka.serde.Serde

trait EventsProducer {
  def produce(message: String): Task[Unit]
}