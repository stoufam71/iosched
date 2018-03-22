/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("FunctionName")

package com.google.samples.apps.iosched.tv.ui

import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.Row
import com.google.samples.apps.iosched.shared.util.TimeUtils.ConferenceDay
import com.google.samples.apps.iosched.shared.util.getEnum
import com.google.samples.apps.iosched.tv.ui.schedule.ScheduleFragment
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MainPageRowFragmentFactoryTest {

    private lateinit var mainPageRowFragmentFactory: MainPageRowFragmentFactory

    @Before
    fun setup() {
        mainPageRowFragmentFactory = MainPageRowFragmentFactory()
    }

    @Test
    fun createFragment_withConferenceDay() {
        val expectedDay = ConferenceDay.DAY_1
        val row = Row(HeaderItem(expectedDay.ordinal.toLong(), expectedDay.name))

        val fragment = mainPageRowFragmentFactory.createFragment(row)

        val actualDay = fragment.arguments
                ?.getEnum<ConferenceDay>(ScheduleFragment.ARG_CONFERENCE_DAY)
        assertThat(actualDay, `is`(equalTo(expectedDay)))
    }

    @Test
    fun createFragment_onlyRowInstancesAccepted() {

        try {
            mainPageRowFragmentFactory.createFragment(Any())
            fail("Any is not a valid value and should throw an ClassCastException.")
        } catch (exception: Exception) {
            assertThat(exception, instanceOf(ClassCastException::class.java))
        }
    }

    @Test
    fun createFragment_invalidRowSelected() {

        val row = Row(HeaderItem(-1, "Invalid Row"))
        try {
            mainPageRowFragmentFactory.createFragment(row)
            fail("The id of the row should be a valid CONFERENCE_DAY index.")
        } catch (exception: Exception) {
            assertThat(exception, instanceOf(IllegalArgumentException::class.java))
        }
    }

}