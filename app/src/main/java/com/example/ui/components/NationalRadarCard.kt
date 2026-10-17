package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.DailyForecast
import com.example.model.WeatherIconType
import com.example.ui.theme.*

@Composable
fun NationalRadarCard(
  forecast: List<DailyForecast>,
  onAdvisoryDetailsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Infinite animations for radar map beacons
  val infiniteTransition = rememberInfiniteTransition(label = "radar_pings")
  val greenPingScale by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 1.4f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "green_ping"
  )
  val redPingScale by infiniteTransition.animateFloat(
    initialValue = 0.9f,
    targetValue = 1.6f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "red_ping"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("national_provincial_radar_card"),
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
      verticalArrangement = Arrangement.spacedBy(14.dp)
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
            imageVector = Icons.Default.Radar,
            contentDescription = null,
            tint = PakSecondary,
            modifier = Modifier.size(20.dp)
          )
          Text(
            text = "National Provincial Radar",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = PakOnSurface
            )
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(PakSecondaryFixed)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "Live Doppler",
            style = MaterialTheme.typography.labelSmall.copy(
              color = OnPakSecondaryFixed,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          )
        }
      }

      // Map Visual Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        // Satellite / Doppler Map Image
        AsyncImage(
          model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCSyQdrFxPSASxETSMlNwpxqO-OLyUoJKBUX01LF6ffXHcLZYaIO9SLjnY3oDLkJXMdP1ZVWgvtIJMAiBdL-OiaRvFwQcdYtQexQ_323jgixD-18jPEZ5OCJyYL8Ak4SSoJg2PDYmepkaKr-eiXJI2AAJKni7o39rr5uO2_JjRy66RvKlzEw_7SL7taq5f4G1RYzQtcpe2mjLxwBLsPhK4gMNFa5EAUmVyeF1rpr8WdR2VOUMGwVwDV",
          contentDescription = "Pakistan Provincial Radar Map",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Subtle gradient overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.12f))
        )

        // Top Overlay Badges
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .align(Alignment.TopCenter),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          // Punjab Smog Envelope pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(Color.White.copy(alpha = 0.92f))
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .scale(greenPingScale)
                  .clip(CircleShape)
                  .background(PakSecondary)
              )
              Text(
                text = "Punjab • High Smog Envelope",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = PakOnSurface,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                )
              )
            }
          }

          // KP Storm pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(Color.White.copy(alpha = 0.92f))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Thunderstorm,
                contentDescription = null,
                tint = PakSecondary,
                modifier = Modifier.size(12.dp)
              )
              Text(
                text = "KP Storm Radar",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = PakOnSurface,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 10.sp
                )
              )
            }
          }
        }

        // Contextual Radar Pings
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .align(Alignment.Center),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Gilgit ping
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
              modifier = Modifier.size(16.dp),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .scale(greenPingScale)
                  .clip(CircleShape)
                  .background(PakSecondaryFixedDim.copy(alpha = 0.5f))
              )
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(PakSecondary)
              )
            }
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(PakGreenPrimary.copy(alpha = 0.85f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "Gilgit: 8°C",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 9.sp
                )
              )
            }
          }

          // Lahore Central Ping (Severe Smog Alert)
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
              modifier = Modifier.size(20.dp),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(20.dp)
                  .scale(redPingScale)
                  .clip(CircleShape)
                  .background(PakError.copy(alpha = 0.6f))
              )
              Box(
                modifier = Modifier
                  .size(9.dp)
                  .clip(CircleShape)
                  .background(PakError)
              )
            }
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(PakError.copy(alpha = 0.92f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "LHR: 284 AQI",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                )
              )
            }
          }

          // Karachi Coastal Ping
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
              modifier = Modifier.size(16.dp),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .scale(greenPingScale)
                  .clip(CircleShape)
                  .background(PakSecondaryFixedDim.copy(alpha = 0.5f))
              )
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(PakSecondary)
              )
            }
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(PakGreenPrimary.copy(alpha = 0.85f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "Khi: 31°C Humid",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 9.sp
                )
              )
            }
          }
        }

        // Bottom Map Legend
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .align(Alignment.BottomCenter)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.92f))
            .clickable { onAdvisoryDetailsClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
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
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = PakSecondary,
                modifier = Modifier.size(14.dp)
              )
              Text(
                text = "Active NDMA Smog & Inversion Advisory",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = PakOnSurface,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 11.sp
                )
              )
            }

            Text(
              text = "Details →",
              style = MaterialTheme.typography.labelSmall.copy(
                color = PakSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            )
          }
        }
      }

      // 4-Day Forecast Grid Preview
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        forecast.take(4).forEach { day ->
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(PakSurfaceContainerLow)
              .padding(vertical = 10.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
              Text(
                text = day.dayLabel,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = PakOnSurfaceVariant,
                  fontSize = 10.sp
                ),
                maxLines = 1
              )

              Icon(
                imageVector = when (day.weatherIconType) {
                  WeatherIconType.SUNNY -> Icons.Default.WbSunny
                  WeatherIconType.PARTLY_CLOUDY -> Icons.Default.CloudSync
                  WeatherIconType.WINDY -> Icons.Default.Air
                  WeatherIconType.HAZE -> Icons.Default.Grain
                  WeatherIconType.RAIN -> Icons.Default.Thunderstorm
                  else -> Icons.Default.Cloud
                },
                contentDescription = null,
                tint = when (day.weatherIconType) {
                  WeatherIconType.SUNNY, WeatherIconType.PARTLY_CLOUDY -> PakTertiaryFixedDim
                  else -> PakSecondary
                },
                modifier = Modifier.size(20.dp)
              )

              Text(
                text = "${day.highTemp}° / ${day.lowTemp}°",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = PakOnSurface,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                )
              )

              Text(
                text = "AQI ${day.aqi}",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = when {
                    day.aqi > 250 -> PakError
                    day.aqi > 150 -> OnPakTertiaryContainer
                    else -> PakSecondary
                  },
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 10.sp
                )
              )
            }
          }
        }
      }
    }
  }
}
