package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.model.CivicNews
import com.example.ui.theme.*

@Composable
fun NewsScreen(
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf("All") }
  val categories = listOf("All", "Regulation", "Education", "Travel Alert", "Science & Tech", "Civic Traffic")

  val filteredNews = remember(selectedCategory) {
    if (selectedCategory == "All") TelemetryRepository.civicNewsList
    else TelemetryRepository.civicNewsList.filter { it.category == selectedCategory }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .testTag("news_screen_column"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = "Civic News & Bulletins",
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = PakGreenPrimary
          )
        )
        Text(
          text = "Official updates from EPA, NDMA, and Punjab Environment Wing",
          style = MaterialTheme.typography.bodySmall.copy(
            color = PakOnSurfaceVariant
          )
        )
      }
    }

    // Category Filter Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
      ) {
        items(categories) { cat ->
          val isSelected = cat == selectedCategory
          FilterChip(
            selected = isSelected,
            onClick = { selectedCategory = cat },
            label = {
              Text(
                text = cat,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = PakGreenPrimaryContainer,
              selectedLabelColor = Color.White
            )
          )
        }
      }
    }

    // News Items
    items(filteredNews) { item ->
      NewsCard(item = item)
    }
  }
}

@Composable
private fun NewsCard(item: CivicNews) {
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
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (item.isAlert) PakErrorContainer else PakSecondaryContainer)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = item.category.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
              color = if (item.isAlert) OnPakErrorContainer else OnPakSecondaryContainer,
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            )
          )
        }

        Text(
          text = item.timestamp,
          style = MaterialTheme.typography.labelSmall.copy(
            color = PakOnSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }

      Text(
        text = item.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = PakOnSurface
        )
      )

      Text(
        text = item.urduTitle,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.SemiBold,
          color = PakSecondary
        )
      )

      Text(
        text = item.summary,
        style = MaterialTheme.typography.bodySmall.copy(
          color = PakOnSurfaceVariant,
          lineHeight = 18.sp
        )
      )

      Divider(
        color = MaterialTheme.colorScheme.surfaceVariant,
        thickness = 0.8.dp,
        modifier = Modifier.padding(top = 4.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.AccountBalance,
            contentDescription = null,
            tint = PakSecondary,
            modifier = Modifier.size(14.dp)
          )
          Text(
            text = item.department,
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakSecondary,
              fontWeight = FontWeight.SemiBold,
              fontSize = 11.sp
            )
          )
        }

        Icon(
          imageVector = Icons.Default.Share,
          contentDescription = "Share bulletin",
          tint = PakOnSurfaceVariant,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}
