package com.apkdoandroid.helpdesk20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apkdoandroid.helpdesk10.model.repositorio.UsuarioRepositorio
import com.apkdoandroid.helpdesk20.model.Usuario

class MainViewModel(application: Application) : AndroidViewModel(application){
    val repositorio = UsuarioRepositorio.getInstance(application.baseContext)

    private val _Resposta = MutableLiveData<Boolean>()
    var resposta : LiveData<Boolean> = _Resposta

    private var _Mensagem = MutableLiveData<String>()
    var mensagem : LiveData<String> = _Mensagem

    fun login(nome : String, senha : String){
        if(nome.isNotEmpty() && nome.isNotBlank()){
            if(senha.isNotEmpty() && senha.isNotBlank()){
                var usuario : Usuario? = null
                usuario = repositorio.getUsuario(Usuario(nome,senha))

                if(usuario != null){
                    _Resposta.value = (true)
                    _Mensagem.value = ("Login com sucesso")
                }else{
                    _Mensagem.value = ("Login ou senha incorreto!")
                }


            }else{
                _Mensagem.value = ("Preencha o cmapo senha!")
            }
        }else{
            _Mensagem.value = ("Preencha o campo nome!")
        }
    }

}