package com.example.bookfinderapp.presentation.details

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun DetailsScreen(
    workId: String,
    viewModel: DetailsViewModel,
    navController: NavHostController
) {
    val bookDetails by viewModel.bookDetails.collectAsState()
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )
    LaunchedEffect(workId) {
        viewModel.loadBookDetails(workId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (bookDetails == null) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Animated Rotating Book Cover
                    RotatingBookCover(imageUrl = bookDetails!!.coverUrl)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = bookDetails!!.title,
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Author: ${bookDetails!!.author}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Published: ${bookDetails?.publishYear ?: "N/A"}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = bookDetails!!.description)
                }
            }
        }
        }
    }

    @Composable
    fun RotatingBookCover(imageUrl: String?) {
        var rotated by remember { mutableStateOf(false) }

        val rotation by animateFloatAsState(
            targetValue = if (rotated) 360f else 0f,
            animationSpec = tween(durationMillis = 2000) // 2 seconds rotation
        )

        AsyncImage(
            model = imageUrl,
            contentDescription = "Book Cover",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
                .graphicsLayer {
                    rotationY = rotation
                }
                .clickable { rotated = !rotated }, // Tap to trigger animation
            contentScale = ContentScale.Crop
        )
    }


