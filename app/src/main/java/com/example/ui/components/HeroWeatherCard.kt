package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CityTelemetry
import com.example.ui.theme.*

@Composable
fun HeroWeatherCard(
  city: CityTelemetry,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "sun_pulse")
  val sunScale by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(1800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "sun_scale"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("hero_weather_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Box(modifier = Modifier.fillMaxWidth()) {
      // Atmospheric ambient glow
      Box(
        modifier = Modifier
          .size(160.dp)
          .align(Alignment.TopEnd)
          .offset(x = 30.dp, y = (-30).dp)
          .clip(CircleShape)
          .background(PakSecondaryContainer.copy(alpha = 0.25f))
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Card Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Text(
                text = city.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = PakOnSurface
                )
              )
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = PakSecondary,
                modifier = Modifier.size(20.dp)
              )
            }

            Text(
              text = city.urduName,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = PakSecondary
              )
            )

            Text(
              text = "Updated ${city.updatedMinsAgo} mins ago • ${city.stationName}",
              style = MaterialTheme.typography.labelSmall.copy(
                color = PakOnSurfaceVariant,
                fontSize = 11.sp
              ),
              modifier = Modifier.padding(top = 2.dp)
            )
          }

          // Live Sensor Badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(PakSecondaryContainer)
              .padding(horizontal = 10.dp, vertical = 5.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(PakSecondary)
              )
              Text(
                text = "Live Sensor",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = OnPakSecondaryContainer,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                )
              )
            }
          }
        }

        // Temperature & Micro Weather Art
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.Bottom) {
            Text(
              text = "${city.temp}",
              style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 54.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PakOnSurface,
                letterSpacing = (-2).sp
              )
            )
            Text(
              text = "°C",
              style = MaterialTheme.typography.headlineLarge.copy(
                color = PakSecondary,
                fontWeight = FontWeight.Bold
              ),
              modifier = Modifier.padding(bottom = 8.dp, start = 2.dp)
            )

            Column(
              modifier = Modifier.padding(bottom = 10.dp, start = 14.dp)
            ) {
              Text(
                text = city.condition,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = PakOnSurface
                )
              )
              Text(
                text = "Feels like ${city.feelsLike}°C",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = PakOnSurfaceVariant
                )
              )
            }
          }

          // Sun and Cloud art widget
          Box(
            modifier = Modifier
              .size(68.dp),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.WbSunny,
              contentDescription = null,
              tint = PakTertiaryFixedDim,
              modifier = Modifier
                .size(46.dp)
                .scale(sunScale)
            )
            Icon(
              imageVector = Icons.Default.Cloud,
              contentDescription = null,
              tint = PakGreenPrimary.copy(alpha = 0.85f),
              modifier = Modifier
                .size(38.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 4.dp, y = 4.dp)
            )
          }
        }

        // Telemetry Metrics Grid (3 columns)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Humidity
          TelemetryBox(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.WaterDrop,
            title = "HUMIDITY",
            value = "${city.humidityPercent}%",
            subtext = city.humidityDesc,
            isWarning = false
          )

          // Wind
          TelemetryBox(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Air,
            title = "WIND",
            value = "${city.windSpeedKmH} km/h",
            subtext = city.windDirection,
            isWarning = false
          )

          // Visibility
          TelemetryBox(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Visibility,
            title = "VISIBILITY",
            value = "${city.visibilityKm} km",
            subtext = city.visibilityDesc,
            isWarning = city.visibilityKm < 3.5
          )
        }
      }
    }
  }
}

@Composable
private fun TelemetryBox(
  modifier: Modifier = Modifier,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  value: String,
  subtext: String,
  isWarning: Boolean
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(PakSurfaceContainerLow)
      .padding(vertical = 10.dp, horizontal = 8.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = PakSecondary,
          modifier = Modifier.size(13.dp)
        )
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(
            color = PakSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 9.sp
          )
        )
      }

      Text(
        text = value,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = PakOnSurface,
          fontSize = 14.sp
        )
      )

      Text(
        text = subtext,
        style = MaterialTheme.typography.labelSmall.copy(
          color = if (isWarning) PakError else PakOnSurfaceVariant,
          fontWeight = if (isWarning) FontWeight.Bold else FontWeight.Normal,
          fontSize = 10.sp
        ),
        maxLines = 1
      )
    }
  }
}
