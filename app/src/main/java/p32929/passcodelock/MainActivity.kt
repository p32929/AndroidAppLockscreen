package p32929.passcodelock

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import p32929.easypasscodelock.Utils.EasyLock
import p32929.easypasscodelock.Utils.LockscreenHandler

class MainActivity : LockscreenHandler() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyLock.forgotPassword { Toast.makeText(this, "Ask the app to reset it here", Toast.LENGTH_SHORT).show() }
        if (savedInstanceState == null) EasyLock.checkPassword(this)

        findViewById<MaterialButton>(R.id.set).setOnClickListener { EasyLock.setPassword(this) }
        findViewById<MaterialButton>(R.id.lock).setOnClickListener { EasyLock.checkPassword(this) }
        findViewById<MaterialButton>(R.id.change).setOnClickListener { EasyLock.changePassword(this) }
        findViewById<MaterialButton>(R.id.disable).setOnClickListener { EasyLock.disablePassword(this) }
        findViewById<MaterialSwitch>(R.id.biometric).setOnCheckedChangeListener { _, on -> EasyLock.setBiometricEnabled(on) }
    }

    override fun onResume() {
        super.onResume()
        val on = EasyLock.isPasswordSet(this)
        findViewById<TextView>(R.id.status).text = if (on) "Passcode is on" else "No passcode set"
        findViewById<MaterialButton>(R.id.set).isEnabled = !on
        listOf(R.id.lock, R.id.change, R.id.disable).forEach { findViewById<MaterialButton>(it).isEnabled = on }
    }
}
