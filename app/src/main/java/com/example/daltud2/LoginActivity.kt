package com.example.daltud2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Điều chỉnh padding theo hệ thống insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Xử lý sự kiện khi bấm vào "Quên mật khẩu"
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)
        forgotPasswordTextView.setOnClickListener {
            val forgotPasswordFragment = ForgetPass()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, forgotPasswordFragment)
                .addToBackStack(null)
                .commit()
        }

        // Xử lý sự kiện khi nhấn vào RadioButton
        radioButton = findViewById(R.id.switchToRegisterRadioButton)
        radioButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
