package pl.urman.kudos.model.user

import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits._

class UserRepo {

  val redis = new RedisClient("localhost", 6379)


  def createOrGetUser(username: String): Long = {
    val userId = getUserId(username).getOrElse(
      addUser(username)
    )
    userId
  }

  private def addUser(username: String): Long = {
    val userId = redis.incr("user_id").get

    redis.hmset(s"user:$userId", Map("username" -> username))
    redis.hset("users", username, userId)
    userId
  }

  def getUserId(username: String): Option[Long] = {
    val userId = redis.hget[Long]("users", username)
    userId
  }
}
