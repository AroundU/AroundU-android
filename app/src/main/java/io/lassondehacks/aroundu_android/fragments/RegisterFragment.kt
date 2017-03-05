package io.lassondehacks.aroundu_android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.lassondehacks.aroundu_android.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment(onClickLogin: () -> Unit) : Fragment() {

    val onClickCallback: () -> Unit

    init {
        this.onClickCallback = onClickLogin
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_register, container, false)
        view?.findViewById(R.id.link_login)?.setOnClickListener {
            onClickCallback()
        }
        return view
    }
}
