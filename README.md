grinder-scala
=============

A ScriptEngine to allow The Grinder to use .scala files to act as test scripts.

(This is under development, but the hope is to contribute it and have it be included in The Grinder.)

Installation
------------

git clone https://github.com/DealerDotCom/grinder-groovy.git
maven install

Usage

import net.grinder.script.Grinder.grinder
def log(msg:String) = {
  grinder.getLogger.info(msg)
  println(msg)
}
() => {
  () => {
     log("test logger")
  }
}

Example of pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>...</groupId>
    <artifactId>...</artifactId>
    <version>...</version>
    <properties>
        <maven.compiler.source>1.6</maven.compiler.source>
        <maven.compiler.target>1.6</maven.compiler.target>
        <encoding>UTF-8</encoding>
        <scala.version>2.9.2</scala.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <version>${scala.version}</version>
        </dependency>

        <dependency>
            <groupId>pshemass.github</groupId>
            <artifactId>grinder-scala</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>net.sf.grinder</groupId>
            <artifactId>grinder-core</artifactId>
            <version>3.11</version>
        </dependency>

        <dependency>
            <groupId>net.sf.grinder</groupId>
            <artifactId>grinder-swing-console</artifactId>
            <version>3.11</version>
        </dependency>

        <dependency>
            <groupId>net.sf.grinder</groupId>
            <artifactId>grinder-dcr-agent</artifactId>
            <version>3.11</version>
        </dependency>
        <!-- your dependencies -->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptors>
                        <descriptor>src/assembly/assembly.xml</descriptor>
                    </descriptors>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
                            <addClasspath>true</addClasspath>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>Console</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>exec-maven-plugin</artifactId>
                        <version>1.2.1</version>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>java</goal>
                                </goals>
                            </execution>
                        </executions>
                        <configuration>
                            <mainClass>net.grinder.Console</mainClass>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
        <profile>
            <id>Agent</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>exec-maven-plugin</artifactId>
                        <version>1.2.1</version>
                        <configuration>
                            <executable>java</executable>
                            <arguments>
                                <argument>-classpath</argument>
                                <classpath/>
                                <argument>net.grinder.Grinder</argument>
                                <argument>${project.build.outputDirectory}/grinder.properties</argument>
                            </arguments>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>


</project>