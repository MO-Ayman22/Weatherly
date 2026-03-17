package com.example.weatherly.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherly.data.source.local.dao.FavoriteDao
import com.example.weatherly.data.source.local.db.AppDatabase
import com.example.weatherly.domain.model.FavoriteWeather
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: FavoriteDao

    private val fakeFavorite = FavoriteWeather(
        lat = 30.0444,
        lon = 31.2357,
        location = "Cairo",
        language = "en",
        lastFetch = 0L,
        temperature = 23,
        feelsLike = 23,
        condition = "any",
        description = "Disc",
        icon = 1,
        humidity = 32,
        windSpeed = 65,
        pressure = 907,
        clouds = 54,
    )

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoriteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertFavorite_and_getAllFavorites_returns_inserted_item() = runTest {
        // Arrange
        dao.insertFavorite(fakeFavorite)

        // Act
        val result = dao.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result.first(), CoreMatchers.`is`(fakeFavorite))
    }

    @Test
    fun getAllFavorites_returns_empty_when_nothing_inserted() = runTest {
        // Arrange - empty db

        // Act
        val result = dao.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result.isEmpty(), CoreMatchers.`is`(true))
    }

    @Test
    fun getAllFavorites_filters_by_language() = runTest {
        // Arrange
        val arabicFavorite = fakeFavorite.copy(language = "ar")
        dao.insertFavorite(fakeFavorite)
        dao.insertFavorite(arabicFavorite)

        // Act
        val result = dao.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(result.first().language, CoreMatchers.`is`("en"))
    }

    @Test
    fun insertFavorite_replaces_on_conflict() = runTest {
        // Arrange
        val updated = fakeFavorite.copy(location = "Cairo")
        dao.insertFavorite(fakeFavorite)

        // Act
        dao.insertFavorite(updated)
        val result = dao.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(result.first().location, CoreMatchers.`is`("Cairo"))
    }

    @Test
    fun deleteFavoriteByLocation_removes_correct_item() = runTest {
        // Arrange
        dao.insertFavorite(fakeFavorite)

        // Act
        dao.deleteFavoriteByLocation(fakeFavorite.lat, fakeFavorite.lon)
        val result = dao.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result.isEmpty(), CoreMatchers.`is`(true))
    }

    @Test
    fun deleteFavoriteByLocation_does_not_remove_other_items() = runTest {
        // Arrange
        val another = fakeFavorite.copy(lat = 25.0, lon = 45.0)
        dao.insertFavorite(fakeFavorite)
        dao.insertFavorite(another)

        // Act
        dao.deleteFavoriteByLocation(fakeFavorite.lat, fakeFavorite.lon)
        val result = dao.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(result.first().lat, CoreMatchers.`is`(25.0))
    }
}