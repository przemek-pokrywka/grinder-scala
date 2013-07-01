package net.grinder.scriptengine.scala

import org.junit._
import Assert._
import com.twitter.io.TempFile
import com.twitter.util.Eval

/**
 * Created with IntelliJ IDEA.
 * User: pwierzb1
 * Date: 5/13/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
class EvalTest {

  @Test
  def shouldLoadFileWithFactoryMethod() = {
    //given
    val file = TempFile.fromResourcePath("/SimpleTest.scala")
    val eval = new Eval()
    //when
    val factory = eval.apply[(()=>()=>Unit)](file)
    //then
    assertNotNull("should return factory function", factory)
  }

  @Test(expected = classOf[ClassCastException])
  def shouldNotLoadFileWithMissingFactoryMethod() = {
    //given
    val file = TempFile.fromResourcePath("/MissingFactoryMethodTest.scala")
    val eval = new Eval()
    //when
    val factory = eval.apply[(()=>()=>Unit)](file)
    //then
    fail("should throw exception")
  }

}
