package eu.bakici.githubclient.net

import eu.bakici.githubclient.repos.RepositoryResponse
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers

/**
 * Created on 13.01.18.
 */
open class BackendAccess(private val api: GitHubAPI, private val ioScheduler: Scheduler) {

    private val SEARCH_TYPE: String = "+repo"

    fun getRepositories(name: String): Observable<RepositoryResponse> {
        return api.getRepositories(name + SEARCH_TYPE)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
    }
}