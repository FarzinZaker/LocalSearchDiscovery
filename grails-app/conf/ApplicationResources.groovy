import org.codehaus.groovy.grails.plugins.jquery.JQueryConfig

modules = {
    application {
        resource id: 'js', url: [plugin: 'jquery', dir: 'js/jquery', file: "jquery-${JQueryConfig.SHIPPED_VERSION}.min.js"], disposition: 'head', nominify: true
        resource url: 'bootstrap/js/bootstrap.min.js'
        resource url: 'bootstrap/js/bootstrap-toggle.min.js'
        resource url: 'js/form-validator/jquery.form-validator.js'
        resource url: 'js/form-validator/security.js'
        resource url: 'js/form-validator/jquery.form-validator.custom.js'
        resource url: 'cropie/exif.js'
        resource url: 'cropie/croppie.min.js'
        resource url: 'js/jquery.unveil.js'
        resource url: 'selectize/js/standalone/selectize.min.js'
        resource url: 'js/common.js'
        resource url: 'bootstrap/css/bootstrap.min.css'
        resource url: 'bootstrap/css/bootstrap-toggle.min.css'
        resource url: 'bootstrap/css/bootstrap.vertical-tabs.min.css'
        resource url: 'cropie/croppie.css'
        resource url: 'selectize/css/selectize.bootstrap3.css'
        resource url: 'css/bootstrap-rtl.less',attrs:[rel: "stylesheet/less", type:'css']
        resource url: 'css/common.less',attrs:[rel: "stylesheet/less", type:'css']
    }
    maps {
        resource url: 'open-layers/ol.css'
        resource url: 'open-layers/ol.js'
//        resource url: 'open-layers/ol-debug.js'
    }

}