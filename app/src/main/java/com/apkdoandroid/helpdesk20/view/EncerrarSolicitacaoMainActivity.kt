package com.apkdoandroid.helpdesk20.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apkdoandroid.helpdesk20.R
import com.apkdoandroid.helpdesk20.databinding.ActivityEncerrarSolicitacaoMainBinding
import com.apkdoandroid.helpdesk20.viewmodel.EncerrarViewModel

class EncerrarSolicitacaoMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncerrarSolicitacaoMainBinding
    private lateinit var viewModel : EncerrarViewModel
    private var id : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncerrarSolicitacaoMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // presenter = EncerrarPresenter(this,binding.root.context)
        viewModel = ViewModelProvider(this).get(EncerrarViewModel::class.java)

        val bundle = intent.extras
        if(bundle != null){
            id = bundle.getLong("id")
            viewModel.getSolicitacao(id)
        }

        binding.buttonSalvar.setOnClickListener { encerrar() }
        binding.imageViewVoltar.setOnClickListener { finish() }

        observe()
    }

    private fun observe() {
        viewModel.resposta.observe(this){
            if (it){
                finish()
            }

        }
        viewModel.mensagem.observe(this){
           // Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }
        viewModel.helpDesk.observe(this){

            binding.textViewPatrimonio.setText("Patrimônio: ${it.patrimonio}")
            binding.textViewDescricao.setText(it.descricao)
            binding.textViewDAta.setText("Registrado em ${it.data_create}")

            if(!it.data_encerramento.isNullOrEmpty()){
                binding.editSolucao.setText(it.solucao)
                binding.editSolucao.setTextColor(resources.getColor(R.color.white))
                binding.editSolucao.setBackgroundColor(resources.getColor(R.color.azul800))
                binding.editSolucao.isEnabled = false
                binding.buttonSalvar.visibility = View.GONE
            }

        }
    }

    private fun encerrar (){
        var solucao = binding.editSolucao.text.toString()

        viewModel.encerraSolicitacao(id,solucao)
    }
}