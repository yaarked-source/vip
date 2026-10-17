package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = PakGreenPrimary,
  onPrimary = OnPakGreenPrimary,
  primaryContainer = PakGreenPrimaryContainer,
  onPrimaryContainer = OnPakGreenPrimaryContainer,
  secondary = PakSecondary,
  onSecondary = OnPakSecondary,
  secondaryContainer = PakSecondaryContainer,
  onSecondaryContainer = OnPakSecondaryContainer,
  tertiary = PakTertiary,
  tertiaryContainer = PakTertiaryFixedDim,
  onTertiaryContainer = OnPakTertiaryContainer,
  error = PakError,
  onError = OnPakError,
  errorContainer = PakErrorContainer,
  onErrorContainer = OnPakErrorContainer,
  background = PakBackground,
  onBackground = PakOnSurface,
  surface = PakSurface,
  onSurface = PakOnSurface,
  surfaceVariant = PakSurfaceContainerHighest,
  onSurfaceVariant = PakOnSurfaceVariant,
  outline = PakOutline,
  outlineVariant = PakOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
  primary = PakSecondaryFixedDim,
  onPrimary = PakGreenPrimary,
  primaryContainer = PakGreenPrimaryContainer,
  onPrimaryContainer = PakPrimaryFixed,
  secondary = PakSecondaryFixed,
  onSecondary = OnPakSecondaryFixed,
  secondaryContainer = Color(0xFF005236),
  onSecondaryContainer = PakSecondaryFixedDim,
  tertiary = PakTertiaryFixedDim,
  error = Color(0xFFFFB4AB),
  errorContainer = PakErrorContainer,
  background = Color(0xFF0F1523),
  onBackground = Color(0xFFE2E7FF),
  surface = Color(0xFF0F1523),
  onSurface = Color(0xFFE2E7FF),
  surfaceVariant = Color(0xFF1E283F),
  onSurfaceVariant = Color(0xFFC0C9BE),
  outline = PakOutline
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
