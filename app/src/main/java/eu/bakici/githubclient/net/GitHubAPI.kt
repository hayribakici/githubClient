package eu.bakici.githubclient.net

import eu.bakici.githubclient.repos.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created on 13.01.18.
 */
interface GitHubAPI {


    @GET("/search/repositories")
    fun getRepositories(@Query("q") name: String): Observable<RepositoryResponse>
}