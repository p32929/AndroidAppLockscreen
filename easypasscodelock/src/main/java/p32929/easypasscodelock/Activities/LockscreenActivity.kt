package p32929.easypasscodelock.Activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.MaterialColors
import p32929.easypasscodelock.R
import p32929.easypasscodelock.Utils.EasyLock

class LockscreenActivity : AppCompatActivity() {

    // check | set -> set1 | change -> change1 -> change2 | disable -> disable1
    private var status = "check"
    private var entered = ""
    private var firstEntry = ""

    private lateinit var title: TextView
    private lateinit var hint: TextView
    private lateinit var dots: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        if (EasyLock.themeRes != 0) setTheme(EasyLock.themeRes) else DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.easylock_activity)
        EasyLock.init(this)

        status = intent.getStringExtra(EasyLock.EXTRA_STATUS) ?: "check"
        title = findViewById(R.id.easylock_title)
        hint = findViewById(R.id.easylock_hint)
        dots = findViewById(R.id.easylock_dots)
        EasyLock.backgroundColor?.let { findViewById<View>(R.id.easylock_root).setBackgroundColor(it) }

        buildKeypad()
        setupExtras()
        showStep(
            when (status) {
                "set" -> R.string.enter_a_new_password_txt
                "change", "disable" -> R.string.please_enter_current_password_txt
                else -> R.string.enter_your_password_txt
            }
        )

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // The lock screen can't be skipped: leaving it closes the app.
                if (status == "check") finishAffinity() else finish()
            }
        })
    }

    private fun buildKeypad() {
        val keypad = findViewById<LinearLayout>(R.id.easylock_keypad)
        val rows = listOf(listOf("1", "2", "3"), listOf("4", "5", "6"), listOf("7", "8", "9"), listOf("⌫", "0", "✓"))
        val size = resources.getDimensionPixelSize(R.dimen.easylock_key)
        val gap = resources.getDimensionPixelSize(R.dimen.easylock_gap)
        for (row in rows) {
            val line = LinearLayout(this)
            for (key in row) {
                val style = when (key) {
                    "✓" -> com.google.android.material.R.attr.materialIconButtonFilledStyle
                    "⌫" -> com.google.android.material.R.attr.materialIconButtonStyle
                    else -> com.google.android.material.R.attr.materialButtonStyle
                }
                val button = MaterialButton(this, null, style).apply {
                    insetTop = 0
                    insetBottom = 0
                    cornerRadius = size / 2
                    minWidth = 0
                    minHeight = 0
                    setPadding(0, 0, 0, 0)
                    iconPadding = 0
                    iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
                    when (key) {
                        "⌫" -> {
                            setIconResource(R.drawable.easylock_ic_backspace)
                            iconSize = size / 3
                            contentDescription = getString(R.string.clear_txt)
                            setOnClickListener { delete() }
                            setOnLongClickListener { entered = ""; updateDots(); true }
                        }
                        "✓" -> {
                            setIconResource(R.drawable.easylock_ic_check)
                            iconSize = size / 3
                            contentDescription = getString(R.string.easylock_enter)
                            setOnClickListener { submit() }
                        }
                        else -> {
                            text = key
                            setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_HeadlineSmall)
                            setTextColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSecondaryContainer))
                            backgroundTintList = android.content.res.ColorStateList.valueOf(
                                MaterialColors.getColor(this, com.google.android.material.R.attr.colorSecondaryContainer)
                            )
                            setOnClickListener { type(key) }
                        }
                    }
                }
                line.addView(button, LinearLayout.LayoutParams(size, size).apply { setMargins(gap, gap, gap, gap) })
            }
            keypad.addView(line)
        }
    }

    private fun setupExtras() {
        val forgot = findViewById<MaterialButton>(R.id.easylock_forgot)
        EasyLock.onClickListener?.let { listener ->
            forgot.visibility = View.VISIBLE
            forgot.setOnClickListener(listener)
        }

        val biometric = findViewById<MaterialButton>(R.id.easylock_biometric)
        val canUse = EasyLock.biometricEnabled && status == "check" &&
            BiometricManager.from(this).canAuthenticate(BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
        if (canUse) {
            biometric.visibility = View.VISIBLE
            biometric.setOnClickListener { askBiometric() }
            if (lastNonConfigurationInstance == null) askBiometric()
        }
    }

    private fun askBiometric() {
        val prompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) = unlocked()
        })
        prompt.authenticate(
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.easylock_biometric_title))
                .setNegativeButtonText(getString(R.string.easylock_use_passcode))
                .setAllowedAuthenticators(BIOMETRIC_WEAK)
                .build()
        )
    }

    private fun type(digit: String) {
        dots.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        if (entered.length >= MAX) {
            error(R.string.max_8_characters_txt)
            return
        }
        entered += digit
        updateDots(animateLast = true)
    }

    private fun delete() {
        dots.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        entered = entered.dropLast(1)
        updateDots()
    }

    private fun submit() {
        if (entered.length < MIN) {
            error(R.string.easylock_min_digits)
            return
        }
        val value = entered
        entered = ""
        when (status) {
            "check" -> if (EasyLock.matches(value)) unlocked() else error(R.string.incorrect_password_txt)
            "set", "change1" -> {
                firstEntry = value
                status = if (status == "set") "set1" else "change2"
                showStep(R.string.confirm_password_txt)
            }
            "set1", "change2" -> if (value == firstEntry) {
                EasyLock.save(value)
                done(if (status == "set1") R.string.password_is_set_txt else R.string.password_changed_txt)
            } else {
                status = if (status == "set1") "set" else "change1"
                showStep(R.string.enter_a_new_password_txt)
                error(R.string.please_enter_a_new_password_again_txt)
            }
            "change" -> if (EasyLock.matches(value)) {
                status = "change1"
                showStep(R.string.enter_a_new_password_txt)
            } else error(R.string.incorrect_password_txt)
            "disable" -> if (EasyLock.matches(value)) {
                EasyLock.clear()
                done(R.string.password_disabled_txt)
            } else error(R.string.incorrect_password_txt)
        }
    }

    private fun unlocked() {
        EasyLock.wentToBackground = false
        finish()
    }

    private fun done(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        intent.getStringExtra(EasyLock.EXTRA_NEXT)?.let { startActivity(Intent().setClassName(this, it)) }
        finish()
    }

    private fun showStep(text: Int) {
        title.setText(text)
        hint.text = if (status in listOf("set", "set1", "change1", "change2")) getString(R.string.easylock_hint) else ""
        hint.setTextColor(MaterialColors.getColor(hint, com.google.android.material.R.attr.colorOnSurfaceVariant))
        updateDots()
    }

    private fun error(text: Int) {
        entered = ""
        updateDots()
        hint.setText(text)
        hint.setTextColor(MaterialColors.getColor(hint, androidx.appcompat.R.attr.colorError))
        dots.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        ObjectAnimator.ofFloat(dots, View.TRANSLATION_X, 0f, 24f, -24f, 16f, -16f, 8f, -8f, 0f).setDuration(400).start()
    }

    private fun updateDots(animateLast: Boolean = false) {
        val count = maxOf(MIN, entered.length)
        val size = resources.getDimensionPixelSize(R.dimen.easylock_dot)
        while (dots.childCount < count) {
            dots.addView(View(this).apply { setBackgroundResource(R.drawable.easylock_dot) },
                LinearLayout.LayoutParams(size, size).apply { setMargins(size / 2, 0, size / 2, 0) })
        }
        while (dots.childCount > count) dots.removeViewAt(dots.childCount - 1)
        for (i in 0 until dots.childCount) {
            val dot = dots.getChildAt(i)
            val filled = i < entered.length
            dot.isActivated = filled
            dot.alpha = if (filled) 1f else 0.35f
            if (animateLast && i == entered.length - 1) {
                dot.scaleX = 0.4f
                dot.scaleY = 0.4f
                dot.animate().scaleX(1f).scaleY(1f).setDuration(160).start()
            }
        }
        (dots.parent as ViewGroup).contentDescription = getString(R.string.easylock_digits_entered, entered.length)
    }

    private companion object {
        const val MIN = 4
        const val MAX = 8
    }
}
