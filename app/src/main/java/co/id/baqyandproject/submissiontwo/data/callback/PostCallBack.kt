package co.id.baqyandproject.submissiontwo.data.callback

interface PostCallBack : ErrorCallBack {
    fun <T> onSuccessRequest(data: T)
}