package co.id.baqyandproject.submissionthree.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.id.baqyandproject.submissionthree.data.database.dao.GitUserDao
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel

@Database(entities = [CacheUserGithubModel::class], version = 1, exportSchema = false)
abstract class GitDatabase : RoomDatabase() {
    abstract fun gitUserDao(): GitUserDao
}