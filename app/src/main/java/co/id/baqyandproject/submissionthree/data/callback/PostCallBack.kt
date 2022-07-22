package co.id.baqyandproject.submissionthree.data.callback

interface PostCallBack : ErrorCallBack {
    fun <T> onSuccessRequest(data: T)
}