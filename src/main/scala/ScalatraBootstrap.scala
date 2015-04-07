import javax.servlet.ServletContext

import org.scalatra._
import pl.urman.kudos._
import pl.urman.kudos.infrastructure.RedisCleaner
import pl.urman.kudos.model.kudo.{Kudo, KudosRepo}
import pl.urman.kudos.model.user.UserRepo

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new KudosServlet, "/*")
    context.mount(new ApiServlet, "/api")

    RedisCleaner.clean

    val userRepo = new UserRepo
    userRepo.addUser("qrman")
    val kudosRepo = new KudosRepo
    kudosRepo.store(new Kudo("qrman", "You are Awesome"))
    kudosRepo.store(new Kudo("qrman", "My hero"))
  }
}
