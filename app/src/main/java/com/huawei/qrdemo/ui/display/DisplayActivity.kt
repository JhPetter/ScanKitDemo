package com.huawei.qrdemo.ui.display

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.qrdemo.databinding.ActivityDisplayBinding
import com.huawei.qrdemo.utils.SCAN_VALUE

class DisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayBinding
    private lateinit var hmsScan: HmsScan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        intent.getParcelableExtra<HmsScan>(SCAN_VALUE)?.let {
            hmsScan = it
        }
        initComponents()
        displayHmsScan()
        setContentView(binding.root)
    }

    private fun initComponents() {
        binding.displayBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun displayHmsScan() {
        if (!::hmsScan.isInitialized) finish()
        binding.displayResultContent.text = hmsScan.originalValue
        binding.displayFormat.text = hmsScan.scanType.toString()
        when (hmsScan.getScanTypeForm()) {
            HmsScan.PURE_TEXT_FORM -> {
                binding.displayText.text = "Text"
                binding.displayResultType.text = "Text"
            }
            HmsScan.WIFI_CONNECT_INFO_FORM -> {
                binding.displayText.text = "Wi-Fi"
                binding.displayResultType.text = "Wi-Fi"
            }
        }
    }
}