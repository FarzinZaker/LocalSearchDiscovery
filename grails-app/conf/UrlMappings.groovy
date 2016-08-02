class UrlMappings {

    static mappings = {

        "/advertisement/$i1/$id"(controller: 'place', action: 'explore')
        "/tag/search/$id"(controller: 'tag', action: 'search')
        "/tag/$i1/$id"(controller: 'place', action: 'explore')
        "/category/search/$id"(controller: 'category', action: 'search')
        "/category/$i1/$id"(controller: 'place', action: 'explore')
        "/location/$i1/$city"(controller: 'place', action: 'explore')
        "/search/$id"(controller: 'place', action: 'explore')
        "/search_$id"(controller: 'place', action: 'explore')

        "/sitemap/index"(controller: 'sitemap', action: 'index')
        "/sitemap/$type/$id"(controller: 'sitemap', action: 'view')

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
        "404"(view: '/404')
        "500"(view: '/500')
    }
}
