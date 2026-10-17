package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ApkDirectBanner(
  onInstallClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Bounce animation for download icon
  val infiniteTransition = rememberInfiniteTransition(label = "download_bounce")
  val bounceOffset by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.1f,
    animationSpec = infiniteRepeatable(
      animation = tween(700, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "bounce"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(
        Brush.horizontalGradient(
          colors = listOf(
            PakGreenPrimaryContainer,
            PakSecondary,
            PakGreenPrimaryContainer
          )
        )
      )
      .padding(14.dp)
  ) {
    // Ambient blur circle
    Box(
      modifier = Modifier
        .size(100.dp)
        .align(Alignment.TopEnd)
        .offset(x = 20.dp, y = (-20).dp)
        .clip(CircleShape)
        .background(PakSecondaryContainer.copy(alpha = 0.15f))
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Icon container with bounce
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Download,
            contentDescription = null,
            tint = PakSecondaryFixed,
            modifier = Modifier
              .size(24.dp)
              .scale(bounceOffset)
          )
        }

        Column(
          modifier = Modifier.weight(1f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "Official APK Direct",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            )

            // Version badge
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(PakSecondaryFixed)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "v2.4.1 • 18MB",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = OnPakSecondaryFixed,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 9.sp
                )
              )
            }
          }

          Text(
            text = "High-speed regional mirror • No Google store delays",
            style = MaterialTheme.typography.bodySmall.copy(
              color = PakPrimaryFixed,
              fontSize = 11.sp
            ),
            maxLines = 1
          )

          Spacer(modifier = Modifier.height(2.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.VerifiedUser,
              contentDescription = null,
              tint = PakSecondaryFixedDim,
              modifier = Modifier.size(13.dp)
            )
            Text(
              text = "Verified by Google Play Protect",
              style = MaterialTheme.typography.labelSmall.copy(
                color = PakSecondaryFixedDim,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      // Install button
      Button(
        onClick = onInstallClick,
        colors = ButtonDefaults.buttonColors(
          containerColor = PakSecondaryFixed,
          contentColor = OnPakSecondaryFixed
        ),
        shape = RoundedCornerShape(999.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
        modifier = Modifier.testTag("install_apk_button")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = "Install",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold
            )
          )
          Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
        }
      }
    }
  }
}
