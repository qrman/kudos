package pl.urman.kudos

import com.redis.RedisClient
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite
import org.slf4j.LoggerFactory
import pl.urman.kudos.infrastructure.RedisCleaner
import pl.urman.kudos.model.kudo.{Kudo, KudosRepo}
import pl.urman.kudos.model.user.UserRepo

class KudosFlowTest(userRepo:UserRepo, kudosRepo:KudosRepo, redisCleaner: RedisCleaner) extends ScalatraSuite with FunSuiteLike with BeforeAndAfter {

  val logger = LoggerFactory.getLogger(getClass)

  before {
    redisCleaner.clean
  }

  test("should register Users") {
    userRepo.createOrGetUser("johny") should equal(1001)
    userRepo.createOrGetUser("frank") should equal(1002)
    userRepo.createOrGetUser("johny") should equal(1001)
  }

  test("User should have no Kudos at the beginning") {
    val userId = userRepo.createOrGetUser("johny")
    val kudos = kudosRepo.get(userId)
    kudos should be(empty)
  }

  test("After adding Kudo user have it") {
    //given
    val userId = userRepo.createOrGetUser("johny")
    val kudo = new Kudo("bill", "My boy!")
    kudosRepo.store(userId, kudo)

    //when
    val kudos = kudosRepo.get(userId)

    //then
    kudos should contain(kudo)
  }
}
