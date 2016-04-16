class UrlMappings {

    static mappings = {

        "/image/$type?/$id?"(controller: "image", action: "index")
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
    }
}
