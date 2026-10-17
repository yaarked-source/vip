package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TelemetryRepository
import com.example.model.CityTelemetry
import com.example.model.HealthAdvisory
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun AqiSmogScreen(
  selectedCity: CityTelemetry,
  onCitySelected: (CityTelemetry) -> Unit,
  onInstallApkClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedAdvisory by remember { mutableStateOf<HealthAdvisory?>(null) }
  var showNdmaDetails by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .testTag("aqi_smog_screen_column"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Floating Android APK Direct Download Banner
    item {
      ApkDirectBanner(onInstallClick = onInstallApkClick)
    }

    // 2. City Selector Horizontal Scroll Strip
    item {
      CitySelectorStrip(
        cities = TelemetryRepository.cities,
        selectedCityId = selectedCity.id,
        onCitySelected = onCitySelected
      )
    }

    // 3. Hero Live Weather Card
    item {
      HeroWeatherCard(city = selectedCity)
    }

    // 4. Atmospheric Smog & AQI Monitor Card
    item {
      SmogGaugeCard(
        city = selectedCity,
        onAdvisoryClick = { advisory -> selectedAdvisory = advisory }
      )
    }

    // 5. 24-Hour Forecast Horizontal Scrub Bar
    item {
      HourlyForecastStrip(hourlyList = selectedCity.hourly)
    }

    // 6. National Provincial Radar (Live Doppler + 4-day forecast)
    item {
      NationalRadarCard(
        forecast = selectedCity.forecast,
        onAdvisoryDetailsClick = { showNdmaDetails = true }
      )
    }
  }

  // Health Advisory Details Modal
  selectedAdvisory?.let { advisory ->
    AlertDialog(
      onDismissRequest = { selectedAdvisory = null },
      icon = {
        Icon(
          imageVector = when (advisory.id) {
            "mask" -> Icons.Default.Masks
            "purifier" -> Icons.Default.Air
            else -> Icons.Default.DirectionsRun
          },
          contentDescription = null,
          tint = if (advisory.isUrgent) PakError else PakSecondary,
          modifier = Modifier.size(32.dp)
        )
      },
      title = {
        Text(
          text = advisory.title,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = PakOnSurface
          )
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = advisory.subtitle,
            style = MaterialTheme.typography.labelMedium.copy(
              color = PakSecondary,
              fontWeight = FontWeight.Bold
            )
          )
          Text(
            text = advisory.detailedAdvice,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = PakOnSurface
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = { selectedAdvisory = null },
          colors = ButtonDefaults.buttonColors(containerColor = PakSecondary)
        ) {
          Text("Got It", color = Color.White)
        }
      }
    )
  }

  // NDMA Smog & Inversion Advisory Dialog
  if (showNdmaDetails) {
    AlertDialog(
      onDismissRequest = { showNdmaDetails = false },
      icon = {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          tint = PakError,
          modifier = Modifier.size(32.dp)
        )
      },
      title = {
        Text(
          text = "NDMA Thermal Inversion Bulletin",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
          )
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Issued by National Disaster Management Authority (NDMA) & PMD Pakistan",
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakOnSurfaceVariant
            )
          )
          Text(
            text = "A meteorological inversion layer has settled over the Indus Basin (Lahore, Gujranwala, Faisalabad, Sahiwal, and Multan). Calm surface winds (< 5 knots) prevent dispersion of industrial emissions and crop residue aerosols.",
            style = MaterialTheme.typography.bodySmall
          )
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(PakErrorContainer)
              .padding(8.dp)
          ) {
            Text(
              text = "Travel Warning: Motorway M-2 and M-3 visibility drops below 50m between 23:00 and 08:00 PKT. Strictly follow Motorway Police convoy escorts.",
              style = MaterialTheme.typography.bodySmall.copy(
                color = OnPakErrorContainer,
                fontWeight = FontWeight.SemiBold
              )
            )
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showNdmaDetails = false },
          colors = ButtonDefaults.buttonColors(containerColor = PakGreenPrimaryContainer)
        ) {
          Text("Dismiss", color = Color.White)
        }
      }
    )
  }
}
