package eu.bakici.githubclient.repos

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.IdlingResource.ResourceCallback


/**
 * Created on 19.01.18.
 */
class RepositoriesActivityTest : RepositoriesActivity(), IdlingResource {


    private var subscriber: TestRepositorySubscriber? = null
    private var callback: ResourceCallback? = null


    override fun fetchRepositories(name: String) {
        subscriber = TestRepositorySubscriber(name)
        viewModel.getRepositories(name, subscriber!!)
    }

    inner class TestRepositorySubscriber(name: String) : RepositorySubscriber(name) {
        var finished = false

        override fun onNext(repositories: List<Repository>?) {
            super.onNext(repositories)
            finished = true
            callback?.onTransitionToIdle()
        }

        override fun onCompleted() {
            super.onCompleted()
            finished = true
            callback?.onTransitionToIdle()
        }

        override fun onError(e: Throwable?) {
            super.onError(e)
            finished = false
            callback?.onTransitionToIdle()
        }
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        callback = resourceCallback
    }

    override fun getName(): String = "MainActivityTestIdlingResource"

    override fun isIdleNow(): Boolean {
        return subscriber?.finished ?: true
    }

}