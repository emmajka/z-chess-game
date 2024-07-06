package kafka

case class KafkaMessage[K, V](key: K, value: V)
