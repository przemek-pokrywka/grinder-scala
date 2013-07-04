#grinder-scala


A ScriptEngine to allow The Grinder to use .scala files to act as test scripts.

(This is under development, but the hope is to contribute it and have it be included in The Grinder.)

##Installation

```
git clone https://github.com/pshemass/grinder-scala.git
mvn install
```

##Usage

The easiest way is to create maven project with archetype from: https://github.com/pshemass/grinder-scala-archetype

If you have existing project add maven dependency only.
```
<dependency>
  <groupId>grinder-scala</groupId>
  <artifactId>grinder-scala</artifactId>
  <version>1.0-SNAPSHOT_2.9.2</version>
</dependency>
```

##Script Example

```scala
import net.grinder.script.{Grinder, Test}

() => {
    def log(msg:String) = {
      println(msg)
    }
    
    //process level
    log("start process %s" format Grinder.grinder.getProcessNumber)
    
    //create The Grinder's test
    val test = new Test(1, "test log")
    
    
    
  () => {
    //thread level
    log("start thread %s" format Grinder.grinder.getThreadNumber)

    val $log = log _
    
    test.record($log)
    
    $log("test logger")
  }
}
```

