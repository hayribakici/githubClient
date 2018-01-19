package eu.bakici.githubclient.repos

import com.google.gson.annotations.SerializedName

/**
 * Created on 13.01.18.
 */
data class RepositoryResponse(
        @SerializedName("total_count")
        val count: Int,
        @SerializedName("items")
        val repositories: List<Repository>)