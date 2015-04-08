package pl.urman.kudos.model.kudo

import com.redis._
import org.slf4j.LoggerFactory
import pl.urman.kudos.model.user.UserRepo


class KudosRepo {

  val logger = LoggerFactory.getLogger(getClass)

  val redis = new RedisClient("localhost", 6379)

  def store(kudoRecipientId: Long, kudo: Kudo): Unit = {
    val kudoId = redis.incr("kudo_id").get
    val key = s"user_kudo:" + kudoRecipientId

    redis.lpush(key, kudoId)
    redis.hmset(s"kudos:{$kudoId}", Map("from" -> kudo.from, "message" -> kudo.message))
  }

  def get(userId: Long): List[Kudo] = {
    redis.lrange(s"user_kudo:" + userId, 0, -1).get.map(
      kudoId => getOne(kudoId.get)
    )
  }

  def getOne(kudoId: String): Kudo = {
    val k = redis.hgetall(s"kudos:{$kudoId}").get
    new Kudo(k.get("from").get, k.get("message").get)
  }
}
