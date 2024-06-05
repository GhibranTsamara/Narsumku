package com.bangkit.narsumku.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.narsumku.data.pref.UserModel
import com.bangkit.narsumku.databinding.ActivityLoginBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.main.MainActivity
import com.bangkit.narsumku.ui.signup.SignupActivity
import com.bangkit.narsumku.ui.welcome.WelcomeActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()
    }

    private fun playAnimation() {

        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(250)
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(250)
        val intro = ObjectAnimator.ofFloat(binding.tvAppIntro, View.ALPHA, 1f).setDuration(250)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmailOrMobileNumber, View.ALPHA, 1f).setDuration(250)
        val email = ObjectAnimator.ofFloat(binding.EmailEditText, View.ALPHA, 1f).setDuration(250)
        val layoutEmail = ObjectAnimator.ofFloat(binding.EmailEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(250)
        val password = ObjectAnimator.ofFloat(binding.PasswordEditText, View.ALPHA, 1f).setDuration(250)
        val layoutPassword = ObjectAnimator.ofFloat(binding.PasswordEditTextLayout, View.ALPHA, 1f).setDuration(250)
        val forgotPassword = ObjectAnimator.ofFloat(binding.tvForgetPassword, View.ALPHA, 1f).setDuration(250)
        val account = ObjectAnimator.ofFloat(binding.tvNoAccount, View.ALPHA, 1f).setDuration(250)
        val signup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(250)

        val together = AnimatorSet().apply {
            playTogether(account, signup)
        }
        AnimatorSet().apply {
            playSequentially(
                welcome,
                intro,
                tvEmail,
                email,
                layoutEmail,
                tvPassword,
                password,
                layoutPassword,
                forgotPassword,
                login,
                together
            )
            start()
        }
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.EmailEditText.text.toString()
            viewModel.saveSession(UserModel(email, "sample_token"))
            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("You have successfully logged in.")
                setPositiveButton("Continue") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.ivArrowBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}