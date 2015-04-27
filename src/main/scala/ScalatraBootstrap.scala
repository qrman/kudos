import javax.servlet.ServletContext

import com.softwaremill.macwire.Macwire
import org.scalatra._
import pl.urman.kudos._
import pl.urman.kudos.infrastructure.{AppModule, RedisCleaner}
import pl.urman.kudos.model.kudo.{Kudo, KudosRepo}
import pl.urman.kudos.model.user.UserRepo

class ScalatraBootstrap extends LifeCycle {

  val appModule = new AppModule {}

  override def init(context: ServletContext) {

    val redisCleaner = appModule.redisCleaner
    redisCleaner.clean()

    val userRepo = appModule.userRepo
    val kudosRepo = appModule.kudosRepo
    val userId = userRepo.createOrGetUser("qrman")
    kudosRepo.store(userId, new Kudo("Han Solo", "You are Awesome"))
    kudosRepo.store(userId, new Kudo("chewbacca", "My hero"))


    context.mount(new MainPageServlet, "/*")
    context.mount(new KudoServlet(kudosRepo, userRepo), "/api")
    context.mount(new UserServlet(userRepo), "/api")
  }
}
