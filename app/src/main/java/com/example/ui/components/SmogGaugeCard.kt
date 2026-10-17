package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Masks
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TelemetryRepository
import com.example.model.CityTelemetry
import com.example.model.HealthAdvisory
import com.example.ui.theme.*

@Composable
fun SmogGaugeCard(
  city: CityTelemetry,
  onAdvisoryClick: (HealthAdvisory) -> Unit,
  modifier: Modifier = Modifier
) {
  // Red warning ping animation
  val infiniteTransition = rememberInfiniteTransition(label = "hazard_pulse")
  val pingScale by infiniteTransition.animateFloat(
    initialValue = 0.9f,
    targetValue = 1.4f,
    animationSpec = infiniteRepeatable(
      animation = tween(1100, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "ping_scale"
  )

  // Animated sweep for AQI gauge
  val targetSweep = (city.aqi.toFloat() / 400f * 260f).coerceIn(20f, 260f)
  val animatedSweep by animateFloatAsState(
    targetValue = targetSweep,
    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
    label = "aqi_sweep"
  )

  val gaugeColor = when {
    city.aqi <= 50 -> AqiGood
    city.aqi <= 100 -> AqiModerate
    city.aqi <= 150 -> AqiUnhealthySensitive
    city.aqi <= 200 -> AqiUnhealthy
    city.aqi <= 300 -> AqiVeryUnhealthy
    else -> AqiHazardous
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("smog_telemetry_radar_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Box(modifier = Modifier.fillMaxWidth()) {
      // Hazard ambient flare
      Box(
        modifier = Modifier
          .size(170.dp)
          .align(Alignment.BottomEnd)
          .offset(x = 40.dp, y = 40.dp)
          .clip(CircleShape)
          .background(PakErrorContainer.copy(alpha = 0.35f))
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Title & Live Smog Alert Status
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier.size(12.dp),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(12.dp)
                  .scale(pingScale)
                  .clip(CircleShape)
                  .background(PakError.copy(alpha = 0.4f))
              )
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(PakError)
              )
            }

            Text(
              text = "SMOG TELEMETRY RADAR",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = PakOnSurface,
                letterSpacing = 0.8.sp
              )
            )
          }

          // Severity warning pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(999.dp))
              .background(PakErrorContainer)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (city.aqi > 250) "Severe Warning" else "Elevated Risk",
              style = MaterialTheme.typography.labelSmall.copy(
                color = OnPakErrorContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            )
          }
        }

        // Circular Gauge & Metric Cluster
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular gauge drawn with Canvas
          Box(
            modifier = Modifier.size(112.dp),
            contentAlignment = Alignment.Center
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val strokeWidth = 11.dp.toPx()
              val padding = strokeWidth / 2
              val arcSize = Size(size.width - 2 * padding, size.height - 2 * padding)
              val topLeft = Offset(padding, padding)

              // Track background
              drawArc(
                color = Color(0xFFE2E7FF),
                startAngle = 140f,
                sweepAngle = 260f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
              )

              // Active AQI progress
              drawArc(
                color = gaugeColor,
                startAngle = 140f,
                sweepAngle = animatedSweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
              )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "${city.aqi}",
                style = MaterialTheme.typography.headlineLarge.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = gaugeColor,
                  fontSize = 28.sp
                )
              )
              Text(
                text = "US-AQI",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = PakOnSurfaceVariant,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                )
              )
            }
          }

          // AQI Interpretation & Urdu Notice
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              text = city.aqiCategory,
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                color = gaugeColor,
                fontSize = 17.sp
              )
            )

            Text(
              text = city.aqiUrdu,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = gaugeColor
              )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // PM2.5 Density tile
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(PakSurfaceContainerLow)
                .padding(horizontal = 10.dp, vertical = 7.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Fine PM2.5 Density",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = PakOnSurfaceVariant,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp
                  )
                )
                Text(
                  text = "${city.pm25Density} µg/m³",
                  style = MaterialTheme.typography.labelMedium.copy(
                    color = PakOnSurface,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp
                  )
                )
              }
            }
          }
        }

        // Citizen Health Advisory Section
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "CITIZEN HEALTH ADVISORY",
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakOnSurface,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.8.sp,
              fontSize = 10.sp
            )
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            TelemetryRepository.healthAdvisories.forEach { adv ->
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(12.dp))
                  .background(PakSurfaceContainerLow)
                  .clickable { onAdvisoryClick(adv) }
                  .padding(vertical = 10.dp, horizontal = 6.dp)
                  .testTag("health_advisory_${adv.id}"),
                contentAlignment = Alignment.Center
              ) {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(
                    imageVector = when (adv.id) {
                      "mask" -> Icons.Default.Masks
                      "purifier" -> Icons.Default.Air
                      else -> Icons.Default.DirectionsRun
                    },
                    contentDescription = null,
                    tint = if (adv.isUrgent) PakError else PakSecondary,
                    modifier = Modifier.size(22.dp)
                  )

                  Text(
                    text = adv.title,
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = PakOnSurface,
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp
                    ),
                    maxLines = 1
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
