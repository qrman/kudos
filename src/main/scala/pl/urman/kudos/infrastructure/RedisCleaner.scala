package pl.urman.kudos.infrastructure

import com.redis.RedisClient

class RedisCleaner(redis: RedisClient) {

  def clean(): Unit = {
    redis.flushall
    redis.set("user_id", 1000)
    redis.set("kudo_id", 1000)
  }
}
