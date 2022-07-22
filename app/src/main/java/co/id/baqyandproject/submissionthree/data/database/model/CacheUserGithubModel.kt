package co.id.baqyandproject.submissionthree.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.id.baqyandproject.submissionthree.model.UserGithub

@Entity(tableName = "favorit_user")
data class CacheUserGithubModel(
    @PrimaryKey
    val id: Int,
    val username: String?,
    val name: String?,
    val location: String?,
    val company: String?,
    val repository: Int?,
    val reposUrl: String?,
    val followers: Int?,
    val following: Int?,
    val avatar: String?
) {
    companion object {
        fun fromDomain(userDetail: UserGithub): CacheUserGithubModel {
            return CacheUserGithubModel(
                id = userDetail.id,
                username = userDetail.username,
                name = userDetail.name,
                location = userDetail.location,
                avatar = userDetail.avatar,
                repository = userDetail.repository,
                followers = userDetail.follower,
                following = userDetail.following,
                company = userDetail.company,
                reposUrl = userDetail.reposUrl
            )
        }
    }

    fun toDomain(): UserGithub =
        UserGithub(
            id,
            username,
            name,
            location,
            company,
            repository,
            reposUrl,
            followers,
            following,
            avatar
        )
}
