package com.apkdoandroid.helpdesk20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apkdoandroid.helpdesk10.model.repositorio.HelpDeskRepositorio
import com.apkdoandroid.helpdesk20.model.HelpDesk
import java.text.SimpleDateFormat
import java.util.*

class SolicitacaoViewModel(application: Application) : AndroidViewModel(application) {
    val repositorio = HelpDeskRepositorio.getInstance(application.baseContext)

    private val _Resposta = MutableLiveData<Boolean>()
    var resposta : LiveData<Boolean> = _Resposta

    private var _Mensagem = MutableLiveData<String>()
    var mensagem : LiveData<String> = _Mensagem

    fun salvarSolicitacao(patrimonio : String , descricao : String){
        if(patrimonio.isNotEmpty() && patrimonio.isNotBlank()){
            if(descricao.isNotEmpty() && descricao.isNotBlank()){
                val date = Calendar.getInstance().time
                var dateTimeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())


                if(repositorio.insert(HelpDesk(patrimonio,descricao,dateTimeFormat.format(date)))){
                    _Resposta.value = true
                    _Mensagem.value = "Salvo!"
                }else{
                    _Mensagem.value = ("Não foi possível salvar")
                }

            }else{
                _Mensagem.value = ("Preencha o campo Descricao")
            }
        }else{
            _Mensagem.value = ("Preencha o campo patrimonio")
        }

    }
}