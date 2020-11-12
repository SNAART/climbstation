package com.example.climbstation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.CaptureActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.qr_scan_activity.*

class QRScanActivity: AppCompatActivity() {

    // lateinit var qrScanner : BarcodeView
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.qr_scan_activity)
        tv_scan_info.text = getString(R.string.scan_info)
        // qrScanner = findViewById(R.id.zxscan)
         scan_button.setOnClickListener {
             scanQRCode()
             skip_button.setOnClickListener{
                 val intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
             }
         }
        skip_button.setOnClickListener{
            startMain()
        }

     }


     private fun scanQRCode() {
         val integrator = IntentIntegrator(this).apply {
             captureActivity = CaptureActivity::class.java
             setOrientationLocked(false)
             setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
             setPrompt("Scanning Code")
         }
         integrator.initiateScan()
     }
    private fun startMain(){
        val intent = Intent(this@QRScanActivity,MainActivity::class.java)
        startActivity(intent)
    }
     // Get the results:
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
         if (result != null) {
             if (result.contents == null) Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG)
                 .show()
             else{ Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
             startMain()}
         } else {
             super.onActivityResult(requestCode, resultCode, data)
         }
     }
 }
