class UrlMappings {

    static mappings = {

        "/advertisement/$i1/$id"(controller: 'place', action: 'explore')
        "/tag/$i1/$id"(controller: 'place', action: 'explore')
        "/category/search/$id"(controller: 'category', action: 'search')
        "/category/$i1/$id"(controller: 'place', action: 'explore')
        "/location/$i1/$city"(controller: 'place', action: 'explore')
        "/search/$id"(controller: 'place', action: 'explore')

        "/$controller/$action\\.json" {}
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
