package pl.urman.kudos.infrastructure

import com.redis.RedisClient
import com.softwaremill.macwire._
import pl.urman.kudos.model.kudo.KudosRepo
import pl.urman.kudos.model.user.UserRepo

trait AppModule extends Macwire {

  lazy val redisClient = new RedisClient("localhost", 6379)
  lazy val kudosRepo = wire[KudosRepo]
  lazy val userRepo = wire[UserRepo]
  lazy val redisCleaner = wire[RedisCleaner]

}
