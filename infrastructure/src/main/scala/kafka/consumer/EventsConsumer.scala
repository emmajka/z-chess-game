package kafka.consumer

import zio.UIO

trait EventsConsumer {
  def consume: UIO[Unit]
}
