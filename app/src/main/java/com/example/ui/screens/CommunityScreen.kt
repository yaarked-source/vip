package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.model.CitizenReport
import com.example.ui.theme.*

@Composable
fun CommunityScreen(
  modifier: Modifier = Modifier
) {
  val reports = remember { mutableStateListOf(*TelemetryRepository.citizenReports.toTypedArray()) }
  var showAddDialog by remember { mutableStateOf(false) }

  var reportLocation by remember { mutableStateOf("") }
  var reportCategory by remember { mutableStateOf("Open Trash Burning") }
  var reportDescription by remember { mutableStateOf("") }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag("community_screen_column"),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      item {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Text(
            text = "Civic Community Radar",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = PakGreenPrimary
            )
          )
          Text(
            text = "Citizen-verified smog hot-spots, crop burning, and generator smoke",
            style = MaterialTheme.typography.bodySmall.copy(
              color = PakOnSurfaceVariant
            )
          )
        }
      }

      items(reports, key = { it.id }) { report ->
        CitizenReportCard(
          report = report,
          onUpvote = {
            val idx = reports.indexOfFirst { it.id == report.id }
            if (idx != -1) {
              reports[idx] = report.copy(upvotes = report.upvotes + 1)
            }
          }
        )
      }
    }

    // Floating Action Button to post report
    FloatingActionButton(
      onClick = { showAddDialog = true },
      containerColor = PakSecondary,
      contentColor = Color.White,
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(bottom = 80.dp, end = 16.dp)
        .testTag("add_report_fab")
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
        Text(
          text = "Report Smoke",
          fontWeight = FontWeight.Bold
        )
      }
    }
  }

  // Add Report Dialog
  if (showAddDialog) {
    AlertDialog(
      onDismissRequest = { showAddDialog = false },
      title = {
        Text("Report Pollution Incident", fontWeight = FontWeight.Bold)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Reports are sent to EPA anti-smog squad & community dashboard.",
            style = MaterialTheme.typography.bodySmall,
            color = PakOnSurfaceVariant
          )

          OutlinedTextField(
            value = reportLocation,
            onValueChange = { reportLocation = it },
            label = { Text("Sector / Location (e.g. Gulberg, Lahore)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )

          OutlinedTextField(
            value = reportCategory,
            onValueChange = { reportCategory = it },
            label = { Text("Category (e.g. Generator, Trash, Kiln)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )

          OutlinedTextField(
            value = reportDescription,
            onValueChange = { reportDescription = it },
            label = { Text("Description & Visible Impact") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (reportLocation.isNotBlank() && reportDescription.isNotBlank()) {
              reports.add(
                0,
                CitizenReport(
                  id = System.currentTimeMillis().toString(),
                  citizenName = "Citizen (You)",
                  city = "Lahore",
                  sector = reportLocation,
                  timeAgo = "Just now",
                  category = reportCategory,
                  description = reportDescription,
                  estimatedAqi = 310,
                  upvotes = 1,
                  isVerified = true
                )
              )
              reportLocation = ""
              reportDescription = ""
              showAddDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = PakSecondary)
        ) {
          Text("Submit Report", color = Color.White)
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
private fun CitizenReportCard(
  report: CitizenReport,
  onUpvote: () -> Unit
) {
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
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = report.sector,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = PakOnSurface
            )
          )

          if (report.isVerified) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "Verified",
              tint = PakSecondary,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(PakErrorContainer)
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "Est. AQI ${report.estimatedAqi}",
            style = MaterialTheme.typography.labelSmall.copy(
              color = OnPakErrorContainer,
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            )
          )
        }
      }

      Text(
        text = "${report.citizenName} • ${report.city} • ${report.timeAgo}",
        style = MaterialTheme.typography.labelSmall.copy(
          color = PakOnSurfaceVariant,
          fontSize = 11.sp
        )
      )

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(PakSurfaceContainerLow)
          .padding(horizontal = 8.dp, vertical = 3.dp)
      ) {
        Text(
          text = report.category,
          style = MaterialTheme.typography.labelSmall.copy(
            color = PakSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        )
      }

      Text(
        text = report.description,
        style = MaterialTheme.typography.bodySmall.copy(
          color = PakOnSurface
        )
      )

      Divider(
        color = MaterialTheme.colorScheme.surfaceVariant,
        thickness = 0.8.dp,
        modifier = Modifier.padding(top = 2.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${report.upvotes} citizens confirmed this spot",
          style = MaterialTheme.typography.labelSmall.copy(
            color = PakOnSurfaceVariant
          )
        )

        Button(
          onClick = onUpvote,
          colors = ButtonDefaults.buttonColors(
            containerColor = PakSurfaceContainerLow,
            contentColor = PakSecondary
          ),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
          shape = RoundedCornerShape(999.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.ThumbUp,
              contentDescription = null,
              modifier = Modifier.size(13.dp)
            )
            Text(
              text = "Confirm (+1)",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}
