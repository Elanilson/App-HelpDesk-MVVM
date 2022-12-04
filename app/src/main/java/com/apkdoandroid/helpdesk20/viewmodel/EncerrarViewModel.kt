package com.apkdoandroid.helpdesk20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apkdoandroid.helpdesk10.model.repositorio.HelpDeskRepositorio
import com.apkdoandroid.helpdesk20.model.HelpDesk
import java.text.SimpleDateFormat
import java.util.*

class EncerrarViewModel(application: Application) : AndroidViewModel(application) {

    val repositorio = HelpDeskRepositorio.getInstance(application.baseContext)

    private val _Resposta = MutableLiveData<Boolean>()
    var resposta : LiveData<Boolean> = _Resposta

    private var _Mensagem = MutableLiveData<String>()
    var mensagem : LiveData<String> = _Mensagem

    private val _HelpDesk = MutableLiveData<HelpDesk>()
    var helpDesk : LiveData<HelpDesk> = _HelpDesk

    fun getSolicitacao(id : Long){
        _HelpDesk.value = repositorio.get(id)
    }

    fun encerraSolicitacao(id : Long, texto : String){
        if (id != null){
            if(!texto.isNullOrEmpty()){
                val date = Calendar.getInstance().time
                var dateTimeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                if(repositorio.update(HelpDesk(id,texto,dateTimeFormat.format(date)))){
                    _Resposta.value = (true)
                    _Mensagem.value = ("Salvo")
                }
            }else{
                _Mensagem.value = ("Informe a solução da solicitação")
            }
        }else{
            _Mensagem.value = ("Falta o ID da solicitação")
        }

    }
}