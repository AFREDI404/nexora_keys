package com.nexora.keys

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout

class KeyboardService : InputMethodService() {

private var isBangla = false
private var isSymbols = false

override fun onCreateInputView(): View {
    return createKeyboard()
}

private fun createKeyboard(): View {
    val root = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(4, 6, 4, 6)
        setBackgroundColor(Color.rgb(30, 33, 40))
    }

    val rows = if (isSymbols) {
        listOf(
            listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            listOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
            listOf("*", "\"", "'", ":", ";", "!", "?", "/"),
            listOf("ABC", ",", "🌐", "SPACE", "⌫", "↵")
        )
    } else if (isBangla) {
        listOf(
            listOf("ক", "খ", "গ", "ঘ", "ঙ", "চ", "ছ", "জ", "ঝ", "ঞ"),
            listOf("ট", "ঠ", "ড", "ঢ", "ণ", "ত", "থ", "দ", "ধ"),
            listOf("ন", "প", "ফ", "ব", "ভ", "ম", "য", "র", "ল"),
            listOf("ABC", "া", "ি", "ী", "ু", "ে", "ো", "🌐", "SPACE", "⌫", "↵")
        )
    } else {
        listOf(
            "qwertyuiop".map { it.toString() },
            "asdfghjkl".map { it.toString() },
            listOf("⇧") + "zxcvbnm".map { it.toString() } + listOf("⌫"),
            listOf("?123", ",", "🌐", "SPACE", ".", "↵")
        )
    }

    for (row in rows) {
        val rowLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        for (key in row) {
            val button = Button(this).apply {
                text = when (key) {
                    "SPACE" -> "English / বাংলা"
                    else -> key
                }
                isAllCaps = false
                textSize = if (key.length > 1) 12f else 18f
                setTextColor(Color.WHITE)
                setTypeface(null, Typeface.NORMAL)
                setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                        if (key == "↵") Color.rgb(55, 130, 220)
                        else Color.rgb(65, 70, 80)
                    )
                )
                setPadding(0, 0, 0, 0)
                minWidth = 0
                minimumWidth = 0
                minHeight = 0
                minimumHeight = 0
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    if (key == "SPACE") 3f else 1f
                ).apply {
                    setMargins(2, 3, 2, 3)
                }
                setOnClickListener { handleKey(key) }
            }
            rowLayout.addView(button)
        }
        root.addView(rowLayout)
    }

    return root
}

private fun handleKey(key: String) {
    when (key) {
        "🌐" -> {
            isBangla = !isBangla
            isSymbols = false
            setInputView(createKeyboard())
        }
        "?123" -> {
            isSymbols = true
            setInputView(createKeyboard())
        }
        "ABC" -> {
            isSymbols = false
            setInputView(createKeyboard())
        }
        "SPACE" -> currentInputConnection?.commitText(" ", 1)
        "⌫" -> {
            val ic: InputConnection = currentInputConnection ?: return
            ic.deleteSurroundingText(1, 0)
        }
        "↵" -> currentInputConnection?.commitText("\n", 1)
        "⇧" -> { }
        else -> currentInputConnection?.commitText(key, 1)
    }
}

}
