package pl.urman.kudos

import org.scalatra._
import pl.urman.kudos.model.kudo._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import pl.urman.kudos.model.user.UserRepo

class ApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  val kudosRepo = new KudosRepo()
  val userRepo = new UserRepo()

  before() {
    contentType = formats("json")
  }

  get("/kudos/:user") {
    val userId = userRepo.getUserId(params("user")).getOrElse(
      throw new RuntimeException("No such user in Application")
    )
    val kudosList = kudosRepo.get(userId)
    kudosList
  }

  post("/kudos/:user") {
    val kudo = parsedBody.extract[Kudo];
    val userId = userRepo.createOrGetUser(params("user"))
    kudosRepo.store(userId, kudo);
  }
}
