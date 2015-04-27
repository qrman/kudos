package pl.urman.kudos

import com.sun.xml.internal.ws.wsdl.parser.RuntimeWSDLParser
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite
import pl.urman.kudos.infrastructure.AppModule

class UserServletSpec extends ScalatraSuite with FunSuiteLike with BeforeAndAfter {

  val appModule = new AppModule {}

  implicit val kudosRepo = appModule.kudosRepo
  implicit val userRepo = appModule.userRepo
  implicit val redisCleaner = appModule.redisCleaner

  addServlet(new UserServlet(userRepo), "/api/*")

  before {
    redisCleaner.clean
    userRepo.createOrGetUser("Bill")
    userRepo.createOrGetUser("John")
  }

  test("Will display users list") {
    get("/api/users") {
      status should equal(200)

      body should include ("Bill")
    }
  }
}
