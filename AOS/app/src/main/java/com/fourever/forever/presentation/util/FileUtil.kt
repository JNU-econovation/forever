package com.fourever.forever.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun getMultipartBody(contentUri: Uri, context: Context, fileName: String): MultipartBody.Part {
    val fileUriPath = getFileUriPath(contentUri, context)
    val file = getFile(fileUriPath)

    return getMultipartBody(file, fileName)
}

@SuppressLint("Range")
private fun getFileUriPath(contentUri: Uri, context: Context): String? {
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(contentUri, null, null, null, null)

    cursor?.moveToNext()
    val path = cursor?.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
    cursor?.close()

    return path
}

private fun getFile(fileUriPath: String?): File {
    return File(fileUriPath)
}

private fun getMultipartBody(file: File, fileName: String): MultipartBody.Part {
    val fileRequestBody = file.asRequestBody("application/pdf".toMediaType())

    return MultipartBody.Part.createFormData("file", fileName, fileRequestBody)
}