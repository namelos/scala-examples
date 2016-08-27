package sample.hello
import akka.actor.{Actor, ActorSystem, Props}

object Greeter {
  case object Greet
  case object Done
}

class Greeter extends Actor {
  def receive = {
    case Greeter.Greet =>
      println("Hello world!")
      sender() ! Greeter.Done
  }
}

class HelloWorld extends Actor {
  override def preStart() {
    val greeter = context.actorOf(Props[Greeter], "greeter")
    greeter ! Greeter.Greet
  }

  def receive: Receive = {
    case Greeter.Done => context.stop(self)
  }
}

object Main extends App {
//  akka.Main.main(Array(classOf[HelloWorld].getName))
  val system = ActorSystem("app")

  val greeter = system.actorOf(Props[Greeter], "greeter")

  greeter ! Greeter.Greet
}
