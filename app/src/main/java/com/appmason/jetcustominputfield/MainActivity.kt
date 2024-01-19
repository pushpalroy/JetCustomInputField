package com.appmason.jetcustominputfield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.appmason.jetcustominputfield.ui.components.SSNInputField
import com.appmason.jetcustominputfield.ui.theme.JetCustomInputFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCustomInputFieldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    // State to hold the SSN value
                    val ssnValue = remember { mutableStateOf(TextFieldValue()) }

                    SSNInputField(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        ssn = ssnValue
                    )
                }
            }
        }
    }
}

