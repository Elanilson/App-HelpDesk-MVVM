package com.apkdoandroid.helpdesk20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apkdoandroid.helpdesk10.model.repositorio.HelpDeskRepositorio
import com.apkdoandroid.helpdesk20.model.HelpDesk

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val repositorio = HelpDeskRepositorio.getInstance(application.baseContext)

    private val _Resposta = MutableLiveData<Boolean>()
    var resposta : LiveData<Boolean> = _Resposta

    private var _Mensagem = MutableLiveData<String>()
    var mensagem : LiveData<String> = _Mensagem

    private val _HelpDesk = MutableLiveData<HelpDesk>()
    var helpDesk : LiveData<HelpDesk> = _HelpDesk

    private val _Solicitacoes = MutableLiveData<MutableList<HelpDesk>>()
    var solicitacoes : LiveData<MutableList<HelpDesk>> = _Solicitacoes

    fun carregarTodasSolicitacoes(){
        val solicitacoes = repositorio.getAll()
        if(!solicitacoes.isNullOrEmpty()){
            _Resposta.value =(true)
           _Solicitacoes.value = (solicitacoes)
        }else{
            _Resposta.value =(false)
            _Mensagem.value = ("Sem daddos "+solicitacoes.size)
        }

    }
    fun carregar_Solicitacoes_Em_Andamento(){
        val solicitacoes = repositorio.getSolicitacoesAbertas()
        if(!solicitacoes.isNullOrEmpty()){
            _Resposta.value = (true)
            _Solicitacoes.value = (solicitacoes)
        }else{
            _Resposta.value =(false)
        }
    }
    fun carregar_Solicitacoes_finalizadas(){
        val solicitacoes = repositorio.getSolicitacoesEncerradas()
        if(!solicitacoes.isNullOrEmpty()){
            _Resposta.value = (true)
            _Solicitacoes.value = (solicitacoes)
        }else{
            _Resposta.value =(false)
        }
    }
}