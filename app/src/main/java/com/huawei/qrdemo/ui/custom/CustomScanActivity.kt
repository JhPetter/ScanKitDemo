package com.huawei.qrdemo.ui.custom

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.qrdemo.databinding.ActivityCustomScanBinding
import com.huawei.qrdemo.utils.SCAN_FRAME_SIZE
import com.huawei.qrdemo.utils.SCAN_RESULT

class CustomScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomScanBinding

    private lateinit var remoteView: RemoteView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomScanBinding.inflate(layoutInflater)
        initComponents(savedInstanceState)
        setContentView(binding.root)
    }

    private fun initComponents(savedInstanceState: Bundle?) {
        configRemoteView(savedInstanceState)
    }

    private fun configRemoteView(savedInstanceState: Bundle?) {
        val mScreenWidth = resources.displayMetrics.widthPixels
        val mScreenHeight = resources.displayMetrics.heightPixels
        val scanFrameSize = (SCAN_FRAME_SIZE * resources.displayMetrics.density).toInt()

        val rect = Rect()
        rect.left = mScreenWidth / 2 - scanFrameSize / 2
        rect.right = mScreenWidth / 2 + scanFrameSize / 2
        rect.top = mScreenHeight / 2 - scanFrameSize / 2
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2

        remoteView = RemoteView.Builder().setContext(this).setBoundingBox(rect)
            .setFormat(HmsScan.ALL_SCAN_TYPE).build()

        remoteView.setOnResultCallback { result ->
            if (result != null && result.isNotEmpty() && result[0] != null
                && !TextUtils.isEmpty(result[0].getOriginalValue())
            ) {
                println("Here: result: ${result[0].getOriginalValue()}")
                val intent = Intent()
                intent.putExtra(SCAN_RESULT, result[0])
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        remoteView.onCreate(savedInstanceState)

        val params = FrameLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.frameContainer.addView(remoteView, params)
    }

    override fun onStart() {
        super.onStart()
        remoteView.onStart()
    }

    override fun onStop() {
        super.onStop()
        remoteView.onStop()
    }

    override fun onResume() {
        super.onResume()
        remoteView.onResume()
    }

    override fun onPause() {
        super.onPause()
        remoteView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        remoteView.onDestroy()
    }

}