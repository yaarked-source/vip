package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class PulseTab(
  val label: String,
  val icon: ImageVector,
  val testTag: String
) {
  AQI_SMOG("AQI & Smog", Icons.Default.Air, "tab_aqi_smog"),
  NEWS("News", Icons.Default.Newspaper, "tab_news"),
  AI_VOICE("AI & Voice", Icons.Default.Psychology, "tab_ai_voice"),
  COMMUNITY("Community", Icons.Default.Forum, "tab_community"),
  CIVIC_DESK("Civic Desk", Icons.Default.ContactSupport, "tab_civic_desk")
}

@Composable
fun PakistanPulseBottomNav(
  currentTab: PulseTab,
  onTabSelected: (PulseTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
    tonalElevation = 8.dp,
    shadowElevation = 12.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .height(64.dp)
        .padding(horizontal = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      PulseTab.entries.forEach { tab ->
        val isSelected = tab == currentTab
        val color = if (isSelected) PakSecondary else PakOnSurfaceVariant

        Column(
          modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable { onTabSelected(tab) }
            .testTag(tab.testTag),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = tab.icon,
              contentDescription = tab.label,
              tint = color,
              modifier = Modifier.size(24.dp)
            )

            // AI tab beacon badge dot
            if (tab == PulseTab.AI_VOICE) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .align(Alignment.TopEnd)
                  .offset(x = 4.dp, y = (-2).dp)
                  .clip(CircleShape)
                  .background(PakSecondaryFixedDim)
              )
            }
          }

          Spacer(modifier = Modifier.height(3.dp))

          Text(
            text = tab.label,
            style = MaterialTheme.typography.labelSmall.copy(
              color = color,
              fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
              fontSize = 10.sp
            ),
            maxLines = 1
          )
        }
      }
    }
  }
}
