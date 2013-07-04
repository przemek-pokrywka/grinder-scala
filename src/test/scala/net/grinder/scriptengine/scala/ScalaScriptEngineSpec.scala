package net.grinder.scriptengine.scala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import java.util
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import net.grinder.testutility.AbstractJUnit4FileTestCase
import net.grinder.engine.common.{EngineException, ScriptLocation}
import net.grinder.util.Directory
import net.grinder.testutility.FileUtilities._
import net.grinder.scriptengine.ScriptExecutionException

/**
 * Created with IntelliJ IDEA.
 * User: pwierzb1
 * Date: 7/4/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class ScalaScriptEngineSpec extends AbstractJUnit4FileTestCase with FlatSpec with ShouldMatchers  {

  "The Grinder Script Engine" should "create test from basic closure" in {
    //given
    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("test.scala"))

    createFile(script.getFile(),
      "() => {" +
        "() => { println (\"called runner\")}" +
        "}")

    //when
    val engine = new ScalaScriptEngineService().createScriptEngine(script)

    //then
    engine should not be null
  }

  it should "throw EngineException if a file contains only one function" in {
    //given
    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("withoutProperFactoryFunction.scala"))

    createFile(script.getFile(),
      "() => {" +
//        "() => { println (\"called runner\")}" +
        "}")

    //when
    val thrown = intercept[EngineException]{
      new ScalaScriptEngineService().createScriptEngine(script)
    }


    //then
    thrown.getMessage should be ("File doesn't contains factory method")
  }

  it should "throw ScriptExecutionException with cause of exception from script" in {
    //given
    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("exception.scala"))

    createFile(script.getFile(),
      "() => {" +
           "() => { throw new UnsupportedOperationException(\"exception from thread\")}" +
        "}")

    val scriptEngine = new ScalaScriptEngineService().createScriptEngine(script)

    //when
    val thrown = intercept[EngineException]{
      scriptEngine.createWorkerRunnable().run()
    }


    //then
    thrown.getMessage should be ("Exception raised by worker thread")
    thrown.getCause.getMessage should be ("exception from thread")
  }

  it should "return proper description" in {
    //given
    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("fake.scala"))

    createFile(script.getFile(),
      "() => {" +
        "() => { throw new UnsupportedOperationException(\"exception from thread\")}" +
        "}")

    val scriptEngine = new ScalaScriptEngineService().createScriptEngine(script)

    //when
    val description = scriptEngine.getDescription


    //then
    description should be ("Scala Script Engine")

  }

  it should "create test from test object ()=>Unit" in {
    //given
    val runnable = ()=>{}

    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("fake.scala"))

    createFile(script.getFile(),
      "() => {" +
        "() => { println (\"called runner\")}" +
        "}")

    //when
    val engine = new ScalaScriptEngine(script).createWorkerRunnable(runnable)

    //then
    engine should not be null
  }

  it should "throw ScalaScriptExecutionException when you would try to create test from incorrect object" in {
    //given
    val incorrect = None

    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("fake.scala"))

    createFile(script.getFile(),
      "() => {" +
        "() => { println (\"called runner\")}" +
        "}")

    //when
    val thrown = intercept[ScriptExecutionException]{
      new ScalaScriptEngine(script).createWorkerRunnable(incorrect)
    }

    //then
    thrown.getMessage should be ("given object is not ()=>Unit")
  }

  it should "throw EngineException for not existing file" in {
    //given
    val script =
      new ScriptLocation(new Directory(getDirectory()), new java.io.File("notExisting.scala"))


    //when
    val thrown = intercept[EngineException]{
      new ScalaScriptEngineService().createScriptEngine(script)
    }

    //then
    thrown.getMessage should be ("Unable to parse scala script at: %s" format script.getFile.getAbsolutePath)
  }
}
