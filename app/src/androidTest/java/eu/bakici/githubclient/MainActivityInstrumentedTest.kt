package eu.bakici.githubclient

import android.app.Instrumentation
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import eu.bakici.githubclient.repos.RepositoriesActivityTest
import eu.bakici.githubclient.repos.Repository
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created on 19.01.18.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityInstrumentedTest {

    companion object {
        private const val REPO_NAME = "tetris"
    }

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RepositoriesActivityTest::class.java)

    lateinit var instrumentation: Instrumentation

    @Before
    fun setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation()
        IdlingRegistry.getInstance().register(activityRule.activity)
    }

    @Test
    fun testFetchRepositories() {
        // Type text and then press the button.
        onView(withId(R.id.action_search))
                .perform(ViewActions.click())

        onView(withId(android.support.design.R.id.search_src_text))
                .perform(ViewActions.typeTextIntoFocusedView(REPO_NAME), ViewActions.closeSoftKeyboard())
        onData(allOf(instanceOf(Repository::class.java), contains(REPO_NAME)))
    }
}
