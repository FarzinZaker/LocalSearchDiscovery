package agahisaz

import utils.StringHelper

class FarsiNormalizationFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
//                println('farsi noralization filter')
                params.each { param ->
                    if (params."${param.key}" instanceof String[]) {
                        def list = params."${param.key}".collect {
//                            println("normalizing ${it} with code ${it.toCharArray().collect { it as Integer }.join(',')}")
                            def result = StringHelper.normalize(it.value as String)
//                            println("normalized ${result} with code ${result.toCharArray().collect { it as Integer }.join(',')}")
                            result
                        }
                        def array = new String[list.size()]
                        params."${param.key}" = list.toArray(array)
                    } else if (params."${param.key}" instanceof String) {
//                        println("normalizing ${params."${param.key}"} with code ${params."${param.key}".toCharArray().collect { it as Integer }.join(',')}")
                        params."${param.key}" = StringHelper.normalize(param.value as String)
//                        println("normalized ${params."${param.key}"} with code ${params."${param.key}".toCharArray().collect { it as Integer }.join(',')}")
                    }
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
