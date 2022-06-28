package co.id.baqyandproject.submissiontwo.data

import co.id.baqyandproject.submissiontwo.model.SearchResponse
import co.id.baqyandproject.submissiontwo.model.UserGithub
import co.id.baqyandproject.submissiontwo.util.GitConst
import retrofit2.http.*

interface GithubApiService {

    @GET(GitConst.SEARCH_USERS)
    @Headers("Authorization: token ${GitConst.TOKEN}")
    suspend fun searchUsers(
        @Query("q")
        query: String
    ): SearchResponse

    @GET("${GitConst.USER_DETAIL}{username}")
    @Headers("Authorization: token ${GitConst.TOKEN}")
    suspend fun getDetailUser(
        @Path("username")
        username: String
    ): UserGithub

    @GET("${GitConst.USER_DETAIL}{username}/followers")
    @Headers("Authorization: token ${GitConst.TOKEN}")
    suspend fun getUserFollowers(
        @Path("username")
        username: String
    ): List<UserGithub>

    @GET("${GitConst.USER_DETAIL}{username}/following")
    @Headers("Authorization: token ${GitConst.TOKEN}")
    suspend fun getUserFollowing(
        @Path("username")
        username: String
    ): List<UserGithub>
}