package fr.uha.gm.android.livedata

data class Reply<out T>(val status: Status, val content: T?, val message: Int?) {

    companion object {

        fun <T> success(content: T): Reply<T> {
            return Reply(Status.SUCCESS, content, null)
        }

        fun <T> error(message: Int): Reply<T> {
            return Reply(Status.ERROR, null, message)
        }

        fun <T> loading(message: Int): Reply<T> {
            return Reply(Status.LOADING, null, message)
        }

        fun <T> loading(): Reply<T> {
            return Reply(Status.LOADING, null, null)
        }

    }

}