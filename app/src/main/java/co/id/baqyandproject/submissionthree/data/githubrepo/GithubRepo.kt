package co.id.baqyandproject.submissionthree.data.githubrepo

import androidx.annotation.Nullable
import co.id.baqyandproject.submissionthree.data.callback.PostCallBack
import co.id.baqyandproject.submissionthree.model.UserGithub

interface GithubRepo {
    suspend fun searchUser(query: String, @Nullable callBack: PostCallBack)
    suspend fun getUserDetail(username: String, @Nullable callBack: PostCallBack)
    suspend fun getFollower(username: String, @Nullable callBack: PostCallBack)
    suspend fun getFollowing(username: String, @Nullable callBack: PostCallBack)

    suspend fun setFavoriteUser(userGithub: UserGithub, @Nullable callBack: PostCallBack)
    suspend fun getFavoriteUsers(@Nullable callBack: PostCallBack)
    suspend fun getDetailUser(username: String, @Nullable callBack: PostCallBack)
    suspend fun deleteFavoriteUser(id: Int, @Nullable callBack: PostCallBack)
}