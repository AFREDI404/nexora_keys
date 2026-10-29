package com.nexora.keys

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val layout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setPadding(32, 32, 32, 32)
        setBackgroundColor(0xFF101820.toInt())
    }

    val title = TextView(this).apply {
        text = "Nexora Keys"
        textSize = 30f
        setTextColor(0xFFFFFFFF.toInt())
        gravity = Gravity.CENTER
    }

    val subtitle = TextView(this).apply {
        text = "Your keyboard, your language."
        textSize = 16f
        setTextColor(0xFFCCCCCC.toInt())
        gravity = Gravity.CENTER
    }

    val enableButton = Button(this).apply {
        text = "Enable Nexora Keys"
        setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }
    }

    val chooseButton = Button(this).apply {
        text = "Choose Keyboard"
        setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE)
                as android.view.inputmethod.InputMethodManager
            imm.showInputMethodPicker()
        }
    }

    layout.addView(title, matchWrap())
    layout.addView(subtitle, matchWrap())
    layout.addView(enableButton, matchWrap())
    layout.addView(chooseButton, matchWrap())

    setContentView(layout)
}

private fun matchWrap(): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 16, 0, 16)
    }
}

}
