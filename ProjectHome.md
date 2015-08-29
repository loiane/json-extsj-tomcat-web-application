This app is a skeleton app of a web application using spring, hibernate, and extjs.
The goal of the app is to provide a reference/learning example of a java base web application.

the following third party applications are used:

  * [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  * [Apache Tomcat (7)](http://tomcat.apache.org/)
  * [Sencha Extjs (4.2.x)](http://www.sencha.com/products/extjs/)
  * [Mysql 5.5](http://www.mysql.com/)

the following java third party libraries controlled via [ivy](https://ant.apache.org/ivy/) see [ivy.xml](https://code.google.com/p/json-extsj-tomcat-web-application/source/browse/trunk/ivy.xml) for complete list:

  * [java API](http://docs.oracle.com/javase/7/docs/api/)
  * [j2ee servlet API](http://docs.oracle.com/javaee/7/api/)
  * [Apache commons lang](https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/)
  * [Apache commons logging](http://commons.apache.org/proper/commons-logging/apidocs/)
  * [JSON lib](http://json-lib.sourceforge.net/apidocs/jdk15/)
  * [Spring Framework](http://projects.spring.io/spring-framework/)
  * [Spring Security](http://projects.spring.io/spring-security/)
  * [Hibernate](http://hibernate.org/)
  * [HikariCP Connection pool](https://github.com/brettwooldridge/HikariCP)

The following are build and infrastructure tools used for this project:

  * [Eclipse IDE for Java EE Developers](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr2)
  * [Ivy eclipse plugin](http://ant.apache.org/ivy/ivyde/)
  * [extjs jsdt plugin](https://marketplace.eclipse.org/content/extjs-jsdt-integration)
  * [Checkstyle eclipse plugin](http://eclipse-cs.sourceforge.net/)
  * [Apache ANT](http://ant.apache.org/)
  * [ANT contrib](http://ant-contrib.sourceforge.net/)
  * [Apache IVY](https://ant.apache.org/ivy/)
  * [dbdeploy](http://dbdeploy.com/)
  * [junit](http://junit.org/)
  * [Findbugs](http://findbugs.sourceforge.net/)
  * [Checkstyle](http://checkstyle.sourceforge.net/)
  * [PMD/CPD](http://pmd.sourceforge.net/)
  * [Cobertura (code coverage)](http://cobertura.github.io/cobertura/)
  * [jsHint (modded)](http://jshint.com/)
  * [Sench CMD build tool (4.x)](http://www.sencha.com/products/sencha-cmd/download)
  * [ruby windows installer (pre 3.0)](http://rubyinstaller.org/downloads/)

TODO:
  * get user manger tab working to display all data (roles are not loading) and persist changes
  * have both Hibernate and Spring JDBC DBO layer (jdbc to use programmatic transactions)
  * abstract extjs source code location to eliminate need for static check in.
  * add [jenkins](http://jenkins-ci.org/) setup for CI