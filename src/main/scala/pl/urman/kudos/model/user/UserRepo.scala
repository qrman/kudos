package pl.urman.kudos.model.user

import com.redis.RedisClient

class UserRepo {

  val redis = new RedisClient("localhost", 6379)
  def addUser(username: String): Unit = {
    val userId = redis.incr("user_id").get

    redis.hmset(s"user:$userId", Map("username" -> username))
    redis.hset("users", username, userId)
  }

  def getUser(username: String): String = {
    val userId = redis.hget("users", username)
    userId.getOrElse(throw new RuntimeException(s"Unable to find user: $username"))
  }
}
