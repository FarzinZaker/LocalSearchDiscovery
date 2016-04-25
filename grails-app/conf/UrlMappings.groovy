class UrlMappings {

    static mappings = {

        "/$controller/$action\\.json" {}
        "/image/$type?/$id?"(controller: "image", action: "index")
        "/$controller/$action?/$id?/$name" {
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
