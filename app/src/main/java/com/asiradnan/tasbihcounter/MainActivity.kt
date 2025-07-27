package com.asiradnan.tasbihcounter

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asiradnan.tasbihcounter.components.DisplayWithProgressBorder
import com.asiradnan.tasbihcounter.ui.theme.TasbihCounterTheme
import com.asiradnan.tasbihcounter.ui.theme.primaryLight
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onLeftHandToggle: () -> Unit,
    leftHandEnabled: Boolean,
    darkEnabled: Boolean,
    darkEnabledToggle: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text("Tasbih Counter")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.tasbih_outlined),
                    contentDescription = "Moon icon as logo"
                )
            }
        },
        actions = {
            Box() {
                var expanded by remember { mutableStateOf(false) }
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings icon"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Left Hand Mode")

                        },
                        onClick = { },
                        trailingIcon = {
                            Switch(
                                checked = leftHandEnabled,
                                onCheckedChange = { onLeftHandToggle() })
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Dark Mode")

                        },
                        onClick = { },
                        trailingIcon = {
                            Switch(
                                checked = darkEnabled,
                                onCheckedChange = { darkEnabledToggle() })
                        }
                    )
                }
            }
        },
    )
}


@Composable
fun DuaDropDown(
    modifier: Modifier = Modifier,
    itemPosition: Int,
    changeItemPosition: (idx: Int) -> Unit
) {
//    var itemPosition by rememberSaveable { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val duas = listOf(
        "General",
        "SubhanAllah - Glory be to Allah",
        "Alhamdulillah - Praise be to Allah",
        "AllahuAkbar - Allah is the Greatest"
    )
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f),
    ) {
        OutlinedCard(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.outlinedCardElevation(2.dp),
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .padding(vertical = 6.dp, horizontal = 16.dp)
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
            ) {

                Text(text = duas[itemPosition], style = MaterialTheme.typography.titleSmall)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = modifier.size(36.dp),

                    )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier.fillMaxWidth(0.8f)
        ) {

            duas.forEachIndexed { idx, dua ->
                DropdownMenuItem(
                    text = { Text(text = dua) },
                    onClick = {
                        changeItemPosition(idx)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Display(
    modifier: Modifier = Modifier,
    count: Int,
    currentProgress: Float,
    showProgressBar: Boolean = false
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 4.dp)
    ) {
        val animatedProgress by animateFloatAsState(
            targetValue = currentProgress.coerceIn(0f, 1f),
            animationSpec = tween(durationMillis = 250),
            label = "linearProgressAnimation"
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = true)
                    )
                },
                label = "animatedCounterText"
            ) { targetCount ->
                Text(
                    text = targetCount.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
            if (showProgressBar) {
                Spacer(modifier = modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = modifier.fillMaxWidth(0.8f),
                )
                Spacer(modifier = modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun DecrementButton(modifier: Modifier = Modifier, count: Int, onDecrement: () -> Unit) {
    val scale = remember { Animatable(1f) } // Initial scale
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            onDecrement()
            coroutineScope.launch {
                // Scale up
                scale.animateTo(
                    targetValue = 0.95f,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = LinearEasing
                    )
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = LinearEasing
                    )
                )
            }
        },
        modifier = modifier
            .aspectRatio(1f)
            .scale(scale.value),
        shape = CircleShape,
        enabled = count > 0,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
    ) {
        Icon(
            imageVector = Icons.Filled.Remove,
            contentDescription = null,
            modifier = modifier.fillMaxSize(0.75f)
        )
    }
}

@Composable
fun IncrementButton(modifier: Modifier = Modifier, onIncrement: () -> Unit) {
    val scale = remember { Animatable(1f) } // Initial scale
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            onIncrement()
            coroutineScope.launch {
                // Scale up
                scale.animateTo(
                    targetValue = 0.95f,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = LinearEasing
                    )
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = LinearEasing
                    )
                )
            }
        },
        modifier = modifier
            .aspectRatio(1f)
            .scale(scale.value),
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = modifier.fillMaxSize(0.75f)
        )
    }
}

@Composable
fun RightHandButtons(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = modifier.weight(0.4f))
        DecrementButton(modifier = modifier.weight(1.5f), count = count, { onDecrement() })
        Spacer(modifier = modifier.weight(0.4f))
        IncrementButton(modifier = modifier.weight(2.75f), onIncrement = { onIncrement() })
        Spacer(modifier = modifier.weight(0.4f))
    }
}

