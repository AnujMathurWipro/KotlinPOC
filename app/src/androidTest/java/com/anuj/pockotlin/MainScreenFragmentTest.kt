package com.anuj.pockotlin

import android.Manifest
import android.content.Context
import android.net.wifi.WifiManager
import android.view.View

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4

import com.anuj.pockotlin.log.Logger
import com.anuj.pockotlin.ui.MainActivity
import com.anuj.pockotlin.ui.fragment.MainScreenFragment

import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainScreenFragmentTest {

    @get:Rule
    var permissionRule = GrantPermissionRule.grant(Manifest.permission.CHANGE_WIFI_STATE)
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    private var mIdlingResource: SimpleIdlingRes? = null

    internal inner class SimpleIdlingRes : IdlingResource {
        private var idle = false
        var callback: IdlingResource.ResourceCallback? = null


        fun setIdle(idle: Boolean) {
            Logger.debugLog("setIdle $idle")
            this.idle = idle
            if (idle && callback != null)
                callback!!.onTransitionToIdle()
        }

        override fun getName(): String {
            return "TestName"
        }

        override fun isIdleNow(): Boolean {
            return idle
        }

        override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
            this.callback = callback
        }
    }

    @Before
    fun setUp() {
        mIdlingResource = SimpleIdlingRes()
        IdlingRegistry.getInstance().register(mIdlingResource!!)
    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource!!)
        }
    }

    @Test
    fun testCorrect() {
        val cb = object : MainScreenFragment.IdlingCallback{
            override fun onIdle() {
                mIdlingResource!!.setIdle(true)
            }

        }
        val factory = MainScreenFragmentFactory(cb)
        mIdlingResource!!.registerIdleTransitionCallback { Logger.debugLog("Now went idle") }

        FragmentScenario.launchInContainer(MainScreenFragment::class.java, null, factory)

        Espresso.onView(ViewMatchers.withId(R.id.tv_errorText))
                .check(ViewAssertions.matches(CoreMatchers.not<View>(ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.rvItemList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rvItemList))
                .check(ViewAssertions.matches(CustomMatchers.withItemCount(14)))
        Espresso.onView(ViewMatchers.withId(R.id.rvItemList))
                .check(CustomAssertions.hasItemCount(14))
    }

    @Test
    fun testNoInternet() {
        val wifi = activityRule.activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifi.disconnect()
        val cb = object : MainScreenFragment.IdlingCallback{
            override fun onIdle() {
                mIdlingResource!!.setIdle(true)
            }

        }
        val factory = MainScreenFragmentFactory(cb)
        mIdlingResource!!.registerIdleTransitionCallback { Logger.debugLog("Now went idle") }

        FragmentScenario.launchInContainer(MainScreenFragment::class.java, null, factory)

        Espresso.onView(ViewMatchers.withId(R.id.tv_errorText))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rvItemList))
                .check(ViewAssertions.matches(CoreMatchers.not<View>(ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.rvItemList))
                .check(ViewAssertions.matches(CustomMatchers.withItemCount(0)))
    }
}
