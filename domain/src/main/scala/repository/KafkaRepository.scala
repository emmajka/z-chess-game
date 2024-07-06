package repository

import zio.Task

trait KafkaRepository {
  def produce(topic: String, key: Long, value: String): Task[Unit]
}
