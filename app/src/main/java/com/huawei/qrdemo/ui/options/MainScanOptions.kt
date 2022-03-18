package com.huawei.qrdemo.ui.options

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.huawei.qrdemo.databinding.ActivityMainScanOptionsBinding
import com.huawei.qrdemo.model.Option
import com.huawei.qrdemo.ui.custom.CustomScanActivity
import com.huawei.qrdemo.ui.display.DisplayActivity
import com.huawei.qrdemo.utils.SCAN_RESULT
import com.huawei.qrdemo.utils.SCAN_VALUE

class MainScanOptions : AppCompatActivity() {
    private lateinit var binding: ActivityMainScanOptionsBinding

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val scan: HmsScan? = result.data?.getParcelableExtra(SCAN_RESULT)
            scan?.let { showScannedInformation(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScanOptionsBinding.inflate(layoutInflater)
        initComponents()
        setContentView(binding.root)
    }

    private fun initComponents() {
        binding.listOptions.adapter = OptionAdapter(loadOptions()) {
            openScan(it)
        }
    }

    private fun showScannedInformation(scan: HmsScan) {
        val intent = Intent(this, DisplayActivity::class.java).apply {
            putExtra(SCAN_VALUE, scan)
        }
        startActivity(intent)
    }

    private fun openScan(option: Option) {
        when (option.id) {
            1 -> ScanUtil.startScan(
                this,
                option.requestCode,
                HmsScanAnalyzerOptions.Creator().create()
            )
            2 -> {
                val intent = Intent(this, CustomScanActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    private fun loadOptions(): List<Option> {
        return arrayListOf(
            Option(1, "Scan by sdk design", 100),
            Option(2, "Scan by my custom design", 101)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val hmsScan: HmsScan? = data?.getParcelableExtra(ScanUtil.RESULT)
            hmsScan?.let {
                showScannedInformation(it)
            }
        }
    }
}