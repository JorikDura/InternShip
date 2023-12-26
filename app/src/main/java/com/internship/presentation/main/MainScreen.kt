package com.internship.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.internship.R
import com.internship.presentation.cams.CamsScreen
import com.internship.presentation.doors.DoorsScreen

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 32.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.my_home),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        val tabsItems = listOf(
            R.string.cams,
            R.string.doors
        )

        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabsItems.forEachIndexed { index, titleId ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(text = stringResource(id = titleId))
                    }
                )
            }
        }

        when(selectedTabIndex) {
            0 -> {
                CamsScreen()
            }

            1 -> {
                DoorsScreen()
            }

            else -> {
                Text(text = stringResource(id = R.string.tab_error))
            }
        }

    }
}
