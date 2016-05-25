dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
grails {
    mongo {
        host = "192.168.0.3"
        username = "root"
        password = ""
    }
}
// environment specific settings
environments {
    development {
//        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//        }
        grails {
            mongo {
                host = "192.168.0.3"
                username = "root"
                password = ""
                databaseName = "agahisaz"
            }
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        grails {
            mongo {
                host = "192.168.64.3"
                username = "root"
                password = ""
                databaseName = "agahisaz"
            }
        }
    }
}
