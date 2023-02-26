package com.etu.booking.compose.screen

import android.Manifest
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.etu.booking.R
import com.etu.booking.compose.component.FailedAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    comeback: () -> Unit
) {
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "We need your permission to give you possibility to photo your documents.\n" +
                stringResource(id = R.string.warning_description),
        comeback = comeback,
    ) {
        CameraView(onImageCaptured = { uri, fromGallery ->
            Log.d("yab", "Image Uri Captured from Camera View")
        }, onError = { Log.d("granted", "fuck")} )
    }
}

@Composable
fun CameraView(onImageCaptured: (Uri, Boolean) -> Unit, onError: (ImageCaptureException) -> Unit) {

    val context = LocalContext.current
    val lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) onImageCaptured(uri, true)
    }

    CameraPreviewView(
        imageCapture,
        lensFacing
    ) { cameraUIAction ->
        when (cameraUIAction) {
            is CameraUIAction.OnCameraClick -> {
                imageCapture.takePicture(context, lensFacing, onImageCaptured, onError)
            }
            is CameraUIAction.OnGalleryViewClick -> {
                if (true == context.getOutputDirectory().listFiles()?.isNotEmpty()) {
                    galleryLauncher.launch("image/*")
                }
            }
        }
    }
}

@Composable
private fun CameraPreviewView(
    imageCapture: ImageCapture,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    cameraUIAction: (CameraUIAction) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember { PreviewView(context) }
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {

        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            CameraControls(cameraUIAction)
        }

    }
}

private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val PHOTO_EXTENSION = ".jpg"

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

fun ImageCapture.takePicture(
    context: Context,
    lensFacing: Int,
    onImageCaptured: (Uri, Boolean) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val outputDirectory = context.getOutputDirectory()
    val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)
    val outputFileOptions = getOutputFileOptions(lensFacing, photoFile)

    this.takePicture(
        outputFileOptions,
        Executors.newSingleThreadExecutor(),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                val mimeType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(savedUri.toFile().extension)
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(savedUri.toFile().absolutePath),
                    arrayOf(mimeType)
                ) { _, uri ->

                }
                onImageCaptured(savedUri, false)
            }
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        })
}


fun getOutputFileOptions(
    lensFacing: Int,
    photoFile: File
): ImageCapture.OutputFileOptions {

    val metadata = ImageCapture.Metadata().apply {
        isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
    }
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
        .setMetadata(metadata)
        .build()

    return outputOptions
}

fun createFile(baseFolder: File, format: String, extension: String) =
    File(
        baseFolder, SimpleDateFormat(format, Locale.US)
            .format(System.currentTimeMillis()) + extension
    )


fun Context.getOutputDirectory(): File {
    val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
        File(it, this.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else this.filesDir
}

@Composable
fun CameraControls(cameraUIAction: (CameraUIAction) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CameraControl(
            Icons.Sharp.Star,
            R.string.app_name,
            modifier= Modifier
                .size(64.dp)
                .padding(1.dp)
                .border(1.dp, Color.White, CircleShape),
            onClick = { cameraUIAction(CameraUIAction.OnCameraClick) }
        )

        CameraControl(
            Icons.Sharp.Star,
            R.string.address,
            modifier= Modifier.size(64.dp),
            onClick = { cameraUIAction(CameraUIAction.OnGalleryViewClick) }
        )

    }
}


@Composable
fun CameraControl(
    imageVector: ImageVector,
    contentDescId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector,
            contentDescription = stringResource(id = contentDescId),
            modifier = modifier,
            tint = Color.White
        )
    }

}

sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    object OnGalleryViewClick : CameraUIAction()
}

@ExperimentalPermissionsApi
@Composable
fun Permission(
    permission: String = Manifest.permission.CAMERA,
    rationale: String = "This permission is important for this app. Please grant the permission.",
    comeback: () -> Unit,
    content: @Composable () -> Unit = { }
) {
    val permissionState = rememberPermissionState(permission)
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            Rationale(
                text = rationale,
                onRequestPermission = {
                    permissionState.launchPermissionRequest()
                },
                comeback = comeback,
            )
        },
        permissionNotAvailableContent = {
            FailedAction(text = "Permission was not granted", onClick = comeback)
        },
        content = content
    )
}

@Composable
private fun Rationale(
    text: String,
    comeback: () -> Unit,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = comeback,
        title = {
            Text(text = "Permission request")
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(onClick = onRequestPermission) {
                Text("Ok")
            }
        }
    )
}
