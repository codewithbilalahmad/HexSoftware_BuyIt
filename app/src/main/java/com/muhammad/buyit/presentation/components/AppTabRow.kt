package com.muhammad.buyit.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.domain.model.tab.TabItem
import kotlinx.coroutines.launch

@Composable
fun AppTabRow(
    tabItems: List<TabItem>, pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val currentIndex = pagerState.currentPage
    SecondaryTabRow(
        selectedTabIndex = currentIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background, indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(currentIndex)
                    .padding(horizontal = 16.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary,
                height = 3.dp
            )
        }
    ) {
        tabItems.forEachIndexed { index, item ->
            val isSelected = index == currentIndex
            val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            Tab(selected = isSelected, onClick = {
                if (currentIndex != index) {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            }, interactionSource = interactionSource, content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = color,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            })
        }
    }
}