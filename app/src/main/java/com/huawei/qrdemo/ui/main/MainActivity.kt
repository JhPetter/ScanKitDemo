package com.huawei.qrdemo.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.qrdemo.databinding.ActivityMainBinding
import com.huawei.qrdemo.ui.options.MainScanOptions
import com.huawei.qrdemo.utils.CAMERA_REQ_CODE
import com.huawei.qrdemo.utils.PERMISSIONS_LENGTH

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validatePermissions()
        binding.startScan.setOnClickListener {
            openScan()
        }
    }

    private fun openScan() {
        startActivity(Intent(this, MainScanOptions::class.java))
    }

    private fun validatePermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
            CAMERA_REQ_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQ_CODE
            && grantResults.size == PERMISSIONS_LENGTH
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {
            generateCode()
        }
    }

    /**
     * content: represent the QR content
     *
     * barColor: This is the QR's Color
     *
     * backgroundColor: This is the QR's background Color
     *
     * typeQR: This is the QR type, these are the types:  HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE, HmsScan.PDF417_SCAN_TYPE, HmsScan.AZTEC_SCAN_TYPE,
    HmsScan.EAN8_SCAN_TYPE, HmsScan.EAN13_SCAN_TYPE, HmsScan.UPCCODE_A_SCAN_TYPE, HmsScan.UPCCODE_E_SCAN_TYPE, HmsScan.CODABAR_SCAN_TYPE,
    HmsScan.CODE39_SCAN_TYPE, HmsScan.CODE93_SCAN_TYPE, HmsScan.CODE128_SCAN_TYPE, HmsScan.ITF14_SCAN_TYPE
     *
     * width and height: These parameters represent the size of the QR
     *
     */
    private fun generateCode() {
        val content = "This is my QR content"
        val barColor = Color.BLACK
        val backgroundColor = Color.WHITE
        val typeQR = HmsScan.QRCODE_SCAN_TYPE
        val width = 700
        val height = 700
        val options =
            HmsBuildBitmapOption.Creator().setBitmapColor(barColor)
                .setBitmapBackgroundColor(backgroundColor).create()
        val bitmap = ScanUtil.buildBitmap(content, typeQR, width, height, options)
        binding.qrImage.setImageBitmap(bitmap)
    }
}