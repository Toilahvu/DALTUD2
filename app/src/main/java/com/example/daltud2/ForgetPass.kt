package com.example.daltud2

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ForgetPass : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var verificationCodeEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var countdownTextView: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forget_pass, container, false)

        // Ánh xạ các trường EditText và Button từ layout
        emailEditText = view.findViewById(R.id.emailEditText)
        verificationCodeEditText = view.findViewById(R.id.verificationCodeEditText)
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        submitButton = view.findViewById(R.id.submitButton)
        countdownTextView = view.findViewById(R.id.countdownTextView)

        countdownTextView.visibility = View.GONE

        // Xử lý sự kiện khi nhấn nút Gửi
        submitButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val verificationCode = verificationCodeEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword == confirmPassword) {
                // Thực hiện kiểm tra mã xác nhận và thay đổi mật khẩu
                Toast.makeText(requireContext(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show()
                countdownTextView.visibility = View.VISIBLE
                startCountdown()
                Handler(Looper.getMainLooper()).postDelayed({
                    requireActivity().supportFragmentManager.popBackStack()
                }, 5000) // 5000 milliseconds = 5 seconds
            } else {
                Toast.makeText(requireContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
            }
}

        return view
    }
    private fun startCountdown() {
        // Đếm ngược từ 5 giây
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Cập nhật TextView với thời gian còn lại
                countdownTextView.text = "Quay lại trong ${millisUntilFinished / 1000} giây"
            }

            override fun onFinish() {
                countdownTextView.text = "Quay lại trang trước"
            }
        }.start()
    }
}
