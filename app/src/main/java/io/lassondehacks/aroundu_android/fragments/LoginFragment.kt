package io.lassondehacks.aroundu_android.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.result.Result

import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.domain.User
import io.lassondehacks.aroundu_android.services.AuthenticationService

class LoginFragment(onClickRegister: () -> Unit, onSuccessLogin: (user: User) -> Unit, onFailedLogin: (err: String?) -> Unit) : Fragment() {

    val onClickRegisterCallback: () -> Unit
    val onSuccessLoginCallback: (user: User) -> Unit
    val onFailedLoginCallback: (err: String?) -> Unit

    init {
        this.onClickRegisterCallback = onClickRegister
        this.onSuccessLoginCallback = onSuccessLogin
        this.onFailedLoginCallback = onFailedLogin
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_login, container, false)
        view?.findViewById(R.id.link_signup)?.setOnClickListener {
            onClickRegisterCallback()
        }
        view?.findViewById(R.id.btn_login)?.setOnClickListener {
            AuthenticationService.login(
                    (view?.findViewById(R.id.input_email) as EditText).text.toString(),
                    (view?.findViewById(R.id.input_password) as EditText).text.toString(),
                    { err, user ->
                        if(user != null) {
                            onSuccessLoginCallback(user)
                        } else {
                            onFailedLoginCallback(err)
                        }
                    }
            )
        }
        return view
    }


}
