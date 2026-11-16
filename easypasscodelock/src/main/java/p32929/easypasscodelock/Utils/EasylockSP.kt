package p32929.easypasscodelock.Utils

import android.content.Context
import android.content.SharedPreferences

/** Small SharedPreferences helper used by the lock screen. Same file name ("Lockscreen") as 1.x, so saved passcodes keep working. */
object EasylockSP {
    @JvmStatic
    lateinit var sharedPreferences: SharedPreferences

    @JvmStatic
    fun init(context: Context) {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences = context.applicationContext.getSharedPreferences("Lockscreen", Context.MODE_PRIVATE)
        }
    }

    @JvmStatic fun put(title: String, value: Boolean) = sharedPreferences.edit().putBoolean(title, value).apply()
    @JvmStatic fun put(title: String, value: Float) = sharedPreferences.edit().putFloat(title, value).apply()
    @JvmStatic fun put(title: String, value: Int) = sharedPreferences.edit().putInt(title, value).apply()
    @JvmStatic fun put(title: String, value: Long) = sharedPreferences.edit().putLong(title, value).apply()
    @JvmStatic fun put(title: String, value: String?) = sharedPreferences.edit().putString(title, value).apply()

    @JvmStatic fun getBoolean(title: String, defaultValue: Boolean) = sharedPreferences.getBoolean(title, defaultValue)
    @JvmStatic fun getFloat(title: String, defaultValue: Float) = sharedPreferences.getFloat(title, defaultValue)
    @JvmStatic fun getInt(title: String, defaultValue: Int) = sharedPreferences.getInt(title, defaultValue)
    @JvmStatic fun getLong(title: String, defaultValue: Long) = sharedPreferences.getLong(title, defaultValue)
    @JvmStatic fun getString(title: String, defaultValue: String?): String? = sharedPreferences.getString(title, defaultValue)

    @JvmStatic fun clearAll() = sharedPreferences.edit().clear().apply()
}
