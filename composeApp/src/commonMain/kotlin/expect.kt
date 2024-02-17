import com.darkrockstudios.libraries.mpfilepicker.MPFile

expect class ByteArrayFactory {

    suspend fun getByteArray(mpFile: MPFile<Any>): ByteArray?
}