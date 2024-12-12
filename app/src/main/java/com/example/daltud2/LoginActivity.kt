package com.example.daltud2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.daltud2.Control.DataBaseSQLLite
import com.example.daltud2.Model.NguoiDung
import com.example.daltud2.View.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var dataBaseSQLLite: DataBaseSQLLite
    private lateinit var logo: ImageView
    private lateinit var dkbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Điều chỉnh padding theo hệ thống insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo cơ sở dữ liệu
        dataBaseSQLLite = DataBaseSQLLite(this)

        Control()
        Events()
    }

    private fun Control(){
        loginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)
        logo = findViewById(R.id.logo)
        dkbtn = findViewById(R.id.dkbtn)

    }

    private fun Events(){
        // Xử lý sự kiện khi bấm vào "Quên mật khẩu"
        forgotPasswordTextView.setOnClickListener {
            val forgotPasswordFragment = ForgetPass()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, forgotPasswordFragment)
                .addToBackStack(null)
                .commit()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra nếu email và password là "admin"
            if (email == "admin" && password == "admin") {
                Toast.makeText(this, "Chào mừng Admin", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminActivity::class.java)  // Chuyển tới AdminActivity
                startActivity(intent)
                finish() // Kết thúc Activity hiện tại nếu không cần quay lại
                return@setOnClickListener // Thoát khỏi hàm sau khi chuyển trang
            }

            // Mở kết nối đến cơ sở dữ liệu
            val db: SQLiteDatabase = dataBaseSQLLite.readableDatabase

            // Kiểm tra thông tin đăng nhập
            val user: NguoiDung? = dataBaseSQLLite.kiemTraTaiKhoanMatKhau(db, email, password)
            db.close()

            if (user != null) {
                // Đăng nhập thành công
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)  // Chuyển tới MainActivity
                startActivity(intent)
                finish()
            } else {
                // Đăng nhập thất bại
                Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                Log.d("Account", "Email: $email Pass: $password")
            }
        }

        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        dkbtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
