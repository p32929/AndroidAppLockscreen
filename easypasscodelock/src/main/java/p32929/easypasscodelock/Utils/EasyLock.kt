package p32929.easypasscodelock.Utils

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import p32929.easypasscodelock.Activities.LockscreenActivity
import java.security.MessageDigest
import java.security.SecureRandom

/** Entry point of the library. Every method works from Kotlin and Java. */
object EasyLock {
    internal const val KEY_PASSWORD = "password"
    internal const val EXTRA_STATUS = "passStatus"
    internal const val EXTRA_NEXT = "nextActivity"

    @ColorInt
    @JvmStatic
    var backgroundColor: Int? = null
        private set

    @JvmStatic
    var onClickListener: View.OnClickListener? = null
        private set

    @StyleRes
    internal var themeRes: Int = 0
    internal var biometricEnabled = false
    internal var wentToBackground = false
    private var observing = false

    internal fun init(context: Context) {
        EasylockSP.init(context)
        if (!observing) {
            observing = true
            ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onStop(owner: LifecycleOwner) {
                    wentToBackground = true
                }
            })
        }
    }

    /** Asks for a new passcode twice and saves it. Opens [activityClassToGo] afterwards, or just closes if it is null. */
    @JvmStatic
    @JvmOverloads
    fun setPassword(context: Context, activityClassToGo: Class<*>? = null) = open(context, "set", activityClassToGo)

    /** Asks for the current passcode, then a new one twice. */
    @JvmStatic
    @JvmOverloads
    fun changePassword(context: Context, activityClassToGo: Class<*>? = null) = open(context, "change", activityClassToGo)

    /** Asks for the current passcode, then turns the lock off. */
    @JvmStatic
    @JvmOverloads
    fun disablePassword(context: Context, activityClassToGo: Class<*>? = null) = open(context, "disable", activityClassToGo)

    /** Shows the lock screen if a passcode is set. Call it in onCreate of your first activity. */
    @JvmStatic
    fun checkPassword(context: Context) {
        init(context)
        wentToBackground = false
        if (isPasswordSet(context)) open(context, "check", null)
    }

    @JvmStatic
    fun isPasswordSet(context: Context): Boolean {
        init(context)
        return EasylockSP.getString(KEY_PASSWORD, null) != null
    }

    /** Optional. By default the lock screen uses the colours of its theme. */
    @JvmStatic
    fun setBackgroundColor(@ColorInt backgroundColor: Int) {
        this.backgroundColor = backgroundColor
    }

    /** Shows a "Forgot passcode?" button that runs [onClickListener]. Pass null to hide it. */
    @JvmStatic
    fun forgotPassword(onClickListener: View.OnClickListener?) {
        this.onClickListener = onClickListener
    }

    /** Lets people unlock with fingerprint or face, when the phone supports it. Off by default. */
    @JvmStatic
    fun setBiometricEnabled(enabled: Boolean) {
        biometricEnabled = enabled
    }

    /** Use your own Material 3 theme for the lock screen. Without it, the lock screen uses Material 3 DayNight with the phone's dynamic colours. */
    @JvmStatic
    fun setTheme(@StyleRes themeRes: Int) {
        this.themeRes = themeRes
    }

    private fun open(context: Context, status: String, next: Class<*>?) {
        init(context)
        val intent = Intent(context, LockscreenActivity::class.java)
            .putExtra(EXTRA_STATUS, status)
            .putExtra(EXTRA_NEXT, next?.name)
        if (context !is android.app.Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    // Passcodes are saved as a salted SHA-256 hash. 1.x saved them as plain text: those still unlock and get re-saved hashed.
    internal fun save(passcode: String) {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }.toHex()
        EasylockSP.put(KEY_PASSWORD, "sha256:$salt:${hash(salt, passcode)}")
    }

    internal fun matches(passcode: String): Boolean {
        val saved = EasylockSP.getString(KEY_PASSWORD, null) ?: return false
        if (!saved.startsWith("sha256:")) {
            return (saved == passcode).also { if (it) save(passcode) }
        }
        val (_, salt, digest) = saved.split(":")
        return MessageDigest.isEqual(hash(salt, passcode).toByteArray(), digest.toByteArray())
    }

    internal fun clear() = EasylockSP.put(KEY_PASSWORD, null)

    private fun hash(salt: String, passcode: String) =
        MessageDigest.getInstance("SHA-256").digest("$salt$passcode".toByteArray()).toHex()

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}
