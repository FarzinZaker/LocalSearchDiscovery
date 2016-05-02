class UrlMappings {

    static mappings = {

//        "/advertisement/$i1/$id"(controller: 'place', action: 'explore')
//        "/tag/$i1/$id"(controller: 'place', action: 'explore')
//        "/category/$i1/$id"(controller: 'place', action: 'explore')
//        "/location/$i1/$id"(controller: 'place', action: 'explore')
//        "/search/$id"(controller: 'place', action: 'explore')

        "/$controller/$action\\.json" {}
        "/image/$type?/$id?"(controller: "image", action: "index")
        "/$controller/$action?/$id?/$name?" {
            constraints {
                // apply constraints here
            }
        }
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
    }
}
