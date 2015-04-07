package pl.urman.kudos

import com.redis.RedisClient
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite
import org.slf4j.LoggerFactory
import pl.urman.kudos.infrastructure.RedisCleaner
import pl.urman.kudos.model.kudo.{Kudo, KudosRepo}
import pl.urman.kudos.model.user.UserRepo

class KudosFlowTest extends ScalatraSuite with FunSuiteLike with BeforeAndAfter {

  val userRepo = new UserRepo
  val kudosRepo = new KudosRepo

  val logger = LoggerFactory.getLogger(getClass)

  before {
    RedisCleaner.clean
  }

  test("should register Users") {
    userRepo.addUser("johny")
    userRepo.getUser("johny") should equal("1001")

    userRepo.addUser("frank")
    userRepo.getUser("frank") should equal("1002")

  }

  test("User should have no Kudos at the beginning") {
    userRepo.addUser("johny")
    val kudos = kudosRepo.get("johny")
    kudos should be(empty)
  }

  test("After adding Kudo user have it") {
    //given
    userRepo.addUser("johny")
    val kudo = new Kudo("johny", "My boy!")
    kudosRepo.store(kudo)

    //when
    val kudos = kudosRepo.get("johny")

    //then
    kudos should contain(kudo)
  }
}
