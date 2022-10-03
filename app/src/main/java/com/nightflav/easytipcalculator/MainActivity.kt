package com.nightflav.easytipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.nightflav.easytipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCountTip.setOnClickListener { calculateTips() }

        binding.etCostOfService.setOnKeyListener { view, i, _ -> handleKeyEvent(view, i) }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTips() {
        val percent = when (binding.rgTipPercentageGroup.checkedRadioButtonId) {
            binding.rbAmazing.id -> 0.2
            binding.rbGood.id -> 0.15
            else -> 0.1
        }
        val cost = binding.etCostOfService.text.toString().toDoubleOrNull() ?: 0.0
        val tip =
            if (binding.switchRoundUpTip.isChecked) (percent * cost).roundToLong() else (percent * cost)
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTipAmount.text = getString(R.string.tip_amount, formattedTip.toString())
    }
}