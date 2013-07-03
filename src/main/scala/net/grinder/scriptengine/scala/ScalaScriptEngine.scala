package net.grinder.scriptengine.scala

import net.grinder.scriptengine.ScriptEngineService.{WorkerRunnable, ScriptEngine}
import net.grinder.engine.common.{EngineException, ScriptLocation}
import com.twitter.util.Eval
import java.io.IOException
import net.grinder.scriptengine.{ScriptExecutionException, ScriptEngineService}

/**
 * Created with IntelliJ IDEA.
 * User: pwierzb1
 * Date: 5/13/13
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
class ScalaScriptEngine(script:ScriptLocation) extends ScriptEngine  {



  val eval = new Eval()

  val factory = try {
                  eval.apply[(()=>()=>Unit)](script.getFile)()
                }catch {
                   case io: IOException => throw new EngineException("Unable to parse scala script at: " +
                     script.getFile().getAbsolutePath(), io)
                   case cc: ClassCastException => throw new EngineException("File doesn't contains factory method")
                }
  @throws(classOf[EngineException])
  def createWorkerRunnable(): WorkerRunnable = {
    try {
      new ScalaWorkerRunnable(factory)
    }catch {
      case e => throw new EngineException("Cannot create runner.", e )
    }
  }

  def createWorkerRunnable(runner: Any): WorkerRunnable = {
    if(runner.isInstanceOf[()=>Unit]){
      new ScalaWorkerRunnable(runner.asInstanceOf[()=>Unit])
    }else{
      throw new ScalaScriptExecutionException("given object is not ()=>Unit")
    }
  }

  def getDescription: String = "Scala Script Engine"

  def shutdown() {}

  class ScalaWorkerRunnable(runner : ()=>Unit) extends ScriptEngineService.WorkerRunnable {

    @throws(classOf[ScalaScriptExecutionException])
    def run() {
      try{
        runner()
      }
      catch {
        case e => throw new ScalaScriptExecutionException("Exception raised by worker thread", e)
      }
    }

    def shutdown() {}
  }

  class ScalaScriptExecutionException(val message:String, val throwable:Throwable=null) extends ScriptExecutionException(message, throwable)
}
