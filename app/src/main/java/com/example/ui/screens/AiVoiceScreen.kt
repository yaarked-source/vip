package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CivicAiMessage
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AiVoiceScreen(
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val listState = rememberLazyListState()

  var inputText by remember { mutableStateOf("") }
  var isSpeaking by remember { mutableStateOf(false) }

  val messages = remember {
    mutableStateListOf(
      CivicAiMessage(
        id = "1",
        sender = "copilot",
        text = "Salam! I am the PakPulse Civic Telemetry AI. How can I assist you with air quality readings, smog precautions, or provincial advisories today?",
        urduSummary = "السلام علیکم! میں پاک پلس سوک ٹیلی میٹری اے آئی ہوں۔ میں آپ کی سموگ اور موسم سے متعلق کیا مدد کر سکتا ہوں؟",
        timestamp = "Just now"
      )
    )
  }

  val promptSuggestions = listOf(
    "Is it safe to run outdoors in Lahore today?",
    "What is the difference between N95 and surgical mask for smog?",
    "Are schools closed tomorrow due to AQI?",
    "How do I report illegal stubble burning to EPA?",
    "Explain US-AQI 284 vs PM2.5 density"
  )

  fun handleSend(query: String) {
    if (query.isBlank()) return
    messages.add(
      CivicAiMessage(
        id = System.currentTimeMillis().toString(),
        sender = "user",
        text = query,
        timestamp = "Just now"
      )
    )
    inputText = ""

    coroutineScope.launch {
      listState.animateScrollToItem(messages.size - 1)
    }

    // Generate smart context-aware answer
    val replyText: String
    val urduText: String
    val lower = query.lowercase()

    when {
      lower.contains("run") || lower.contains("jog") || lower.contains("exercise") -> {
        replyText = "Outdoor running is strongly discouraged today in Lahore and central Punjab (AQI 284 - Very Unhealthy). Deep aerobic breathing pulls fine PM2.5 particulates directly into lung tissue. Please exercise indoors with a HEPA filter."
        urduText = "آج کھلی فضا میں دوڑنے یا ورزش سے پرہیز کریں۔ ایئر کوالٹی انڈیکس انتہائی مضر صحت ہے۔"
      }
      lower.contains("mask") -> {
        replyText = "A certified N95 or KN95 respirator is essential. Standard surgical or cloth masks leave large air gaps and fail to block PM2.5 micro-particles. Ensure your N95 forms an airtight seal around your nose and chin."
        urduText = "سموگ سے بچاؤ کے لیے عام کپڑے کے ماسک کے بجائے مستند N95 ماسک کا استعمال کریں۔"
      }
      lower.contains("school") -> {
        replyText = "According to the latest Punjab Education Department bulletin, primary schools across Lahore and Multan divisions start at 8:45 AM. Outdoor morning assemblies and physical education have been temporarily suspended."
        urduText = "پرائمری سکولوں کے اوقات کار صبح 8:45 بجے تک محدود ہیں اور بیرونی سرگرمیاں معطل ہیں۔"
      }
      lower.contains("stubble") || lower.contains("report") || lower.contains("burning") -> {
        replyText = "You can immediately report illegal crop residue or waste burning through the Community tab here in Pakistan Pulse, or by calling Punjab EPA's toll-free hotline at 0800-02345 (Section 144 is currently enforced)."
        urduText = "فصلوں کی باقیات جلانے کی شکایت پنجاب ای پی اے کی ہیلپ لائن 02345-0800 پر درج کرائیں۔"
      }
      else -> {
        replyText = "Current readings across Punjab indicate severe thermal inversion with US-AQI hovering between 250 and 320. Fine PM2.5 density is above 200 µg/m³ (WHO safe threshold is 15 µg/m³). Keep indoor air purifiers running and seal drafty doors."
        urduText = "پنجاب میں شدید تھرمل انورشن کے باعث سموگ کی شرح خطرناک حد تک بڑھ چکی ہے۔ احتیاطی تدابیر اختیار کریں۔"
      }
    }

    messages.add(
      CivicAiMessage(
        id = (System.currentTimeMillis() + 1).toString(),
        sender = "copilot",
        text = replyText,
        urduSummary = urduText,
        timestamp = "Just now"
      )
    )

    coroutineScope.launch {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .padding(bottom = 76.dp)
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(PakGreenPrimaryContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Psychology,
            contentDescription = null,
            tint = PakSecondaryFixed,
            modifier = Modifier.size(22.dp)
          )
        }

        Column {
          Text(
            text = "AI Telemetry Copilot",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = PakGreenPrimary
            )
          )
          Text(
            text = "Real-time Civic Health & Smog Advisor",
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakOnSurfaceVariant
            )
          )
        }
      }

      // Voice read toggle button
      IconButton(
        onClick = { isSpeaking = !isSpeaking },
        modifier = Modifier
          .clip(CircleShape)
          .background(if (isSpeaking) PakSecondaryFixed else MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Icon(
          imageVector = if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
          contentDescription = "Voice readout",
          tint = if (isSpeaking) OnPakSecondaryFixed else PakOnSurfaceVariant
        )
      }
    }

    // Message List
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(messages) { msg ->
        val isUser = msg.sender == "user"

        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
        ) {
          Box(
            modifier = Modifier
              .widthIn(max = 300.dp)
              .clip(
                RoundedCornerShape(
                  topStart = 16.dp,
                  topEnd = 16.dp,
                  bottomStart = if (isUser) 16.dp else 4.dp,
                  bottomEnd = if (isUser) 4.dp else 16.dp
                )
              )
              .background(
                if (isUser) PakGreenPrimaryContainer
                else MaterialTheme.colorScheme.surfaceContainerLowest
              )
              .padding(14.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = msg.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = if (isUser) Color.White else PakOnSurface,
                  lineHeight = 20.sp
                )
              )

              if (msg.urduSummary.isNotEmpty()) {
                Text(
                  text = msg.urduSummary,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = if (isUser) PakSecondaryFixed else PakSecondary,
                    fontWeight = FontWeight.SemiBold
                  )
                )
              }

              Text(
                text = msg.timestamp,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = if (isUser) PakPrimaryFixed.copy(alpha = 0.7f) else PakOnSurfaceVariant,
                  fontSize = 9.sp
                ),
                modifier = Modifier.align(Alignment.End)
              )
            }
          }
        }
      }
    }

    // Quick suggestion prompt chips
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(promptSuggestions) { prompt ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { handleSend(prompt) }
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = prompt,
            style = MaterialTheme.typography.labelSmall.copy(
              color = PakOnSurface,
              fontWeight = FontWeight.Medium
            )
          )
        }
      }
    }

    // Input Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      TextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = { Text("Ask about smog, AQI, or health...", fontSize = 13.sp) },
        modifier = Modifier
          .weight(1f)
          .testTag("ai_query_input"),
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
          unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
      )

      IconButton(
        onClick = { handleSend(inputText) },
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(PakGreenPrimaryContainer)
          .testTag("ai_send_button")
      ) {
        Icon(
          imageVector = Icons.Default.Send,
          contentDescription = "Send",
          tint = Color.White
        )
      }
    }
  }
}
