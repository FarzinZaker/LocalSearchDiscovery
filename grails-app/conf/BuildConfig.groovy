grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "verbose" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()
        mavenRepo "https://repo.grails.org/grails/plugins/"
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // runtime 'mysql:mysql-connector-java:5.1.22'
        compile 'org.apache.axis:axis:1.4'
        compile 'javax.xml:jaxrpc-api:1.1'
        compile 'commons-discovery:commons-discovery:0.4'
        compile 'org.ccil.cowan.tagsoup:tagsoup:1.2'
        build 'joda-time:joda-time:2.3'
        build 'joda-time:joda-time-hibernate:1.3'
        compile 'commons-httpclient:commons-httpclient:3.1'
        compile 'org.jsoup:jsoup:1.8.1'
        compile 'wsdl4j:wsdl4j:1.6.2'

//        runtime('org.hibernate:hibernate-core:3.6.7.Final') {
//            exclude group:'commons-logging', name:'commons-logging'
//            exclude group:'commons-collections', name:'commons-collections'
//            exclude group:'org.slf4j', name:'slf4j-api'
//            exclude group:'xml-apis', name:'xml-apis'
//            exclude group:'dom4j', name:'dom4j'
//            exclude group:'antlr', name:'antlr'
//        }
        runtime 'net.sourceforge.jtds:jtds:1.2.6'


    }

    plugins {
        compile ':mongodb:1.3.3'
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.11.1"
        runtime ":resources:1.2"
        compile ":lesscss-resources:1.3.3"
//        runtime ":zipped-resources:1.0"
//        runtime ":cached-resources:1.0"
//        runtime ":cache-headers:1.0.4"


//        compile ":asset-pipeline:1.8.11"
//        compile ":less-asset-pipeline:1.7.0"

        // Uncomment these (or add new ones) to enable additional resources capabilities
//        runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"

        build ":tomcat:$grailsVersion"

//        runtime ":database-migration:1.3.2"
        compile ":spring-security-core:1.2.7.3"
        compile ':cache:1.0.1'

        compile ":mail:1.0.6"

        compile ":pretty-time:2.1.3.Final-1.0.1"
        compile "org.grails.plugins:browser-detection:0.4.3"

        compile ":quartz:0.4.1"
//	compile ":console:1.2"
        compile "org.grails.plugins:canonical:0.1"
    }
}
