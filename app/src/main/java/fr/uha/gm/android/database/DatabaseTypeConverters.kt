package fr.uha.gm.android.database
/*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*

object DatabaseTypeConverters {
    @TypeConverter
    fun long2Date(time: Long): Date? {
        return if (time == -1L) null else Date(time)
    }

    @TypeConverter
    fun date2Long(date: Date?): Long {
        return date?.time ?: -1
    }

    @TypeConverter
    fun toBitmap(content: ByteArray?): Bitmap? {
        return if (content == null) null else BitmapFactory.decodeByteArray(content, 0, content.size)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
*/
