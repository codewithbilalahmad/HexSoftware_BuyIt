package com.muhammad.buyit.presentation.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.components.AppTabRow
import com.muhammad.buyit.presentation.navigation.BottomNavigationBar
import com.muhammad.buyit.presentation.screens.orders.components.OrderCard
import com.muhammad.domain.model.tab.TabItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OrdersScreen(
    navHostController: NavHostController,
    viewModel: OrdersViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabItems = OrderFilter.entries.map { filter ->
        TabItem(label = filter.name)
    }
    val pagerState = rememberPagerState { tabItems.size }
    LaunchedEffect(pagerState.currentPage) {
        val filter = OrderFilter.entries[pagerState.currentPage]
        viewModel.onAction(OrdersAction.OnOrderFilter(filter.name))
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.orders))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
            AppTabRow(tabItems = tabItems, pagerState = pagerState)
        }
    }, bottomBar = {
        BottomNavigationBar(navController = navHostController)
    }) { paddingValues ->
        HorizontalPager(
            state = pagerState, verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isOrdersLoading ->{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        ContainedLoadingIndicator()
                    }
                }
                state.ordersError != null ->{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                            contentDescription = null, modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = state.ordersError!!,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                state.orders.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.order_outlined),
                            contentDescription = null, modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = stringResource(R.string.no_orders_found),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                else  -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.orders, key = { it.id }) { order ->
                            OrderCard(order = order)
                        }
                    }
                }
            }
        }
    }
}