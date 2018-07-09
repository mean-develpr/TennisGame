package tennisgame

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        //"/"(view:"/index")
        "500"(view: '/error')
        "404"(view: '/notFound')

        //TODO fixme
        get "/score"(controller: 'scoreboard', action: "score")
        get "/point/$player?"(controller: 'scoreboard', action: "point")
        post "/point/$player?"(controller: 'scoreboard', action: "point")
        put "/point/$player?"(controller: 'scoreboard', action: "point")
    }
}
