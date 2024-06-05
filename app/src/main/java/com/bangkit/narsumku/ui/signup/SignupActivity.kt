package com.bangkit.narsumku.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bangkit.narsumku.databinding.ActivitySignupBinding
import com.bangkit.narsumku.ui.login.LoginActivity
import com.bangkit.narsumku.ui.welcome.WelcomeActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()
    }

    private fun playAnimation() {

        val register = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(250)
        val tvFullName = ObjectAnimator.ofFloat(binding.tvFullName, View.ALPHA, 1f).setDuration(250)
        val name = ObjectAnimator.ofFloat(binding.FullNameEditText, View.ALPHA, 1f).setDuration(250)
        val layoutName = ObjectAnimator.ofFloat(binding.FullNameEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(250)
        val password = ObjectAnimator.ofFloat(binding.PasswordEditText, View.ALPHA, 1f).setDuration(250)
        val layoutPassword = ObjectAnimator.ofFloat(binding.PasswordEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(250)
        val email = ObjectAnimator.ofFloat(binding.EmailEditText, View.ALPHA, 1f).setDuration(250)
        val layoutEmail = ObjectAnimator.ofFloat(binding.EmailEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val tvMobileNumber = ObjectAnimator.ofFloat(binding.tvMobileNumber, View.ALPHA, 1f).setDuration(250)
        val mobileNumber = ObjectAnimator.ofFloat(binding.MobileNumberEditText, View.ALPHA, 1f).setDuration(250)
        val layoutMobilePhone = ObjectAnimator.ofFloat(binding.MobileNumberEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val tvDate = ObjectAnimator.ofFloat(binding.tvDateOfBirth, View.ALPHA, 1f).setDuration(250)
        val date = ObjectAnimator.ofFloat(binding.DateOfBirthEditText, View.ALPHA, 1f).setDuration(250)
        val layoutDate = ObjectAnimator.ofFloat(binding.DateOfBirthEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val tvContinue = ObjectAnimator.ofFloat(binding.tvByContinue, View.ALPHA, 1f).setDuration(250)
        val tvTerms = ObjectAnimator.ofFloat(binding.tvTerms, View.ALPHA, 1f).setDuration(250)
        val tvAnd = ObjectAnimator.ofFloat(binding.tvAnd, View.ALPHA, 1f).setDuration(250)
        val tvPolicy = ObjectAnimator.ofFloat(binding.tvPolicy, View.ALPHA, 1f).setDuration(250)
        val account = ObjectAnimator.ofFloat(binding.tvNoAccount, View.ALPHA, 1f).setDuration(250)
        val signup = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(250)

        val together1 = AnimatorSet().apply {
            playTogether(tvContinue, tvTerms, tvAnd, tvPolicy)
        }
        val together2 = AnimatorSet().apply {
            playTogether(account, signup)
        }
        AnimatorSet().apply {
            playSequentially(
                tvFullName,
                name,
                layoutName,
                tvPassword,
                password,
                layoutPassword,
                tvEmail,
                email,
                layoutEmail,
                tvMobileNumber,
                mobileNumber,
                layoutMobilePhone,
                tvDate,
                date,
                layoutDate,
                register,
                together1,
                together2
            )
            start()
        }
    }

    private fun setupAction() {
        binding.btnSignup.setOnClickListener {
            val email = binding.EmailEditText.text.toString()

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Account $email created successfully.")
                setPositiveButton("Continue") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}