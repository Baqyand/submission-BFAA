package co.id.baqyandproject.submissionthree.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel

@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(favoriteUser: CacheUserGithubModel)

    @Query("SELECT * FROM favorit_user")
    fun getUserList(): List<CacheUserGithubModel>

    @Query("SELECT * FROM favorit_user WHERE username = :username")
    fun getUserDetail(username: String): CacheUserGithubModel

    @Query("DELETE FROM favorit_user WHERE id = :userId")
    fun deleteUser(userId: Int)
}