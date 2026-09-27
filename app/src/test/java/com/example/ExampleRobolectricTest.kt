package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.CurriculumRepository
import com.example.data.repository.LabDataRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AI ML Academy", appName)
  }

  @Test
  fun `curriculum repository provides complete roadmap domains`() {
    val domains = CurriculumRepository.domains
    assertTrue("Domains must not be empty", domains.isNotEmpty())
    assertTrue("At least 5 core AI/ML tracks available", domains.size >= 5)
  }

  @Test
  fun `creator profile verified as Awiskar Acharya`() {
    val creator = LabDataRepository.creator
    assertEquals("Awiskar Acharya", creator.name)
    assertTrue("LinkedIn URL must be valid", creator.linkedInUrl.contains("linkedin.com/in/awiskaracharya"))
  }
}
