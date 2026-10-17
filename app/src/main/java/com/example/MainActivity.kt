package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.TelemetryRepository
import com.example.model.CityTelemetry
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        PakistanPulseApp()
      }
    }
  }
}

@Composable
fun PakistanPulseApp() {
  var currentTab by remember { mutableStateOf(PulseTab.AQI_SMOG) }
  var selectedCity by remember { mutableStateOf<CityTelemetry>(TelemetryRepository.cities.first()) }
  var showProfileDialog by remember { mutableStateOf(false) }
  var showDownloadDialog by remember { mutableStateOf(false) }
  var isDownloading by remember { mutableStateOf(false) }
  var downloadProgress by remember { mutableFloatStateOf(0f) }

  // Handle system back navigation
  BackHandler(enabled = currentTab != PulseTab.AQI_SMOG) {
    currentTab = PulseTab.AQI_SMOG
  }

  // Trigger simulated download progress when requested
  LaunchedEffect(isDownloading) {
    if (isDownloading) {
      downloadProgress = 0f
      while (downloadProgress < 1f) {
        delay(150)
        downloadProgress += 0.15f
      }
      downloadProgress = 1f
      isDownloading = false
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.surface,
    topBar = {
      Column(modifier = Modifier.statusBarsPadding()) {
        PakistanPulseHeader(
          onProfileClick = { showProfileDialog = true },
          onFastDlClick = {
            showDownloadDialog = true
            isDownloading = true
          }
        )
      }
    },
    bottomBar = {
      PakistanPulseBottomNav(
        currentTab = currentTab,
        onTabSelected = { tab -> currentTab = tab }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      AnimatedContent(
        targetState = currentTab,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "tab_transition"
      ) { tab ->
        when (tab) {
          PulseTab.AQI_SMOG -> AqiSmogScreen(
            selectedCity = selectedCity,
            onCitySelected = { city -> selectedCity = city },
            onInstallApkClick = {
              showDownloadDialog = true
              isDownloading = true
            }
          )
          PulseTab.NEWS -> NewsScreen()
          PulseTab.AI_VOICE -> AiVoiceScreen()
          PulseTab.COMMUNITY -> CommunityScreen()
          PulseTab.CIVIC_DESK -> CivicDeskScreen()
        }
      }
    }
  }

  // Profile Dialog with Google Account info
  if (showProfileDialog) {
    AlertDialog(
      onDismissRequest = { showProfileDialog = false },
      title = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(PakGreenPrimaryContainer),
            contentAlignment = Alignment.Center
          ) {
            AsyncImage(
              model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBNuK5SV0n84gjsgaJsDCtJt7AsKLrKMCJiCEFDUVuMgHKrr-SOUiWFDgbYOPS50maIx9S91GC8rL3J9_7xTcVlOGaqAnt6sFCWW-HpkY3O3IdJW7QqLlEaG3xlzCJcagcv-JHTIgBgQ1hmiKu7zhrTG3QvgjVZnRbSv7STPRtKro7A3TiT_cbZNYt63kW-9bnS0r16Vkf5x-3v9nRVOqIEj6WTK1oaoZV958CicWe8wTdGQRSzv2ti",
              contentDescription = null,
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
            )
          }
          Column {
            Text(
              text = "Civic Citizen Account",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Verified Google Identity",
              style = MaterialTheme.typography.labelSmall.copy(color = PakSecondary)
            )
          }
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Connected via Google Sign-In with regional telemetry sync.",
            style = MaterialTheme.typography.bodySmall,
            color = PakOnSurfaceVariant
          )

          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = PakSurfaceContainerLow)
          ) {
            Column(
              modifier = Modifier.padding(12.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Primary Telemetry Hub",
                  style = MaterialTheme.typography.labelSmall.copy(color = PakOnSurfaceVariant)
                )
                Text(
                  text = "${selectedCity.name} (${selectedCity.stationName})",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PakOnSurface
                  )
                )
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Smog Inversion Alerts",
                  style = MaterialTheme.typography.labelSmall.copy(color = PakOnSurfaceVariant)
                )
                Text(
                  text = "Enabled (Push & SMS)",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PakSecondary
                  )
                )
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Verified Reports Filed",
                  style = MaterialTheme.typography.labelSmall.copy(color = PakOnSurfaceVariant)
                )
                Text(
                  text = "4 Reports",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PakOnSurface
                  )
                )
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showProfileDialog = false },
          colors = ButtonDefaults.buttonColors(containerColor = PakSecondary)
        ) {
          Text("Done", color = Color.White)
        }
      }
    )
  }

  // APK Direct Download Dialog
  if (showDownloadDialog) {
    AlertDialog(
      onDismissRequest = {
        if (!isDownloading) showDownloadDialog = false
      },
      icon = {
        Icon(
          imageVector = Icons.Default.Download,
          contentDescription = null,
          tint = PakSecondary,
          modifier = Modifier.size(36.dp)
        )
      },
      title = {
        Text(
          text = if (downloadProgress < 1f) "Downloading APK v2.4.1..." else "APK Download Ready",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      },
      text = {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "Package: com.aistudio.pakpulse.czqrm • Size: 18 MB\nDirect High-Speed Regional Mirror (Karachi & Lahore CDN Edge)",
            style = MaterialTheme.typography.bodySmall,
            color = PakOnSurfaceVariant
          )

          LinearProgressIndicator(
            progress = { downloadProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = PakSecondary,
            trackColor = PakSurfaceContainerLow
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = if (downloadProgress < 1f) "${(downloadProgress * 18).toInt()} MB / 18 MB" else "18 MB / 18 MB (Verified)",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = PakSecondary
              )
            )

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = null,
                tint = PakSecondaryFixedDim,
                modifier = Modifier.size(12.dp)
              )
              Text(
                text = "Play Protect Passed",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  color = PakSecondary
                )
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showDownloadDialog = false },
          enabled = downloadProgress >= 1f,
          colors = ButtonDefaults.buttonColors(containerColor = PakGreenPrimaryContainer)
        ) {
          Text("Open & Install", color = Color.White)
        }
      },
      dismissButton = {
        if (!isDownloading) {
          TextButton(onClick = { showDownloadDialog = false }) {
            Text("Close")
          }
        }
      }
    )
  }
}
