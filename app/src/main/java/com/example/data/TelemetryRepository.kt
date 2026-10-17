package com.example.data

import com.example.model.*

object TelemetryRepository {

  val cities: List<CityTelemetry> = listOf(
    CityTelemetry(
      id = "lahore",
      name = "Lahore",
      urduName = "لاہور کا موسم",
      province = "Punjab",
      stationName = "Gulberg Telemetry Stn",
      updatedMinsAgo = 4,
      temp = 24,
      feelsLike = 26,
      condition = "Partly Cloudy",
      humidityPercent = 62,
      humidityDesc = "Moderate mist",
      windSpeedKmH = 14,
      windDirection = "North-West",
      visibilityKm = 2.8,
      visibilityDesc = "Smog haze",
      aqi = 284,
      aqiCategory = "Very Unhealthy",
      aqiUrdu = "شدید فضائی آلودگی",
      pm25Density = 234,
      hourly = listOf(
        HourlyTelemetry("Now", 24, 284, WeatherIconType.CLOUDY, isCurrent = true),
        HourlyTelemetry("14:00", 27, 310, WeatherIconType.SUNNY),
        HourlyTelemetry("16:00", 26, 295, WeatherIconType.PARTLY_CLOUDY),
        HourlyTelemetry("18:00", 23, 280, WeatherIconType.CLOUDY),
        HourlyTelemetry("20:00", 21, 265, WeatherIconType.NIGHT_CLEAR),
        HourlyTelemetry("22:00", 19, 250, WeatherIconType.NIGHT_CLEAR),
        HourlyTelemetry("00:00", 18, 240, WeatherIconType.NIGHT_CLOUDY),
        HourlyTelemetry("02:00", 16, 235, WeatherIconType.NIGHT_CLOUDY)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.PARTLY_CLOUDY, 25, 14, 290),
        DailyForecast("Wednesday", WeatherIconType.WINDY, 23, 12, 210),
        DailyForecast("Thursday", WeatherIconType.SUNNY, 24, 13, 275),
        DailyForecast("Friday", WeatherIconType.HAZE, 21, 11, 140)
      )
    ),
    CityTelemetry(
      id = "karachi",
      name = "Karachi",
      urduName = "کراچی کا موسم",
      province = "Sindh",
      stationName = "Clifton Coastal Sensor",
      updatedMinsAgo = 6,
      temp = 31,
      feelsLike = 35,
      condition = "Humid & Hazy",
      humidityPercent = 78,
      humidityDesc = "High sea humidity",
      windSpeedKmH = 22,
      windDirection = "South-West",
      visibilityKm = 4.5,
      visibilityDesc = "Sea haze",
      aqi = 118,
      aqiCategory = "Moderate",
      aqiUrdu = "معتدل معیار",
      pm25Density = 42,
      hourly = listOf(
        HourlyTelemetry("Now", 31, 118, WeatherIconType.PARTLY_CLOUDY, isCurrent = true),
        HourlyTelemetry("14:00", 33, 125, WeatherIconType.SUNNY),
        HourlyTelemetry("16:00", 32, 120, WeatherIconType.SUNNY),
        HourlyTelemetry("18:00", 29, 115, WeatherIconType.WINDY),
        HourlyTelemetry("20:00", 27, 110, WeatherIconType.NIGHT_CLEAR),
        HourlyTelemetry("22:00", 26, 105, WeatherIconType.NIGHT_CLEAR)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.SUNNY, 32, 22, 115),
        DailyForecast("Wednesday", WeatherIconType.PARTLY_CLOUDY, 31, 23, 120),
        DailyForecast("Thursday", WeatherIconType.WINDY, 30, 21, 108),
        DailyForecast("Friday", WeatherIconType.SUNNY, 32, 22, 112)
      )
    ),
    CityTelemetry(
      id = "islamabad",
      name = "Islamabad",
      urduName = "اسلام آباد کا موسم",
      province = "Federal",
      stationName = "F-7 Margalla Foothills Lab",
      updatedMinsAgo = 2,
      temp = 21,
      feelsLike = 22,
      condition = "Clear Sky",
      humidityPercent = 48,
      humidityDesc = "Crisp air",
      windSpeedKmH = 9,
      windDirection = "North",
      visibilityKm = 6.0,
      visibilityDesc = "Good visibility",
      aqi = 158,
      aqiCategory = "Unhealthy for Sensitive",
      aqiUrdu = "حساس افراد کے لیے مضر",
      pm25Density = 68,
      hourly = listOf(
        HourlyTelemetry("Now", 21, 158, WeatherIconType.SUNNY, isCurrent = true),
        HourlyTelemetry("14:00", 24, 162, WeatherIconType.SUNNY),
        HourlyTelemetry("16:00", 23, 155, WeatherIconType.PARTLY_CLOUDY),
        HourlyTelemetry("18:00", 19, 150, WeatherIconType.NIGHT_CLEAR),
        HourlyTelemetry("20:00", 16, 145, WeatherIconType.NIGHT_CLEAR)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.SUNNY, 23, 11, 150),
        DailyForecast("Wednesday", WeatherIconType.RAIN, 19, 9, 85),
        DailyForecast("Thursday", WeatherIconType.PARTLY_CLOUDY, 20, 10, 110),
        DailyForecast("Friday", WeatherIconType.SUNNY, 22, 11, 130)
      )
    ),
    CityTelemetry(
      id = "peshawar",
      name = "Peshawar",
      urduName = "پشاور کا موسم",
      province = "KPK",
      stationName = "Hayatabad Environmental Stn",
      updatedMinsAgo = 10,
      temp = 22,
      feelsLike = 23,
      condition = "Dense Haze",
      humidityPercent = 55,
      humidityDesc = "Dust inversion",
      windSpeedKmH = 7,
      windDirection = "West",
      visibilityKm = 3.2,
      visibilityDesc = "Inversion fog",
      aqi = 245,
      aqiCategory = "Very Unhealthy",
      aqiUrdu = "شدید فضائی آلودگی",
      pm25Density = 195,
      hourly = listOf(
        HourlyTelemetry("Now", 22, 245, WeatherIconType.HAZE, isCurrent = true),
        HourlyTelemetry("14:00", 25, 260, WeatherIconType.HAZE),
        HourlyTelemetry("16:00", 24, 250, WeatherIconType.HAZE),
        HourlyTelemetry("18:00", 20, 240, WeatherIconType.NIGHT_CLOUDY)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.HAZE, 24, 12, 240),
        DailyForecast("Wednesday", WeatherIconType.PARTLY_CLOUDY, 22, 10, 195),
        DailyForecast("Thursday", WeatherIconType.SUNNY, 23, 11, 210),
        DailyForecast("Friday", WeatherIconType.WINDY, 21, 9, 160)
      )
    ),
    CityTelemetry(
      id = "quetta",
      name = "Quetta",
      urduName = "کوئٹہ کا موسم",
      province = "Balochistan",
      stationName = "Chiltan High-Altitude Stn",
      updatedMinsAgo = 8,
      temp = 16,
      feelsLike = 14,
      condition = "Dry & Cool",
      humidityPercent = 28,
      humidityDesc = "Dry valley breeze",
      windSpeedKmH = 18,
      windDirection = "North-East",
      visibilityKm = 8.0,
      visibilityDesc = "Clear valley",
      aqi = 92,
      aqiCategory = "Moderate",
      aqiUrdu = "معتدل ہوا",
      pm25Density = 31,
      hourly = listOf(
        HourlyTelemetry("Now", 16, 92, WeatherIconType.SUNNY, isCurrent = true),
        HourlyTelemetry("14:00", 18, 98, WeatherIconType.SUNNY),
        HourlyTelemetry("16:00", 17, 95, WeatherIconType.WINDY),
        HourlyTelemetry("18:00", 13, 88, WeatherIconType.NIGHT_CLEAR)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.SUNNY, 17, 5, 90),
        DailyForecast("Wednesday", WeatherIconType.WINDY, 15, 3, 85),
        DailyForecast("Thursday", WeatherIconType.SUNNY, 16, 4, 92),
        DailyForecast("Friday", WeatherIconType.SUNNY, 18, 6, 95)
      )
    ),
    CityTelemetry(
      id = "multan",
      name = "Multan",
      urduName = "ملتان کا موسم",
      province = "Punjab",
      stationName = "Chungi No. 9 Sensor Grid",
      updatedMinsAgo = 5,
      temp = 26,
      feelsLike = 28,
      condition = "Severe Smog",
      humidityPercent = 64,
      humidityDesc = "Thermal inversion",
      windSpeedKmH = 8,
      windDirection = "Calm",
      visibilityKm = 1.9,
      visibilityDesc = "Dense smog",
      aqi = 298,
      aqiCategory = "Hazardous",
      aqiUrdu = "خطرناک فضائی آلودگی",
      pm25Density = 248,
      hourly = listOf(
        HourlyTelemetry("Now", 26, 298, WeatherIconType.HAZE, isCurrent = true),
        HourlyTelemetry("14:00", 28, 320, WeatherIconType.HAZE),
        HourlyTelemetry("16:00", 27, 305, WeatherIconType.HAZE),
        HourlyTelemetry("18:00", 23, 290, WeatherIconType.NIGHT_CLOUDY)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.HAZE, 27, 15, 305),
        DailyForecast("Wednesday", WeatherIconType.HAZE, 26, 14, 280),
        DailyForecast("Thursday", WeatherIconType.PARTLY_CLOUDY, 25, 13, 240),
        DailyForecast("Friday", WeatherIconType.SUNNY, 26, 14, 210)
      )
    ),
    CityTelemetry(
      id = "faisalabad",
      name = "Faisalabad",
      urduName = "فیصل آباد کا موسم",
      province = "Punjab",
      stationName = "Clock Tower Industrial Hub",
      updatedMinsAgo = 7,
      temp = 25,
      feelsLike = 27,
      condition = "Industrial Smog",
      humidityPercent = 60,
      humidityDesc = "Heavy particulate",
      windSpeedKmH = 6,
      windDirection = "East",
      visibilityKm = 2.1,
      visibilityDesc = "Toxic smog layer",
      aqi = 312,
      aqiCategory = "Hazardous",
      aqiUrdu = "انتہائی خطرناک",
      pm25Density = 260,
      hourly = listOf(
        HourlyTelemetry("Now", 25, 312, WeatherIconType.HAZE, isCurrent = true),
        HourlyTelemetry("14:00", 28, 335, WeatherIconType.HAZE),
        HourlyTelemetry("16:00", 27, 320, WeatherIconType.HAZE),
        HourlyTelemetry("18:00", 23, 300, WeatherIconType.NIGHT_CLOUDY)
      ),
      forecast = listOf(
        DailyForecast("Tomorrow", WeatherIconType.HAZE, 26, 14, 315),
        DailyForecast("Wednesday", WeatherIconType.HAZE, 24, 13, 290),
        DailyForecast("Thursday", WeatherIconType.PARTLY_CLOUDY, 25, 12, 260),
        DailyForecast("Friday", WeatherIconType.SUNNY, 24, 12, 220)
      )
    )
  )

  val healthAdvisories: List<HealthAdvisory> = listOf(
    HealthAdvisory(
      id = "mask",
      title = "Wear N95 Mask",
      subtitle = "Mandatory outdoors",
      iconName = "masks",
      isUrgent = false,
      detailedAdvice = "Standard surgical or cloth masks filter less than 20% of fine PM2.5 particles. Always wear a snug-fitting N95 or KN95 respirator when commuting outdoors in Lahore & central Punjab."
    ),
    HealthAdvisory(
      id = "purifier",
      title = "Air Purifiers On",
      subtitle = "Keep indoor HEPA running",
      iconName = "mode_fan",
      isUrgent = false,
      detailedAdvice = "Keep windows sealed between 5:00 AM and 11:00 AM when thermal inversion traps heavy PM2.5 smog near the ground. Run HEPA air filters in living and sleeping areas."
    ),
    HealthAdvisory(
      id = "outdoors",
      title = "Avoid Outdoors",
      subtitle = "Sensitive groups remain inside",
      iconName = "directions_run",
      isUrgent = true,
      detailedAdvice = "Children, elderly individuals, and asthma or cardiac patients must cancel outdoor sports and jogging. Heavy breathing during exercise pulls toxic heavy metals deep into pulmonary alveoli."
    )
  )

  val civicNewsList: List<CivicNews> = listOf(
    CivicNews(
      id = "1",
      title = "Punjab EPA Imposes Section 144 on Crop Stubble & Brick Kilns",
      urduTitle = "پنجاب میں فصلوں کی باقیات جلانے اور بھٹوں پر دفعہ 144 نافذ",
      department = "Punjab Environment Protection Agency",
      timestamp = "28 mins ago",
      category = "Regulation",
      summary = "Special anti-smog squads have been deployed across Sheikhupura, Kasur, and Gujranwala with drone surveillance to penalize illegal residue burning.",
      isAlert = true
    ),
    CivicNews(
      id = "2",
      title = "School Timings Adjusted Across Lahore & Multan Divisions",
      urduTitle = "لاہور اور ملتان کے سکولوں کے اوقات کار میں تبدیلی",
      department = "Punjab School Education Department",
      timestamp = "1 hour ago",
      category = "Education",
      summary = "Primary classes to start at 8:45 AM with compulsory mask mandates; outdoor assemblies and sports suspended until AQI levels drop below 200.",
      isAlert = false
    ),
    CivicNews(
      id = "3",
      title = "NDMA Issues Thermal Inversion Advisory for Motorways M-2 and M-3",
      urduTitle = "این ڈی ایم اے: موٹرویز پر شدید سموگ اور حد نگاہ کی کمی کا الرٹ",
      department = "National Disaster Management Authority",
      timestamp = "2 hours ago",
      category = "Travel Alert",
      summary = "Night and early morning visibility dropped to under 100 meters between Lahore, Pindi Bhattian, and Faisalabad. Commuters urged to use fog lights.",
      isAlert = true
    ),
    CivicNews(
      id = "4",
      title = "Artificial Rain & Cloud Seeding Prepared by Meteorological Dept",
      urduTitle = "لاہور میں مصنوعی بارش کے دوسرے مرحلے کی تیاریاں مکمل",
      department = "Pakistan Meteorological Department (PMD)",
      timestamp = "3 hours ago",
      category = "Science & Tech",
      summary = "C-130 aircraft and flares configured at Walton base awaiting requisite cloud moisture levels (above 40%) to trigger artificial rain wash.",
      isAlert = false
    ),
    CivicNews(
      id = "5",
      title = "Green Lockdown Zones Established in Gulberg, Mall Road, and Shimla Hill",
      urduTitle = "لاہور میں گرین لاک ڈاؤن کے مزید زونز کا اعلان",
      department = "Lahore District Administration",
      timestamp = "5 hours ago",
      category = "Civic Traffic",
      summary = "Heavy transport vehicles strictly restricted, auto-rickshaws without catalytic converters barred, and construction sites mandated with continuous water sprinkling.",
      isAlert = false
    )
  )

  val citizenReports: List<CitizenReport> = listOf(
    CitizenReport(
      id = "r1",
      citizenName = "Hamza Farooq",
      city = "Lahore",
      sector = "Gulberg III / MM Alam",
      timeAgo = "12 mins ago",
      category = "Dense Smoke / Generator",
      description = "Large commercial diesel generator emitting thick unburnt black soot without scrubber. Visibility in alley zero.",
      estimatedAqi = 340,
      upvotes = 24,
      isVerified = true
    ),
    CitizenReport(
      id = "r2",
      citizenName = "Dr. Ayesha Malik",
      city = "Lahore",
      sector = "DHA Phase 5",
      timeAgo = "35 mins ago",
      category = "Open Trash Burning",
      description = "Lawn waste and plastic bags burning behind commercial market near Ring Road interchange. Strong sulfur odor.",
      estimatedAqi = 310,
      upvotes = 42,
      isVerified = true
    ),
    CitizenReport(
      id = "r3",
      citizenName = "Usman Tariq",
      city = "Faisalabad",
      sector = "Satiana Road",
      timeAgo = "1 hour ago",
      category = "Brick Kiln Operation",
      description = "Traditional Bull's Trench kiln operating without Zig-Zag conversion during daytime lockdown.",
      estimatedAqi = 380,
      upvotes = 31,
      isVerified = false
    ),
    CitizenReport(
      id = "r4",
      citizenName = "Zainab Raza",
      city = "Rawalpindi",
      sector = "I-J-P Road Border",
      timeAgo = "2 hours ago",
      category = "Heavy Traffic Congestion",
      description = "Fleet of older cargo trucks stuck at toll plaza emitting dark exhaust fumes. Breathing difficulty reported by shopkeepers.",
      estimatedAqi = 260,
      upvotes = 19,
      isVerified = true
    )
  )

  val civicHelplines: List<CivicHelpline> = listOf(
    CivicHelpline(
      id = "h1",
      agency = "NDMA Pakistan",
      title = "National Disaster Helpline",
      number = "1129",
      description = "24/7 disaster, flood, severe smog emergency coordination and regional alerts.",
      iconType = "warning"
    ),
    CivicHelpline(
      id = "h2",
      agency = "Punjab EPA",
      title = "Anti-Smog Enforcement Cell",
      number = "0800-02345",
      description = "Toll-free complaint line for illegal crop burning, industrial smoke, and smoking vehicles.",
      iconType = "airwave"
    ),
    CivicHelpline(
      id = "h3",
      agency = "Rescue 1122",
      title = "Emergency Medical & Ambulance",
      number = "1122",
      description = "Immediate medical intervention for acute respiratory distress, asthma attacks, and road accidents in low visibility.",
      iconType = "medical_services"
    ),
    CivicHelpline(
      id = "h4",
      agency = "National Highways & Motorway Police",
      title = "Motorway Fog & Smog Help",
      number = "130",
      description = "Real-time fog updates on M-1, M-2, M-3, M-4, M-5, diversions, and convoy escorts.",
      iconType = "traffic"
    )
  )
}
