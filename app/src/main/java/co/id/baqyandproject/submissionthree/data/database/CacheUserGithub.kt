package co.id.baqyandproject.submissionthree.data.database

import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel

interface CacheUserGithub {
    suspend fun addUser(cachedGitHubUser: CacheUserGithubModel)
    suspend fun getUsers(): List<CacheUserGithubModel>
    suspend fun getUserDetail(username: String): CacheUserGithubModel
    suspend fun deleteUser(userId: Int)
}