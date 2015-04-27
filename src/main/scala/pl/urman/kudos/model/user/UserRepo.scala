package pl.urman.kudos.model.user

import java.util

import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits._

class UserRepo(redis: RedisClient) {

  def all(): List[User] = {
    List(new User("Bill", 1000), new User("John", 1001))
  }


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
