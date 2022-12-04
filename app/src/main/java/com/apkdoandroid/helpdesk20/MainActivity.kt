package com.apkdoandroid.helpdesk20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.apkdoandroid.helpdesk20.databinding.ActivityMainBinding
import com.apkdoandroid.helpdesk20.view.CadastroActivity
import com.apkdoandroid.helpdesk20.view.HomeActivity
import com.apkdoandroid.helpdesk20.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //  supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.buttonCadastrar.setOnClickListener { startActivity(Intent(this, CadastroActivity::class.java)) }
        // binding.buttonCadastrar.setOnClickListener { login() }
    //    binding.buttonEntrar.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
           binding.buttonEntrar.setOnClickListener { login() }

        observe()
    }

    private fun observe() {
        viewModel.resposta.observe(this){
            if(it){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
        viewModel.mensagem.observe(this){
           // Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(){
        val nome = binding.editUsuarioLogin.text.toString()
        val senha = binding.editSenhaLogin.text.toString()

        viewModel.login(nome,senha)
    }
}