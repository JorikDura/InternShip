package com.internship.presentation.cams

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.internship.R
import com.internship.domain.model.Camera
import com.internship.presentation.util.ImageFromRemote
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CameraItem(
    camera: Camera,
    eventListener: (CamsScreenEvents) -> Unit
) {
    val density = LocalDensity.current
    val defaultActionSize = 40.dp
    val endActionSizePx = with(density) { (defaultActionSize * 2).toPx() }
    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
                DragAnchors.Center at 0f
                DragAnchors.End at endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .height(325.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(defaultActionSize * 2)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            OutlinedIconButton(
                border = BorderStroke(1.dp, Color.Black.copy(0.05f)),
                onClick = {
                    if (camera.isFavourite) {
                        eventListener(CamsScreenEvents.UnFavourite(camera.id))
                    } else {
                        eventListener(CamsScreenEvents.Favourite(camera.id))
                    }
                }
            ) {
                AnimatedContent(
                    targetState = camera.isFavourite,
                    label = "starFavouriteAnimation",
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) { favourite ->
                    if (favourite) {
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = null,
                            tint = colorResource(id = R.color.brown)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.star_outfilled),
                            contentDescription = null,
                            tint = colorResource(id = R.color.brown)
                        )
                    }
                }
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = -state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal, reverseDirection = true),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(250.dp)
            ) {
                camera.image?.let { imageUrl ->
                    ImageFromRemote(imageUrl = imageUrl, description = camera.name)
                }

                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.play),
                    tint = Color.White,
                    contentDescription = null
                )

                if (camera.isRec) {
                    Icon(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp)
                            .size(24.dp)
                            .align(Alignment.TopStart),
                        painter = painterResource(id = R.drawable.rec),
                        tint = Color.Red,
                        contentDescription = null
                    )
                }

                this@ElevatedCard.AnimatedVisibility(
                    modifier = Modifier
                        .padding(top = 4.dp, end = 4.dp)
                        .size(24.dp)
                        .align(Alignment.TopEnd), visible = camera.isFavourite
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        tint = colorResource(id = R.color.brown),
                        contentDescription = null
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    text = camera.name
                )
            }
        }
    }

}

enum class DragAnchors {
    Center,
    End,
}