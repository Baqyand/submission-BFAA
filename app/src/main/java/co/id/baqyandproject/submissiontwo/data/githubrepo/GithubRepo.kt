package co.id.baqyandproject.submissiontwo.data.githubrepo

import androidx.annotation.Nullable
import co.id.baqyandproject.submissiontwo.data.callback.PostCallBack

interface GithubRepo {
    suspend fun searchUser(query: String, @Nullable callBack: PostCallBack)
    suspend fun getUserDetail(username: String, @Nullable callBack: PostCallBack)
    suspend fun getFollower(username: String, @Nullable callBack: PostCallBack)
    suspend fun getFollowing(username: String, @Nullable callBack: PostCallBack)
}