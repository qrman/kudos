package pl.urman.kudos.model.kudo

import com.redis._
import org.slf4j.LoggerFactory
import pl.urman.kudos.model.user.UserRepo


class KudosRepo {

  val logger = LoggerFactory.getLogger(getClass)

  val userRepo = new UserRepo
  val redis = new RedisClient("localhost", 6379)

  def store(kudo: Kudo): Unit = {
    var userId = userRepo.getUser(kudo.user)
    val kudoId = redis.incr("kudo_id").get
    val key = s"user_kudo:" + userId

    redis.lpush(key, kudoId)
    redis.hmset(s"kudos:{$kudoId}", Map("from" -> kudo.user, "message" -> kudo.message))
  }

  def get(user: String): List[Kudo] = {
    val userId = userRepo.getUser(user)
    redis.lrange(s"user_kudo:" + userId, 0, -1).get.map(
      kudoId => getOne(kudoId.get)
    )
  }

  def getOne(kudoId: String): Kudo = {
    val k = redis.hgetall(s"kudos:{$kudoId}").get
    new Kudo(k.get("from").get, k.get("message").get)
  }
}
