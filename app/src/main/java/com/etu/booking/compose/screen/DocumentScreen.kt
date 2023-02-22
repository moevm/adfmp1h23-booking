import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.default.DefaultModels.PASSPORT_IMAGES

@Composable
fun DocumentScreen() {
    val state = rememberLazyGridState()
    var open by remember { mutableStateOf(true) }
    if (open) {
        AlertDialog(
            onDismissRequest = {
                open = false
            },
            title = { Text(text = stringResource(id = R.string.warning_title)) },
            text = { Text(text = stringResource(id = R.string.warning_description)) },
            buttons = {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { open = false }
                    ) {
                        Text(
                            text = stringResource(id = R.string.warning_button),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = state,
        content = {
            items(PASSPORT_IMAGES) {
                ImageCard(id = it)
            }
        }
    )
}

@Composable
fun ImageCard(id: Int) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = id),
                contentDescription = null,
                modifier = Modifier.padding(10.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "Name: Passport")
            Text(text = "Uploaded: 01.01.2023")
        }
    }
}