package com.example.model

data class HourlyTelemetry(
  val time: String,
  val temp: Int,
  val aqi: Int,
  val weatherIconType: WeatherIconType,
  val isCurrent: Boolean = false
)

enum class WeatherIconType {
  SUNNY,
  PARTLY_CLOUDY,
  CLOUDY,
  RAIN,
  HAZE,
  NIGHT_CLEAR,
  NIGHT_CLOUDY,
  WINDY
}

data class DailyForecast(
  val dayLabel: String,
  val weatherIconType: WeatherIconType,
  val highTemp: Int,
  val lowTemp: Int,
  val aqi: Int
)

data class CityTelemetry(
  val id: String,
  val name: String,
  val urduName: String,
  val province: String,
  val stationName: String,
  val updatedMinsAgo: Int,
  val temp: Int,
  val feelsLike: Int,
  val condition: String,
  val humidityPercent: Int,
  val humidityDesc: String,
  val windSpeedKmH: Int,
  val windDirection: String,
  val visibilityKm: Double,
  val visibilityDesc: String,
  val aqi: Int,
  val aqiCategory: String,
  val aqiUrdu: String,
  val pm25Density: Int,
  val hourly: List<HourlyTelemetry>,
  val forecast: List<DailyForecast>
)

data class HealthAdvisory(
  val id: String,
  val title: String,
  val subtitle: String,
  val iconName: String,
  val isUrgent: Boolean = false,
  val detailedAdvice: String
)

data class CivicNews(
  val id: String,
  val title: String,
  val urduTitle: String,
  val department: String,
  val timestamp: String,
  val category: String,
  val summary: String,
  val isAlert: Boolean = false,
  val sourceUrl: String = ""
)

data class CitizenReport(
  val id: String,
  val citizenName: String,
  val city: String,
  val sector: String,
  val timeAgo: String,
  val category: String,
  val description: String,
  val estimatedAqi: Int,
  val upvotes: Int,
  val isVerified: Boolean = false
)

data class CivicHelpline(
  val id: String,
  val agency: String,
  val title: String,
  val number: String,
  val description: String,
  val iconType: String
)

data class CivicAiMessage(
  val id: String,
  val sender: String, // "user" or "copilot"
  val text: String,
  val urduSummary: String = "",
  val timestamp: String
)
