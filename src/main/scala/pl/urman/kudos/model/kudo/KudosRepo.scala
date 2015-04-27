package pl.urman.kudos.model.kudo

import com.redis._
import org.slf4j.LoggerFactory

class KudosRepo(redisClient: RedisClient) {

  val logger = LoggerFactory.getLogger(getClass)


  def store(kudoRecipientId: Long, kudo: Kudo): Unit = {
    val kudoId = redisClient.incr("kudo_id").get
    val key = s"user_kudo:" + kudoRecipientId

    redisClient.lpush(key, kudoId)
    redisClient.hmset(s"kudos:{$kudoId}", Map("from" -> kudo.from, "message" -> kudo.message))
  }

  def get(userId: Long): List[Kudo] = {
    redisClient.lrange(s"user_kudo:" + userId, 0, -1).get.map(
      kudoId => getOne(kudoId.get)
    )
  }

  def getOne(kudoId: String): Kudo = {
    val k = redisClient.hgetall(s"kudos:{$kudoId}").get
    new Kudo(k.get("from").get, k.get("message").get)
  }
}
