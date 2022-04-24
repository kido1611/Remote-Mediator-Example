package id.kido1611.example.remotemediator.presenter.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    openMediator: () -> Unit,
    openNetworkBound: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        24.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = openMediator
                ) {
                    Text(text = "Paging 3 - Mediator")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = openNetworkBound
                ) {
                    Text(text = "Network Bound Resource")
                }
            }
        }
    }
}