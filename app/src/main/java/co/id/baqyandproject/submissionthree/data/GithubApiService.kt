package co.id.baqyandproject.submissionthree.data

import co.id.baqyandproject.submissionthree.model.SearchResponse
import co.id.baqyandproject.submissionthree.model.UserGithub
import co.id.baqyandproject.submissionthree.util.GitConst
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

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