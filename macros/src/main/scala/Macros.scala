import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

class OptionalMacros(val c: Context) {
  def getOrElse(alt: c.Tree): c.Tree = {
    import c.universe._
    val q"$prefix.$_[$_]($args)" = c.macroApplication
    val res = q"""
      val temp = $prefix
      if (temp.isEmpty) $alt else temp.value
    """
    //println(showCode(c.macroApplication))
    println(showCode(res))
    res
  }
}

final class Optional[+A >: Null](val value: A) extends AnyVal {
  def get: A = value
  def isEmpty = value == null
  def getOrElse[B >: A](alt: => B): B = macro OptionalMacros.getOrElse
  override def toString = if (isEmpty) "<empty>" else s"$value"
}

object Macros {
  def invoke(str: String, method: String): Unit = macro invokeImpl
  def invokeImpl(c: Context)(str: c.Tree, method: c.Tree): c.Tree = {
    import c.universe._
    val q"${m: String}" = method
    val tn = TermName(m)
    val res = q"println($str.$tn)"
    println(showCode(res))
    res
  }
}
