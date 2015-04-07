package pl.urman.kudos.infrastructure

import com.redis.RedisClient

object RedisCleaner {

  def clean(): Unit = {
    val redis = new RedisClient("localhost", 6379)
    redis.flushall
    redis.set("user_id", 1000)
    redis.set("kudo_id", 1000)
  }
}
