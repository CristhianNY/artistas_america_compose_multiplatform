package support

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun HoverableButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isHoveredInitially: Boolean = false
) {
    var isHovered by remember { mutableStateOf(isHoveredInitially) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isHovered) Color(0xFF1585FF) else Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            .detectHover(onEnter = { isHovered = true },
                onExit = { isHovered = false })
    ) {
        Text(
            text = buttonText,
            color = if (isHovered) Color.White else Color.Blue,
            modifier = Modifier.padding(12.dp) // Ajusta el padding del texto
        )
    }
}

fun Modifier.detectHover(
    onEnter: () -> Unit,
    onExit: () -> Unit
): Modifier = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            if (event.type == PointerEventType.Enter) {
                onEnter()
            } else if (event.type == PointerEventType.Exit) {
                onExit()
            }
        }
    }
}