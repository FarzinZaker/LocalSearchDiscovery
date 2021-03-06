// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all          : '*/*',
        atom         : 'application/atom+xml',
        css          : 'text/css',
        csv          : 'text/csv',
        form         : 'application/x-www-form-urlencoded',
        html         : ['text/html', 'application/xhtml+xml'],
        js           : 'text/javascript',
        json         : ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss          : 'application/rss+xml',
        text         : 'text/plain',
        xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false



elasticSearch {
    datastoreImpl = "mongoDatastore"
    bulkIndexOnStartup = false
    includeTransients = true
    elasticSearch.client.mode = 'transport'
}

environments {
    development {

        sitemap.path = 'D:\\sitemaps\\'
        sitemap.rootUrl = 'localhost:8585'

        grails.app.context = "/"
        grails.logging.jul.usebridge = true
        grails.resources.processing.enabled = true
//        grails.resources.mappers.hashandcache.excludes = ['**/*.*']

        grails.serverURL = "http://192.168.0.155:8585"

        elasticSearch.client.hosts = [
                [host: '82.99.217.213', port: 9300]
        ]
    }
    production {

        sitemap.path = '/home/agahisaz/sitemaps/'
        sitemap.rootUrl = 'www.agahisaz.com'

        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
        grails.resources.processing.enabled = true
        grails.plugin.console.enabled = true
//        grails.resources.mappers.hashandcache.excludes = ['**/*.*']

        grails.serverURL = "http://www.agahisaz.com"


        elasticSearch.client.hosts = [
                [host: '192.168.64.3', port: 9300]
        ]
    }
}
//environments {
//    development {
//        grails.logging.jul.usebridge = true
//    }
//    production {
//        grails.logging.jul.usebridge = false
//        // TODO: grails.serverURL = "http://www.changeme.com"
//    }
//}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.pars.agahisaz.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.pars.agahisaz.UserRole'
grails.plugins.springsecurity.authority.className = 'com.pars.agahisaz.Role'

grails.mail.default.from = "Agahisaz <no-reply@agahisaz.com>"
grails {
    mail {
        ssl = "off"
        host = "mx.agah-it.com"
        from = "no-reply@agahisaz.com"
        port = 25
        ssl = "off"
        username = "no-reply@agahisaz.com"
        password = 'agah!#($'
        props = [
                "mail.debug"             : "false",
                "mail.transport.protocol": "smtp",
                "mail.smtp.host"         : "mx.agah-it.com",
                "mail.smtp.port"         : "25",
                "mail.smtp.auth"         : "true"
        ]

    }
}

telegram {
    bot {
        token = "255362501:AAETVOl6R7H7Xql8n5hlp6rwh_unbYfJ4kk"
    }
}

