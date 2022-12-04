package com.apkdoandroid.helpdesk20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apkdoandroid.helpdesk10.model.repositorio.HelpDeskRepositorio
import com.apkdoandroid.helpdesk10.model.repositorio.UsuarioRepositorio
import com.apkdoandroid.helpdesk20.model.Usuario

class CadastroViewModel(application: Application) : AndroidViewModel(application) {
    private val repositorio = UsuarioRepositorio.getInstance(application.baseContext)

    private val _Resposta = MutableLiveData<Boolean>()
    var resposta : LiveData<Boolean> = _Resposta

    private var _Mensagem = MutableLiveData<String>()
    var mensagem : LiveData<String> = _Mensagem

    fun cadastrar(nome : String , senha1 : String , senha2 : String){
        if(nome.isNotEmpty() && nome.isNotBlank()){
            if(senha1.isNotEmpty() && senha1.isNotBlank()) {
                if(senha2.isNotEmpty() && senha2.isNotBlank()) {
                    if(senha1.equals(senha2)){
                        if(repositorio.insert(Usuario(nome,senha1))){
                            _Resposta.value = (true)
                            _Mensagem.value = ("Cadastrado com sucesso")
                        }else{
                            _Mensagem.value = ("Não conseguir cadastrar")
                        }

                    }else{
                        _Mensagem.value = ("A senha não confere")
                    }

                }else{
                    _Mensagem.value = ("Preencha o campo confirmar senha")
                }

            }else{
                _Mensagem.value = ("Preencha o campo senha")
            }
        }else{
            _Mensagem.value = ("Preencha o campo nome!")
        }

    }
}