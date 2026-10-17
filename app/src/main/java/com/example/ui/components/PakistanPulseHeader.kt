package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.*

@Composable
fun PakistanPulseHeader(
  onProfileClick: () -> Unit,
  onFastDlClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Infinite pulsing animation for live telemetry beacon
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.85f,
    targetValue = 1.35f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_scale"
  )
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 0.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_alpha"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
          )
        )
      )
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    // Top Row: Brand & Profile
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Brand Logo + Title
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Logo Image
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(PakGreenPrimaryContainer),
          contentAlignment = Alignment.Center
        ) {
          AsyncImage(
            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCwG6A5mppfUYg7y-MYJw7fc3X-hxNPRRha_fJrFDqNX0Di4dsZNRzjQ0uyQCwzzfDkh6ki7nFBth8Yz91caqajpBnNKLX7UHQVwEx35kS4zzSEl5FsKHkLNwYbt8WRjL3nbNTiE8UDCGdqeYH5wdXR-1GolgkdFsQ8TjOH_FSIIHDO9FApTBVEvQN-oTHffnJovEc0JGQi3QwQi9vgpF5dSVhKcW5USHPt0sLaN0teiqpt9Te24y7h",
            contentDescription = "Pakistan Pulse Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
          )
        }

        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "Pakistan Pulse",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = PakGreenPrimary
              )
            )

            // Animated Live Pulse Beacon
            Box(
              modifier = Modifier.size(12.dp),
              contentAlignment = Alignment.Center
            ) {
              Box(
                modifier = Modifier
                  .size(12.dp)
                  .scale(pulseScale)
                  .clip(CircleShape)
                  .background(PakSecondaryFixedDim.copy(alpha = pulseAlpha))
              )
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(PakSecondary)
              )
            }
          }

          Text(
            text = "CIVIC TELEMETRY • Weather & Aqi",
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakOnSurfaceVariant,
              fontSize = 10.sp,
              letterSpacing = 0.5.sp
            )
          )
        }
      }

      // Profile avatar with Google badge
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .clickable { onProfileClick() }
          .testTag("profile_avatar_button"),
        contentAlignment = Alignment.Center
      ) {
        AsyncImage(
          model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBNuK5SV0n84gjsgaJsDCtJt7AsKLrKMCJiCEFDUVuMgHKrr-SOUiWFDgbYOPS50maIx9S91GC8rL3J9_7xTcVlOGaqAnt6sFCWW-HpkY3O3IdJW7QqLlEaG3xlzCJcagcv-JHTIgBgQ1hmiKu7zhrTG3QvgjVZnRbSv7STPRtKro7A3TiT_cbZNYt63kW-9bnS0r16Vkf5x-3v9nRVOqIEj6WTK1oaoZV958CicWe8wTdGQRSzv2ti",
          contentDescription = "User Profile",
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape),
          contentScale = ContentScale.Crop
        )

        // Google badge in corner
        Box(
          modifier = Modifier
            .size(14.dp)
            .align(Alignment.BottomEnd)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color(0xFFE0E0E0), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "G",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4285F4)
          )
        }
      }
    }

    // Sub-banner pill: Official Release APK Direct + Fast DL
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(999.dp))
        .background(PakGreenPrimaryContainer)
        .padding(horizontal = 12.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Sensors,
          contentDescription = null,
          tint = PakSecondaryFixed,
          modifier = Modifier.size(14.dp)
        )
        Text(
          text = "OFFICIAL RELEASE",
          style = MaterialTheme.typography.labelSmall.copy(
            color = PakSecondaryFixed,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        )
        Text(
          text = "APK v2.4 Direct",
          style = MaterialTheme.typography.bodySmall.copy(
            color = Color.White,
            fontWeight = FontWeight.Medium
          )
        )
      }

      // Fast DL button
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(999.dp))
          .background(PakSecondary)
          .clickable { onFastDlClick() }
          .padding(horizontal = 8.dp, vertical = 3.dp)
          .testTag("fast_dl_header_button"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(
          text = "Fast DL",
          style = MaterialTheme.typography.labelSmall.copy(
            color = Color.White,
            fontWeight = FontWeight.Bold
          )
        )
        Icon(
          imageVector = Icons.Default.ArrowDownward,
          contentDescription = "Download",
          tint = Color.White,
          modifier = Modifier.size(12.dp)
        )
      }
    }
  }
}
