import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.ImageCarousel
import com.example.common.MainTextContent
import com.example.common.SearchTextField

@Composable
actual fun PlatformSpecificMainContent() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageCarousel(
            modifier = Modifier.weight(1f).height(400.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        MainTextContent(
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    SearchTextField()
}