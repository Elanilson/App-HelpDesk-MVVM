package com.apkdoandroid.helpdesk20.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apkdoandroid.helpdesk20.databinding.ActivityCadastroBinding
import com.apkdoandroid.helpdesk20.viewmodel.CadastroViewModel

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private lateinit var viewModel : CadastroViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)


        binding.buttonVoltar.setOnClickListener { finish() }
        binding.buttonCadastrar.setOnClickListener { cadastrar() }

        observe()
    }

    private fun observe() {
        viewModel.resposta.observe(this){

            if(it){
                finish()
            }

        }
        viewModel.mensagem.observe(this){
           // Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun cadastrar(){
        val nome = binding.editUsuarioCadastro.text.toString()
        val senha1 = binding.editSenhaCadastro.text.toString()
        val senha2 = binding.editSenhaConfirmarCadastro.text.toString()
        viewModel.cadastrar(nome, senha1, senha2)
    }
}