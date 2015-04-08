package pl.urman.kudos

import com.redis.RedisClient
import org.scalatra.test.scalatest._
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import pl.urman.kudos.infrastructure.RedisCleaner
import pl.urman.kudos.model.user.UserRepo

class ApiServletSpec extends ScalatraSuite with FunSuiteLike with BeforeAndAfter {

  val r = new RedisClient("localhost", 6379)
  val userRepo = new UserRepo
  addServlet(classOf[ApiServlet], "/api/*")


  before {
    RedisCleaner.clean
  }

  test("Add Kudos and craete a user") {

    post("/api/kudos/johny", """{"from": "bill", "message": "You are awesome"}""".getBytes, Map("Content-Type" -> "application/json")) {
      status should equal(200)

      get("/api/kudos/johny") {
        status should be equals (200)
        body should be( """[{"from":"bill","message":"You are awesome"}]""")
      }

    }
  }
}
