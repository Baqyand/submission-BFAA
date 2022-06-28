package co.id.baqyandproject.submissiontwo.data.githubrepo

import co.id.baqyandproject.submissiontwo.data.GithubApiService
import co.id.baqyandproject.submissiontwo.data.callback.PostCallBack
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiClient: GithubApiService
) : GithubRepo {

    companion object {
        private const val TAG = "Get Github"
    }

    override suspend fun searchUser(query: String, callBack: PostCallBack) {
        callBack.onSuccessRequest(apiClient.searchUsers(query))
    }

    override suspend fun getUserDetail(username: String, callBack: PostCallBack) {
        callBack.onSuccessRequest(
            apiClient.getDetailUser(username)
        )
    }

    override suspend fun getFollower(username: String, callBack: PostCallBack) {
        callBack.onSuccessRequest(apiClient.getUserFollowers(username))
    }

    override suspend fun getFollowing(username: String, callBack: PostCallBack) {
        callBack.onSuccessRequest(apiClient.getUserFollowing(username))
    }
}