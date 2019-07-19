package thread

import java.util.concurrent.Semaphore
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object SemaphoreExample1 extends App {
  val semaphoreSecond = new Semaphore(0)
  val semaphoreThird = new Semaphore(0)

  def first() = {
    firstThread.run()
    semaphoreSecond.release()
  }

  def second() = {
    semaphoreSecond.acquire()
    secondThread.run()
    semaphoreThird.release()
  }

  def third() = {
    semaphoreThird.acquire()
    thirdThread.run()
  }

  val firstThread = RunnableTemplate("start 1")
  val secondThread = RunnableTemplate("start 2")
  val thirdThread = RunnableTemplate("start 3")

  Future { third() }
  Future { second() }
  Future { first() }

  Thread.sleep(5000)

}

case class RunnableTemplate(str: String) extends Runnable {
  override def run(): Unit = println(str)
}
