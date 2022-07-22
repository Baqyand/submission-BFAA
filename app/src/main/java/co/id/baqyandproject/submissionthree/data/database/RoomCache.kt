package co.id.baqyandproject.submissionthree.data.database

import co.id.baqyandproject.submissionthree.data.database.dao.GitUserDao
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel
import javax.inject.Inject

class RoomCache @Inject constructor(private val gitUserDao: GitUserDao) : CacheUserGithub {

    override suspend fun addUser(cachedGitHubUser: CacheUserGithubModel) {
        gitUserDao.insertUser(cachedGitHubUser)
    }

    override suspend fun getUsers(): List<CacheUserGithubModel> {
        return gitUserDao.getUserList()
    }

    override suspend fun getUserDetail(username: String): CacheUserGithubModel {
        return gitUserDao.getUserDetail(username)
    }

    override suspend fun deleteUser(userId: Int) {
        gitUserDao.deleteUser(userId)
    }
}