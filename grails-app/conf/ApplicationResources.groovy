modules = {
    application {
        resource id: 'js', url: [plugin: 'jquery', dir: 'js/jquery', file: "jquery-${org.codehaus.groovy.grails.plugins.jquery.JQueryConfig.SHIPPED_VERSION}.min.js"], disposition: 'head', nominify: true
        resource url: 'bootstrap/js/bootstrap.min.js'
        resource url: 'bootstrap/js/bootstrap-toggle.min.js'
        resource url: 'bootstrap/css/bootstrap.min.css'
        resource url: 'bootstrap/css/bootstrap-toggle.min.css'
        resource url: 'bootstrap/css/bootstrap-toggle.min.css'
        resource url: 'css/common.less',attrs:[rel: "stylesheet/less", type:'css']
    }
}