@Composable
fun LeftHandButtons(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = modifier.weight(0.4f))
        IncrementButton(modifier = modifier.weight(2.75f), onIncrement = { onIncrement() })
        Spacer(modifier = modifier.weight(0.4f))
        DecrementButton(modifier = modifier.weight(1.5f), count = count, { onDecrement() })
        Spacer(modifier = modifier.weight(0.4f))
    }
}

@Composable
fun ResetButton(modifier: Modifier = Modifier, onReset: () -> Unit) {
    OutlinedButton(
        onClick = { onReset() },
        modifier = modifier
            .fillMaxWidth(0.8f),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),

        ) {
        Icon(
            imageVector = Icons.Outlined.Cached,
            contentDescription = null,
            Modifier.size(30.dp)
        )
        Text(
            text = "Reset Counter",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(10.dp)
        )
    }
}


@Composable
fun MainScreen() {
    var count by rememberSaveable { mutableStateOf(0) }
    var leftHandEnabled by rememberSaveable { mutableStateOf(false) }
    var itemPosition by rememberSaveable { mutableStateOf(0) }
    var userThemePreference by rememberSaveable { mutableStateOf(false) }
    val hapticFeedback: HapticFeedback = LocalHapticFeedback.current
    val mediaPlayer = remember { MediaPlayer() }
    val context = LocalContext.current
    TasbihCounterTheme(darkTheme = userThemePreference) {
        Scaffold(
            topBar = {
                TopBar(
                    Modifier,
                    onLeftHandToggle = { leftHandEnabled = !leftHandEnabled },
                    leftHandEnabled = leftHandEnabled,
                    darkEnabled = userThemePreference,
                    darkEnabledToggle = { userThemePreference = !userThemePreference }
                )
            }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding)
//            verticalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(modifier = Modifier.weight(0.05f))
                DuaDropDown(
                    itemPosition = itemPosition,
                    changeItemPosition = { idx ->
                        if (idx != itemPosition) count = 0; itemPosition = idx
                    })
                Spacer(modifier = Modifier.weight(0.03f))
                Display(
                    modifier = Modifier,
                    count = count,
                    currentProgress = if (itemPosition > 0) (count / 33f) else 0f,
                    showProgressBar = itemPosition > 0
                )
//                DisplayWithProgressBorder(modifier = Modifier, count, progress = if (itemPosition > 0) (count%34).toFloat() / 33f else 0f)
                Spacer(modifier = Modifier.weight(0.25f))
                if (leftHandEnabled)
                    LeftHandButtons(
                        modifier = Modifier,
                        count,
                        onIncrement = {
                            count++
                            if (itemPosition > 0 && (count == 32 || count == 0)) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (itemPosition > 0 && count == 33 ) notify(mediaPlayer, context)
                        },
                        onDecrement = { count-- }
                    )
                else
                    RightHandButtons(
                        modifier = Modifier,
                        count,
                        onIncrement = {
                            count++
                            if (itemPosition > 0 && (count == 32 || count == 0)) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (itemPosition > 0 && count == 33 ) notify(mediaPlayer, context)
                        },
                        onDecrement = { count-- }
                    )
                Spacer(modifier = Modifier.weight(0.1f))
                ResetButton(modifier = Modifier, onReset = { count = 0 })
                Spacer(modifier = Modifier.weight(0.06f))
            }
        }

    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun PreviewScreen() {
    MainScreen()
}

fun notify(mediaPlayer: MediaPlayer, context: Context) {
    val notificationSoundUri: Uri? =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    if (notificationSoundUri != null) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset() // Reset for reuse
        }
        mediaPlayer.setDataSource(context, notificationSoundUri)
        mediaPlayer.prepareAsync() // Prepare asynchronously
        mediaPlayer.setOnPreparedListener { mp ->
            mp.setVolume(0.025f, 0.025f)
            mp.start()
        }
        mediaPlayer.setOnCompletionListener { mp ->
            mp.reset() // Reset after completion for next play
        }
        mediaPlayer.setOnErrorListener { _, _, _ ->
            // Handle error, maybe log it
            mediaPlayer.reset()
            true
        }
    }
}

