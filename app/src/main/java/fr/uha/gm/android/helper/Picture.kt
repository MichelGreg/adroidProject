package fr.uha.gm.android.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class Picture {

    private var photoFile: File? = null

    fun createPhotoFile(context: Context) : Uri? {
        val filename = createUniqueName("photo", "jpg")
        val folder = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (folder != null) {
            photoFile = File(folder, filename)
            return FileProvider.getUriForFile(context, FILE_PROVIDER, photoFile!!)
        } else {
            photoFile = null
            return null
        }
    }

    fun getPhotoPath(): String? {
        if (photoFile == null) return null;
        return photoFile!!.getAbsolutePath()
    }

    companion object {
        val TAG = Picture::class.java.simpleName

        private fun createUniqueName(prefix: String, extension: String?): String {
            val tmp = StringBuilder()
            tmp.append(prefix)
            tmp.append("-")
            val formatter = SimpleDateFormat("yyyyMMdd_HHmmss")
            tmp.append(formatter.format(Calendar.getInstance().time))
            if (extension != null) {
                tmp.append(".")
                tmp.append(extension)
            }
            return tmp.toString()
        }

        private const val FILE_PROVIDER: String = "fr.uha.hassenforder.teams.fileprovider"
        private fun getSimpleName(path: String): String {
            val lastSegmentPosition = path.lastIndexOf('/')
            return if (lastSegmentPosition != -1) {
                val extensionPosition = path.lastIndexOf('.')
                if (extensionPosition != -1) {
                    path.substring(lastSegmentPosition + 1, extensionPosition)
                } else {
                    path.substring(lastSegmentPosition + 1)
                }
            } else {
                path
            }
        }

        fun addToGallery(context: Context, photoPath: String?): Uri? {
            /*
            if (photoPath == null) return null
            Log.d(TAG, photoPath)
            val name = getSimpleName(photoPath)
            Log.d(TAG, name)
            val values = ContentValues(5)
            val current = System.currentTimeMillis()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, name)
            values.put(MediaStore.Images.Media.DATE_ADDED, (current / 1000).toInt())
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + getSimpleName(photoPath) + ".jpg")
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
            val imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val fos = imageUri?.let { context.contentResolver.openInputStream(it) }
            if (fos == null) {
                context.contentResolver.delete(imageUri!!, null, null)
            } else {
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(imageUri, values, null, null)
            }
            return imageUri
             */
            return null
        }

        private fun getInputStream(context: Context, filename: String): InputStream? {
            try {
                 return FileInputStream(File(filename))
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
        }

        fun getBitmapFromUri(context: Context, filename: String?, targetW: Int, targetH: Int): Bitmap? {
            try {
                if (filename == null) return null
                // Get the dimensions of the bitmap
                val bmOptions = BitmapFactory.Options()
                bmOptions.inJustDecodeBounds = true
                val inputStream = getInputStream(context, filename)
                if (inputStream == null) return null
                BitmapFactory.decodeStream(inputStream, null, bmOptions)
                inputStream.close()
                val photoW = bmOptions.outWidth
                val photoH = bmOptions.outHeight

                // Determine how much to scale down the image
                val scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false
                bmOptions.inSampleSize = scaleFactor
                val inputStream2 = getInputStream(context, filename)
                if (inputStream2 == null) return null
                val bitmap = BitmapFactory.decodeStream(inputStream2, null, bmOptions)
                inputStream2.close()
                return bitmap
            } catch (e: Exception) {
            }
            return null
        }
    }
}