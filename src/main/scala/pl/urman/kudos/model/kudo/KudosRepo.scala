package pl.urman.kudos.model.kudo

import com.redis._

class KudosRepo {

  def store(kudo: Kudo): Unit = {
    val r = new RedisClient("localhost", 6379)
    r.set(kudo.email, kudo.message)
  }
  
  def get(): Kudo = {
    val r = new RedisClient("localhost", 6379)
    val key = "person@gmail.com"
    val message = r.get(key)
    new Kudo(key, message.get)
  }
}
