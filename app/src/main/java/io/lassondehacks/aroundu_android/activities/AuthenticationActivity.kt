package io.lassondehacks.aroundu_android.activities

import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.fragments.LoginFragment
import io.lassondehacks.aroundu_android.fragments.RegisterFragment

class AuthenticationActivity : AppCompatActivity() {

    val loginFragment: LoginFragment = LoginFragment(
            {
                switchToRegister()
            },
            { user ->
                val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("saved_user_cookie", user.cookie)
                editor.putString("saved_user_username", user.username)
                editor.commit()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            { err ->
                Toast.makeText(this, err, Toast.LENGTH_SHORT)
            })
    val registerFragment: RegisterFragment = RegisterFragment({ switchToLogin()})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.activity_authentication, loginFragment)
        ft.commit()
    }

    fun switchToLogin() {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
        ft.replace(R.id.activity_authentication, loginFragment)
        ft.commit()
    }

    fun switchToRegister() {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
        ft.replace(R.id.activity_authentication, registerFragment)
        ft.commit()
    }
}
