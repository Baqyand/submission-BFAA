package co.id.baqyandproject.submissionthree.model

import com.google.gson.annotations.SerializedName

data class UserGithub(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val username: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("public_repos")
    val repository: Int?,

    @field:SerializedName("repos_url")
    val reposUrl: String?,

    @field:SerializedName("followers")
    val follower: Int?,

    @field:SerializedName("following")
    val following: Int?,

    @field:SerializedName("avatar_url")
    val avatar: String?
)
