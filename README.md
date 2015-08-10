phantomjs-maven-plugin
======================

[![Build Status](https://travis-ci.org/klieber/phantomjs-maven-plugin.png?branch=master)](https://travis-ci.org/klieber/phantomjs-maven-plugin) [![Coverage Status](https://coveralls.io/repos/klieber/phantomjs-maven-plugin/badge.png)](https://coveralls.io/r/klieber/phantomjs-maven-plugin?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.klieber/phantomjs-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.klieber/phantomjs-maven-plugin) [![Stories in Ready](https://badge.waffle.io/klieber/phantomjs-maven-plugin.png?label=ready)](https://waffle.io/klieber/phantomjs-maven-plugin) [![Flattr this git repo](http://api.flattr.com/button/flattr-badge-large.png)](https://flattr.com/submit/auto?user_id=kylelieber&url=https://github.com/klieber/phantomjs-maven-plugin&title=phantomjs-maven-plugin&language=java&tags=github&category=software)

A maven plugin for installing the [phantomjs](http://phantomjs.org) binary on your system automatically. You no longer need to have phantomjs pre-installed on your CI server or development workstation in order to use it as part of your build.  Just add the following to your build:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.klieber</groupId>
        <artifactId>phantomjs-maven-plugin</artifactId>
        <version>${phantomjs-maven-plugin.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>install</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <version>1.9.7</version>
        </configuration>
      </plugin>
    </plugins>
 </build>
 ...
</projects>

```

The plugin also makes the property `phantomjs.binary` available after it installs phantomjs so that you can use it to configure other maven plugins that use phantomjs or so that it can be used in your JUnit testing.

Example using with [jasmine-maven-plugin](http://searls.github.io/jasmine-maven-plugin/phantomjs.html):

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.klieber</groupId>
        <artifactId>phantomjs-maven-plugin</artifactId>
        <version>${phantomjs-maven-plugin.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>install</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <version>1.9.7</version>
        </configuration>
      </plugin>
      <plugin>
        <groupId>com.github.searls</groupId>
        <artifactId>jasmine-maven-plugin</artifactId>
        <version>${jasmine-maven-plugin-version}</version>
        <executions>
          <execution>
            <goals>
              <goal>test</goal>
            </goals>
            <configuration>
              <webDriverClassName>org.openqa.selenium.phantomjs.PhantomJSDriver</webDriverClassName>
              <webDriverCapabilities>
                <capability>
                  <name>phantomjs.binary.path</name>
                  <value>${phantomjs.binary}</value>
                </capability>
              </webDriverCapabilities>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
 ...
</projects>
```

Example using in a JUnit test:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.klieber</groupId>
        <artifactId>phantomjs-maven-plugin</artifactId>
        <version>${phantomjs-maven-plugin.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>install</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <version>1.9.7</version>
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.17</version>
        <configuration>
          <systemPropertyVariables>
            <phantomjs.binary>${phantomjs.binary}</phantomjs.binary>
          </systemPropertyVariables>
        </configuration>
      </plugin>
    </plugins>
  </build>
 ...
</projects>
```
Then your JUnit test can access it like this:
```java
package org.example;

import org.junit.Test;
import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ExampleTest {

  @Test
  public void shouldHavePhantomJsBinary() {
    String binary = System.getProperty("phantomjs.binary");
    assertNotNull(binary);
    assertTrue(new File(binary).exists());
  }

}
```

The plugin can also execute phantomjs scripts for you as well.  The following downloads phantomjs automatically if it isn't already present on the system and then executes the script `hello.js` with the argument `Bob` ([see full example here](https://github.com/klieber/phantomjs-maven-plugin/tree/master/phantomjs-maven-plugin/src/it/exec-with-args)):

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.klieber</groupId>
        <artifactId>phantomjs-maven-plugin</artifactId>
        <version>${phantomjs-maven-plugin.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>install</goal>
              <goal>exec</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <version>1.9.7</version>
          <checkSystemPath>true</checkSystemPath>
          <script>hello.js</script>
          <arguments>
            <argument>Bob</argument>
          </arguments>
        </configuration>
      </plugin>
    </plugins>
 </build>
 ...
</projects>

```

More documentation can be found on the plugin site: http://klieber.github.io/phantomjs-maven-plugin


