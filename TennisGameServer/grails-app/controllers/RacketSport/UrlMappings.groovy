package RacketSport

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

        get "/score"(controller: 'scoreboard', action: "score")
        put "/point/$player?"(controller: 'scoreboard', action: "point")
        post "/newMatch"(controller: 'scoreboard', action: "newMatch")

    }
}
