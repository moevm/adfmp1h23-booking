import android.content.Context
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.etu.booking.R
import com.etu.booking.ui.compose.component.PushButton
import com.etu.booking.utils.getOutputDirectory
import com.etu.booking.viewmodel.DocumentViewModel

@Composable
fun DocumentScreen(documentViewModel: DocumentViewModel) {
    val context = LocalContext.current
    val state = rememberLazyGridState()
    val needToShowPermission by documentViewModel.needToShowPermission.collectAsState()

    if (needToShowPermission) {
        AlertDialog(
            onDismissRequest = { documentViewModel.seePermission() },
            title = { Text(text = stringResource(id = R.string.warning_title)) },
            text = { Text(text = stringResource(id = R.string.warning_description)) },
            buttons = {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PushButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.ok),
                        onClick = { documentViewModel.seePermission() }
                    )
                }
            }
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = state,
        content = {
            items(getAllPhotos(context)) {
                ImageCard(file = it)
            }
        }
    )
}

fun getAllPhotos(context: Context): List<String> = context
    .getOutputDirectory().listFiles()?.map { it.path } ?: emptyList()

@Composable
private fun ImageCard(file: String) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = file,
                contentDescription = "",
                modifier = Modifier.padding(10.dp),
            )
            Text(text = "Uploaded: ${file.split("/").last().substring(0, 10)}")
        }
    }
}