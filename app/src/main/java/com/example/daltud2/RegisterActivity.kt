package com.example.daltud2

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.daltud2.Control.DataBaseSQLLite
import com.example.daltud2.View.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var radioButton: RadioButton
    private lateinit var dataBaseSQLLite: DataBaseSQLLite
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rewpasswordEditText: EditText
    private lateinit var resgisterButton: Button
    private lateinit var logo : ImageView
    private lateinit var dnbtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Control()
        Events()

        // Khởi tạo cơ sở dữ liệu
        dataBaseSQLLite = DataBaseSQLLite(this)
    }
    private fun Control(){
        radioButton = findViewById(R.id.switchToLoginRadioButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        rewpasswordEditText = findViewById(R.id.rewpasswordEditText)
        resgisterButton = findViewById(R.id.resgisterButton)
        logo = findViewById(R.id.logo)
        dnbtn = findViewById(R.id.dnbtn)
    }
    private fun Events() {
        radioButton.setOnClickListener {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
        }

        resgisterButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Kiểm tra nếu email và mật khẩu không rỗng
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra định dạng email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra độ dài mật khẩu
            if (password.length < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra xem email đã tồn tại chưa
            if (dataBaseSQLLite.kiemTraEmailTonTai(email)) {
                Toast.makeText(this, "Email này đã được đăng ký", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Thêm người dùng vào cơ sở dữ liệu
            dataBaseSQLLite.insertNguoiDung(email, password)
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            Log.d("Account", "Email: " + email + " Pass: " + password)

            // Chuyển hướng sau khi đăng ký thành công
            // val intent = Intent(this, LoginActivity::class.java)
            // startActivity(intent)
            // finish()
        }
        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        dnbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}