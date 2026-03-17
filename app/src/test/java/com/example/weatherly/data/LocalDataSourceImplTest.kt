package com.example.weatherly.data

import com.example.weatherly.data.source.local.LocalDataSourceImpl
import com.example.weatherly.data.source.local.dao.AlertDao
import com.example.weatherly.data.source.local.dao.FavoriteDao
import com.example.weatherly.data.source.local.dao.WeatherDao
import com.example.weatherly.domain.model.CurrentWeather
import com.example.weatherly.domain.model.DailyWeather
import com.example.weatherly.domain.model.FavoriteWeather
import com.example.weatherly.domain.model.HourlyWeather
import com.example.weatherly.domain.model.WeatherAlert
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class LocalDataSourceImplTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var alertDao: AlertDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var localDataSource: LocalDataSourceImpl

    @Before
    fun setUp() {
        weatherDao = mockk(relaxed = true)
        alertDao = mockk(relaxed = true)
        favoriteDao = mockk(relaxed = true)
        localDataSource = LocalDataSourceImpl(weatherDao, alertDao, favoriteDao)
    }

    @Test
    fun `insertCurrentWeather delegates to weatherDao`() = runTest {
        // Arrange
        val weather = mockk<CurrentWeather>()

        // Act
        localDataSource.insertCurrentWeather(weather)

        // Assert
        coVerify(exactly = 1) { weatherDao.insertCurrentWeather(weather) }
    }

    @Test
    fun `getCurrentWeather returns value from weatherDao`() = runTest {
        // Arrange
        val weather = mockk<CurrentWeather>()
        coEvery { weatherDao.getCurrentWeather("en") } returns weather

        // Act
        val result = localDataSource.getCurrentWeather("en")

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(weather))
    }

    @Test
    fun `deleteAll calls all three delete methods on weatherDao`() = runTest {
        // Arrange - relaxed mock

        // Act
        localDataSource.deleteAll()

        // Assert
        coVerify(exactly = 1) { weatherDao.deleteCurrent() }
        coVerify(exactly = 1) { weatherDao.deleteHourly() }
        coVerify(exactly = 1) { weatherDao.deleteDaily() }
    }

    @Test
    fun `insertHourlyWeather delegates to weatherDao`() = runTest {
        // Arrange
        val list = listOf(mockk<HourlyWeather>())

        // Act
        localDataSource.insertHourlyWeather(list)

        // Assert
        coVerify(exactly = 1) { weatherDao.insertHourlyWeather(list) }
    }

    @Test
    fun `getHourlyWeather returns value from weatherDao`() = runTest {
        // Arrange
        val list = listOf(mockk<HourlyWeather>())
        coEvery { weatherDao.getHourlyWeather("en") } returns list

        // Act
        val result = localDataSource.getHourlyWeather("en")

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(list))
    }

    @Test
    fun `insertDailyWeather delegates to weatherDao`() = runTest {
        // Arrange
        val list = listOf(mockk<DailyWeather>())

        // Act
        localDataSource.insertDailyWeather(list)

        // Assert
        coVerify(exactly = 1) { weatherDao.insertDailyWeather(list) }
    }

    @Test
    fun `getDailyWeather returns value from weatherDao`() = runTest {
        // Arrange
        val list = listOf(mockk<DailyWeather>())
        coEvery { weatherDao.getDailyWeather("en") } returns list

        // Act
        val result = localDataSource.getDailyWeather("en")

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(list))
    }

    @Test
    fun `getAlerts returns flow from alertDao`() = runTest {
        // Arrange
        val alerts = listOf(mockk<WeatherAlert>())
        every { alertDao.getAlerts() } returns flowOf(alerts)

        // Act
        val result = localDataSource.getAlerts().first()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(alerts))
    }

    @Test
    fun `insertAlert delegates to alertDao`() = runTest {
        // Arrange
        val alert = mockk<WeatherAlert>()

        // Act
        localDataSource.insertAlert(alert)

        // Assert
        coVerify(exactly = 1) { alertDao.insertAlert(alert) }
    }

    @Test
    fun `updateLastTriggered delegates to alertDao`() = runTest {
        // Arrange
        val type = "rain"
        val time = 123456789L

        // Act
        localDataSource.updateLastTriggered(type, time)

        // Assert
        coVerify(exactly = 1) { alertDao.updateLastTriggered(type, time) }
    }

    @Test
    fun `insertFavorite delegates to favoriteDao`() = runTest {
        // Arrange
        val favorite = mockk<FavoriteWeather>()

        // Act
        localDataSource.insertFavorite(favorite)

        // Assert
        coVerify(exactly = 1) { favoriteDao.insertFavorite(favorite) }
    }

    @Test
    fun `getAllFavorites returns flow from favoriteDao`() = runTest {
        // Arrange
        val favorites = listOf(mockk<FavoriteWeather>())
        every { favoriteDao.getAllFavorites("en") } returns flowOf(favorites)

        // Act
        val result = localDataSource.getAllFavorites("en").first()

        // Assert
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(favorites))
    }

    @Test
    fun `deleteFavoriteByLocation delegates to favoriteDao`() = runTest {
        // Arrange
        val lat = 30.0444
        val lon = 31.2357

        // Act
        localDataSource.deleteFavoriteByLocation(lat, lon)

        // Assert
        coVerify(exactly = 1) { favoriteDao.deleteFavoriteByLocation(lat, lon) }
    }
}