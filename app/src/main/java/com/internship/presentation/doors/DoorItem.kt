package com.internship.presentation.doors

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.internship.R
import com.internship.domain.model.Door
import com.internship.presentation.cams.DragAnchors
import com.internship.presentation.util.ImageFromRemote
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DoorItem(
    door: Door,
    eventListener: (DoorScreenEvents) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current
    val defaultActionSize = 45.dp
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
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .width(defaultActionSize * 2)
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedIconButton(
                modifier = Modifier,
                border = BorderStroke(1.dp, Color.Black.copy(0.05f)),
                onClick = {
                    openDialog = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    tint = colorResource(id = R.color.blue)
                )

            }
            OutlinedIconButton(
                modifier = Modifier,
                border = BorderStroke(1.dp, Color.Black.copy(0.05f)),
                onClick = {
                    if (door.isFavourite) {
                        eventListener(DoorScreenEvents.UnFavourite(door.id))
                    } else {
                        eventListener(DoorScreenEvents.Favourite(door.id))
                    }
                }
            ) {
                AnimatedContent(targetState = door.isFavourite, label = "") { isFavourite ->
                    if (isFavourite) {
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
            door.image?.let { imageUrl ->
                Box(
                    modifier = Modifier
                        .height(250.dp)
                ) {
                    ImageFromRemote(imageUrl = imageUrl, description = door.name)

                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.play),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .height(75.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = door.name
                )
                IconButton(
                    onClick = {
                        if (door.isOpened) {
                            eventListener(DoorScreenEvents.Close(door.id))
                        } else {
                            eventListener(DoorScreenEvents.Open(door.id))
                        }
                    }
                ) {
                    AnimatedContent(
                        targetState = door.isOpened,
                        label = "isOpenedDoorAnimation",
                    ) { isOpened ->
                        if (isOpened) {
                            Icon(
                                painter = painterResource(id = R.drawable.lockoff),
                                contentDescription = null,
                                tint = colorResource(id = R.color.blue)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.lockon),
                                contentDescription = null,
                                tint = colorResource(id = R.color.blue)
                            )
                        }
                    }
                }
            }
        }

    }

    var text by remember {
        mutableStateOf("")
    }
    //Dialog
    if (openDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                openDialog = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "${stringResource(id = R.string.current_title)} ${door.name}")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        label = {
                            Text(text = stringResource(id = R.string.new_title))
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            openDialog = false
                        }) {
                            Text(text = stringResource(id = R.string.cancel), color = Color.Red)
                        }
                        TextButton(onClick = {
                            eventListener(DoorScreenEvents.Edit(door.id, text))
                            text = ""
                            openDialog = false
                        }) {
                            Text(text = stringResource(id = R.string.accept))
                        }
                    }
                }
            }
        }
    }
}