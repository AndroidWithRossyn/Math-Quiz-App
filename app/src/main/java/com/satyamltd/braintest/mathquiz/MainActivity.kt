package com.satyamltd.braintest.mathquiz

import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.satyamltd.braintest.mathquiz.databinding.ActivityMainBinding
import com.satyamltd.braintest.mathquiz.utils.CheckBackground


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var sp : SharedPreferences? = null
    var ed : SharedPreferences.Editor? = null

    var bgnim : RelativeLayout? = null
    var bgnim2 : RelativeLayout? = null

    companion object {
        @JvmField
        var PACKAGE_NAME : String? = null
    }


    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PACKAGE_NAME = applicationContext.packageName




        sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        ed = sp!!.edit()
        val initmusic = sp!!.getBoolean("initmusic", false)
        if (!initmusic) {
            ed!!.putBoolean("initmusic", true)
            ed!!.putBoolean("play", true)
            ed!!.commit()
        }
        val backgroundMusic = sp!!.getBoolean("play", false)

        if (backgroundMusic) {
            startService(Intent(this, BackgroundSoundService::class.java))
        } else {
            stopService(Intent(this, BackgroundSoundService::class.java))
        }





        bgnim = binding.bganimation
        bgnim2 = binding.bganimation2

        val animator = ValueAnimator.ofFloat(0.0f, 0.5f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.setDuration(9000L)
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = bgnim!!.width.toFloat()
            val translationX = width*progress
            bgnim!!.translationX = translationX
            bgnim2!!.translationX = translationX - width
        }
        animator.start()

        MobileAds.initialize(this)
        FirebaseApp.initializeApp(this)

        binding.btnPlay.setOnClickListener(this)

        binding.btnOptions.setOnClickListener(this)
        binding.btnQuit.setOnClickListener(this)

        binding.imgPolitics.setOnClickListener(this)
        binding.imgShare.setOnClickListener(this)
        binding.imgMore.setOnClickListener(this)
        binding.imgRate.setOnClickListener(this)

    }

    private val TAG = "MainActivity"

    override fun onClick(v : View?) {
        when (v) {
            binding.btnPlay -> {
                val intentnew = Intent(this, MenuOptions::class.java)
                startActivity(intentnew)
            }

            binding.btnOptions -> {
                val intentnew = Intent(this, OptionsActivity::class.java)
                startActivity(intentnew)
            }


            binding.imgPolitics -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy))
                    )
                )
                stopService(Intent(applicationContext, BackgroundSoundService::class.java))
            }

            binding.btnQuit -> {
                finish()
            }

            binding.imgShare -> {
                try {
                    val sendIntent = Intent()
                    sendIntent.setAction(Intent.ACTION_SEND)
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT, resources.getString(R.string.share_msg) + packageName
                    )
                    sendIntent.setType("text/plain")
                    startActivity(sendIntent)
                    stopService(Intent(applicationContext, BackgroundSoundService::class.java))
                } catch (e : Exception) {

                    Toast.makeText(this, "No Application Found to Open", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onClick: ${e.message}")
                }
            }

            binding.imgMore -> {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW, Uri.parse(getString(R.string.play_more_apps))
                        )
                    )
                    stopService(Intent(applicationContext, BackgroundSoundService::class.java))
                } catch (e : Exception) {
                    Toast.makeText(this, "No Application Found to Open", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onClick: ${e.message}")
                }
            }

            binding.imgRate -> {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                "https://play.google.com/store/apps/details?id=$packageName"
                            )
                        )
                    )
                    stopService(Intent(applicationContext, BackgroundSoundService::class.java))
                } catch (e : Exception) {
                    Toast.makeText(this, "No Application Found to Open", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onClick: ${e.message}")
                }
            }
        }

    }

    public override fun onDestroy() {
        stopService(Intent(applicationContext, BackgroundSoundService::class.java))
        super.onDestroy()
    }

    public override fun onStop() {
        super.onStop()
        if (CheckBackground.appInBackground(this)) {
            stopService(Intent(applicationContext, BackgroundSoundService::class.java))
        } else {
            val kgMgr = getSystemService(POWER_SERVICE) as PowerManager
            val showing = kgMgr.isScreenOn
            if (!showing) {
                stopService(Intent(applicationContext, BackgroundSoundService::class.java))
            }
        }
    }

    public override fun onPause() {
        super.onPause()
        if (CheckBackground.appInBackground(this)) {
            stopService(Intent(applicationContext, BackgroundSoundService::class.java))
        } else {
            val kgMgr = getSystemService(POWER_SERVICE) as PowerManager
            val showing = kgMgr.isScreenOn
            if (!showing) {
                stopService(Intent(applicationContext, BackgroundSoundService::class.java))
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // preferences
        sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        ed = sp!!.edit()
        val backgroundMusic = sp!!.getBoolean("play", false)

        if (backgroundMusic) {
            val svc = Intent(this, BackgroundSoundService::class.java)
            startService(svc)
        }
    }

}
