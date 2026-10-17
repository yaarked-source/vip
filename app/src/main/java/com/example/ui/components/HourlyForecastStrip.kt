package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.HourlyTelemetry
import com.example.model.WeatherIconType
import com.example.ui.theme.*

@Composable
fun HourlyForecastStrip(
  hourlyList: List<HourlyTelemetry>,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("hourly_temperature_smog_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = PakSecondary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Hourly Temperature & Smog",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = PakOnSurface
            )
          )
        }

        Text(
          text = "Next 24 Hours",
          style = MaterialTheme.typography.labelSmall.copy(
            color = PakOnSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }

      // Horizontal Tiles
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        hourlyList.forEach { item ->
          HourlyTile(item = item)
        }
      }
    }
  }
}

@Composable
private fun HourlyTile(item: HourlyTelemetry) {
  val isCurrent = item.isCurrent

  Box(
    modifier = Modifier
      .width(66.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(
        if (isCurrent) PakGreenPrimaryContainer
        else PakSurfaceContainerLow
      )
      .padding(vertical = 10.dp, horizontal = 6.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = item.time,
        style = MaterialTheme.typography.labelSmall.copy(
          color = if (isCurrent) Color.White else PakOnSurfaceVariant,
          fontWeight = FontWeight.SemiBold,
          fontSize = 11.sp
        )
      )

      Icon(
        imageVector = when (item.weatherIconType) {
          WeatherIconType.SUNNY -> Icons.Default.WbSunny
          WeatherIconType.PARTLY_CLOUDY -> Icons.Default.WbCloudy
          WeatherIconType.CLOUDY -> Icons.Default.Cloud
          WeatherIconType.RAIN -> Icons.Default.Thunderstorm
          WeatherIconType.HAZE -> Icons.Default.FilterDrama
          WeatherIconType.NIGHT_CLEAR -> Icons.Default.Bedtime
          WeatherIconType.NIGHT_CLOUDY -> Icons.Default.Bedtime
          WeatherIconType.WINDY -> Icons.Default.Air
        },
        contentDescription = null,
        tint = if (isCurrent) PakSecondaryFixed
        else when (item.weatherIconType) {
          WeatherIconType.SUNNY, WeatherIconType.PARTLY_CLOUDY -> PakTertiaryFixedDim
          WeatherIconType.RAIN, WeatherIconType.WINDY -> PakSecondary
          else -> PakOnSurfaceVariant
        },
        modifier = Modifier.size(22.dp)
      )

      Text(
        text = "${item.temp}°",
        style = MaterialTheme.typography.labelLarge.copy(
          color = if (isCurrent) Color.White else PakOnSurface,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      )

      Text(
        text = "${item.aqi} AQI",
        style = MaterialTheme.typography.labelSmall.copy(
          color = if (isCurrent) PakSecondaryFixedDim
          else if (item.aqi > 200) PakError
          else PakSecondary,
          fontWeight = FontWeight.SemiBold,
          fontSize = 10.sp
        )
      )
    }
  }
}
