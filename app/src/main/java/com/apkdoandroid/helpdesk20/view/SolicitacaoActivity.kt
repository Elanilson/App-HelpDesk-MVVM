package com.apkdoandroid.helpdesk20.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apkdoandroid.helpdesk20.databinding.ActivitySolicitacaoBinding
import com.apkdoandroid.helpdesk20.viewmodel.SolicitacaoViewModel

class SolicitacaoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySolicitacaoBinding
    private lateinit var viewModel: SolicitacaoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SolicitacaoViewModel::class.java)



        binding.imageViewVoltar.setOnClickListener { finish() }
        binding.buttonSalvar.setOnClickListener { salvar() }

        observe()
    }

    private fun observe() {
        viewModel.resposta.observe(this){
            if(it){
                finish()
            }
        }
        viewModel.mensagem.observe(this){
            //Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun salvar(){
        val patrimonio = binding.editPatrimonio.text.toString()
        val descicao = binding.editDescricao.text.toString()
        viewModel.salvarSolicitacao(patrimonio,descicao)
    }
}