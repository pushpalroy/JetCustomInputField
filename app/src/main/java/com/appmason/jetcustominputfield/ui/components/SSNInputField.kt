package com.appmason.jetcustominputfield.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SSNInputField(
    modifier: Modifier,
    ssn: MutableState<TextFieldValue>,
    shape: Shape = RoundedCornerShape(4.dp),
    textStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp
    )
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, shape)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // Placeholder text that updates as the user types
        val displayText = formatAsPlaceholder(ssn.value.text)
        Text(
            text = displayText,
            style = textStyle.copy(color = Color.LightGray),
            modifier = Modifier.padding(start = 12.dp, top = 12.dp)
        )

        BasicTextField(
            value = ssn.value,
            onValueChange = { newValue ->
                if (newValue.text.filter { it.isDigit() }.length <= 9) {
                    ssn.value = newValue
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            visualTransformation = SSNVisualTransformation,
            textStyle = textStyle,
            modifier = Modifier
                .clip(shape)
                .padding(12.dp)
        )
    }
}

private fun formatAsPlaceholder(input: String): String {
    val placeholder = "XXX - XX - XXXX"
    val inputDigits = input.filter { it.isDigit() }

    return buildString {
        var digitIndex = 0
        placeholder.forEach { c ->
            if (c == ' ' || c == '-') {
                append(c)
            } else if (digitIndex < inputDigits.length) {
                append(inputDigits[digitIndex])
                digitIndex++
            } else {
                append(c)
            }
        }
    }
}


private val SSNVisualTransformation = VisualTransformation { text ->
    val transformedText = StringBuilder()

    text.text.forEachIndexed { index, c ->
        if (index == 3 || index == 5) {
            transformedText.append(" - ") // Add spaces around the hyphen
        }
        if (index < 9) transformedText.append(c)
    }

    val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset <= 3 -> offset
                offset <= 5 -> offset + 3 // Adjust for the extra spaces and hyphen
                else -> offset + 6 // Adjust for both sets of extra spaces and hyphens
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset <= 6 -> offset / 2
                offset <= 12 -> (offset - 3) / 2
                else -> (offset - 6) / 2
            }
        }
    }

    TransformedText(
        AnnotatedString(transformedText.toString()),
        offsetMapping
    )
}