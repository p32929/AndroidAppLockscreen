package p32929.easypasscodelock.Utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/** Extend your activities from this. When people come back to your app after leaving it, the lock screen shows again. */
open class LockscreenHandler : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EasyLock.init(this)
    }

    override fun onResume() {
        super.onResume()
        if (EasyLock.wentToBackground) EasyLock.checkPassword(this)
    }
}
