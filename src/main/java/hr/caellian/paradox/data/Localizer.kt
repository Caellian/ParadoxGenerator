package hr.caellian.paradox.data

class Localizer() {
    var region: String = ""
        private set
    var name: String = ""
        private set

    var localizations = mutableMapOf<String, String>()

    constructor(region: String, name: String): this() {
        this.region = region
        this.name = name
    }
}