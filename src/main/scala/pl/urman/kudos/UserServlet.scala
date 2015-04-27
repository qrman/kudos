package pl.urman.kudos

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport
import pl.urman.kudos.model.user.UserRepo

class UserServlet(userRepo: UserRepo) extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/users") {
    userRepo.all()
  }
}
