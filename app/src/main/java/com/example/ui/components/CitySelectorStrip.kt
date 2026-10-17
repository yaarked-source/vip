package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun CitySelectorStrip(
  cities: List<CityTelemetry>,
  selectedCityId: String,
  onCitySelected: (CityTelemetry) -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  // Pulsing dot for selected/current city
  val infiniteTransition = rememberInfiniteTransition(label = "city_pulse")
  val dotScale by infiniteTransition.animateFloat(
    initialValue = 0.9f,
    targetValue = 1.3f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "dot_scale"
  )

  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(scrollState),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    cities.forEach { city ->
      val isSelected = city.id == selectedCityId

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(999.dp))
          .background(
            if (isSelected) PakGreenPrimaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
          )
          .clickable { onCitySelected(city) }
          .padding(horizontal = 14.dp, vertical = 8.dp)
          .testTag("city_chip_${city.id}"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          if (isSelected) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .scale(dotScale)
                .clip(CircleShape)
                .background(PakSecondaryFixed)
            )
          }

          Text(
            text = if (isSelected && city.id == "lahore") "${city.name} (Current)" else city.name,
            style = MaterialTheme.typography.labelMedium.copy(
              color = if (isSelected) Color.White else PakOnSurface,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
              fontSize = 13.sp
            )
          )
        }
      }
    }
  }
}
