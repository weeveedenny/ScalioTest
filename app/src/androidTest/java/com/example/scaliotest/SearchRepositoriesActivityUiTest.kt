package com.example.scaliotest


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.scaliotest.ui.SearchRepositoriesActivity
import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)

class SearchRepositoriesActivityUiTest {
@get:Rule
val activityRule:ActivityScenarioRule<SearchRepositoriesActivity> = ActivityScenarioRule(SearchRepositoriesActivity::class.java)

    @Test
    fun check_SearchRepositoriesActivity_visibility (){
        onView(withId(R.id.search_repo_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun check_InputLayout_visibility (){
        onView(withId(R.id.input_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun check_RecyclerView_visibility (){
        onView(withId(R.id.list)).check(matches(isDisplayed()))
    }

    @Test
    fun check_ProgressBar_visibility (){
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun check_SearchRepo_button_visibility (){
        onView(withId(R.id.repo_search_button)).check(matches(isDisplayed()))
    }



}