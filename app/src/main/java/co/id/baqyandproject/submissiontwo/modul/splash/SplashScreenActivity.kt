package co.id.baqyandproject.submissiontwo.modul.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import co.id.baqyandproject.submissiontwo.R
import co.id.baqyandproject.submissiontwo.modul.main.MainActivity
import co.id.baqyandproject.submissiontwo.util.GitConst

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, GitConst.SPLASH)
    }
}
