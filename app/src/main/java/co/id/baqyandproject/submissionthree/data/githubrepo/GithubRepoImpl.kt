package co.id.baqyandproject.submissionthree.data.githubrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import co.id.baqyandproject.submissionthree.data.GithubApiService
import co.id.baqyandproject.submissionthree.data.callback.PostCallBack
import co.id.baqyandproject.submissionthree.data.database.CacheUserGithub
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel
import co.id.baqyandproject.submissionthree.data.preferences.Preferences
import co.id.baqyandproject.submissionthree.model.UserGithub
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiClient: GithubApiService,
    private val cacheUserGithub: CacheUserGithub,
    private val preferences: Preferences
) : GithubRepo, PreferencesRepo {

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

    override suspend fun setFavoriteUser(userGithub: UserGithub, callBack: PostCallBack) {
        callBack.onSuccessRequest(cacheUserGithub.addUser(CacheUserGithubModel.fromDomain(userGithub)))
    }

    override suspend fun getFavoriteUsers(callBack: PostCallBack) {
        callBack.onSuccessRequest(cacheUserGithub.getUsers())
    }

    override suspend fun getDetailUser(username: String, callBack: PostCallBack) {
        callBack.onSuccessRequest(cacheUserGithub.getUserDetail(username))
    }

    override suspend fun deleteFavoriteUser(id: Int, callBack: PostCallBack) {
        callBack.onSuccessRequest(cacheUserGithub.deleteUser(id))
    }

    override suspend fun setTheme(isDarkMode: Boolean) {
        preferences.setAppMode(isDarkMode)
    }

    override fun getTheme(): LiveData<Boolean> {
        return preferences.getAppMode().asLiveData()
    }
}