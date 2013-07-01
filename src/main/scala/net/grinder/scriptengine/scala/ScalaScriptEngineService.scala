package net.grinder.scriptengine.scala

import net.grinder.scriptengine.{Instrumenter, ScriptEngineService}
import net.grinder.util.FileExtensionMatcher
import net.grinder.engine.common.ScriptLocation
import net.grinder.scriptengine.ScriptEngineService.ScriptEngine
import java.util
import java.util.Collections

/**
 * Created with IntelliJ IDEA.
 * User: pwierzb1
 * Date: 5/14/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
class ScalaScriptEngineService extends ScriptEngineService {
  private val scalaFileMatcher =
    new FileExtensionMatcher(".scala");

  def createScriptEngine(scriptLocation: ScriptLocation): ScriptEngine = {
    if(scalaFileMatcher.accept(scriptLocation.getFile)){
      new ScalaScriptEngine(scriptLocation)
    }else{
      null
    }
  }

  def createInstrumenters(): util.List[_ <: Instrumenter] = Collections.emptyList()
}

