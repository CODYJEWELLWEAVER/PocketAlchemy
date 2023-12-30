package com.android.pocketalchemy.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.pocketalchemy.R

@Preview
@Composable
fun LoginScreen(
    onLoginAttempt: () -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        val isLoggingIn = remember { mutableStateOf(false) }

        Box (
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                )

                if (!isLoggingIn.value) {
                    TextButton(
                        onClick = {
                            // Start showing progress indicator
                            isLoggingIn.value = true
                            // Initiates login
                            onLoginAttempt()
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = stringResource(id = R.string.guest_login_label),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}