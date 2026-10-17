package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TelemetryRepository
import com.example.model.CivicHelpline
import com.example.ui.theme.*

@Composable
fun CivicDeskScreen(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .testTag("civic_desk_column"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "Civic Helplines & Support",
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = PakGreenPrimary
          )
        )
        Text(
          text = "Direct government lines for smog emergencies, respiratory aid, and road assistance",
          style = MaterialTheme.typography.bodySmall.copy(
            color = PakOnSurfaceVariant
          )
        )
      }
    }

    // Helplines
    items(TelemetryRepository.civicHelplines) { item ->
      HelplineCard(
        item = item,
        onDial = {
          val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item.number}"))
          try {
            context.startActivity(intent)
          } catch (_: Exception) {}
        }
      )
    }

    // AQI Reference Scale Card
    item {
      AqiScaleCard()
    }

    // App Info Card
    item {
      AppVersionCard()
    }
  }
}

@Composable
private fun HelplineCard(
  item: CivicHelpline,
  onDial: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(PakGreenPrimaryContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = when (item.iconType) {
              "warning" -> Icons.Default.Warning
              "airwave" -> Icons.Default.Air
              "medical_services" -> Icons.Default.MedicalServices
              else -> Icons.Default.DirectionsCar
            },
            contentDescription = null,
            tint = PakSecondaryFixed,
            modifier = Modifier.size(22.dp)
          )
        }

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = item.agency,
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakSecondary,
              fontWeight = FontWeight.Bold
            )
          )
          Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = PakOnSurface
            )
          )
          Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall.copy(
              color = PakOnSurfaceVariant,
              fontSize = 11.sp
            ),
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }

      Button(
        onClick = onDial,
        colors = ButtonDefaults.buttonColors(
          containerColor = PakSecondary,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(999.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Text(
            text = item.number,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}

@Composable
private fun AqiScaleCard() {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Text(
        text = "Air Quality Index (AQI) Reference Scale",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = PakOnSurface
        )
      )

      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        AqiScaleRow("0 - 50", "Good", AqiGood, "Air quality is satisfactory")
        AqiScaleRow("51 - 100", "Moderate", AqiModerate, "Acceptable air quality")
        AqiScaleRow("101 - 150", "Unhealthy (Sensitive)", AqiUnhealthySensitive, "Sensitive groups experience irritation")
        AqiScaleRow("151 - 200", "Unhealthy", AqiUnhealthy, "Everyone begins to experience health effects")
        AqiScaleRow("201 - 300", "Very Unhealthy", AqiVeryUnhealthy, "Emergency health warnings issued")
        AqiScaleRow("300+", "Hazardous", AqiHazardous, "Serious health impairment; avoid all outdoor air")
      }
    }
  }
}

@Composable
private fun AqiScaleRow(
  range: String,
  title: String,
  color: Color,
  desc: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(PakSurfaceContainerLow)
      .padding(horizontal = 10.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Box(
      modifier = Modifier
        .size(12.dp)
        .clip(CircleShape)
        .background(color)
    )
    Column(modifier = Modifier.weight(1f)) {
      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
          text = range,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = PakOnSurface
          )
        )
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.ExtraBold,
            color = color
          )
        )
      }
      Text(
        text = desc,
        style = MaterialTheme.typography.bodySmall.copy(
          color = PakOnSurfaceVariant,
          fontSize = 10.sp
        )
      )
    }
  }
}

@Composable
private fun AppVersionCard() {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Pakistan Pulse Official Direct",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "v2.4.1",
          style = MaterialTheme.typography.labelMedium.copy(
            color = PakSecondary,
            fontWeight = FontWeight.Bold
          )
        )
      }
      Text(
        text = "Built for rapid regional telemetry without Google Play Store propagation delays. Verified by Play Protect.",
        style = MaterialTheme.typography.bodySmall.copy(color = PakOnSurfaceVariant)
      )
    }
  }
}
