import android.content.Context
import android.net.Uri
import com.darkrockstudios.libraries.mpfilepicker.MPFile

actual class ByteArrayFactory(private val context: Context) {

    actual suspend fun getByteArray(mpFile: MPFile<Any>): ByteArray? {
        val uri = Uri.parse(mpFile.path)
        val inputStream = context.contentResolver.openInputStream(uri)
        return inputStream.use {
            it?.buffered()?.readBytes()
        }
    }
}