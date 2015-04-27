package pl.urman.kudos

import org.scalatra.test.scalatest._
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import pl.urman.kudos.infrastructure.{AppModule, RedisCleaner}

class KudoServletSpec extends ScalatraSuite with FunSuiteLike with BeforeAndAfter {

  val appModule = new AppModule {}

  implicit val kudosRepo = appModule.kudosRepo
  implicit val userRepo = appModule.userRepo
  implicit val redisCleaner = appModule.redisCleaner

  addServlet(new KudoServlet(kudosRepo, userRepo), "/api/*")


  before {
    redisCleaner.clean
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
