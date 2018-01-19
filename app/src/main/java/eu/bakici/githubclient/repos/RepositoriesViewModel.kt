package eu.bakici.githubclient.repos

import eu.bakici.githubclient.dagger.ApplicationComponent
import eu.bakici.githubclient.net.BackendAccess
import rx.Subscriber
import javax.inject.Inject

/**
 * Created on 13.01.18.
 */
class RepositoriesViewModel {

    @Inject
    lateinit var backendAccess: BackendAccess

    fun inject(applicationComponent: ApplicationComponent) {
        applicationComponent.inject(this)
    }


    fun getRepositories(name: String, subscriber: Subscriber<List<Repository>>) {
        backendAccess.getRepositories(name)
                .map { response -> response.repositories }
                .subscribe(subscriber)
    }


}