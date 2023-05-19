package thunderCore.utilities

class Time {
    companion object {
        lateinit var get: Time
    }
    init { get = this}

    val SEC: Long = 20
    val MIN = SEC * 60
    val TEN_MIN = MIN * 10
    val HOUR = MIN * 60

}