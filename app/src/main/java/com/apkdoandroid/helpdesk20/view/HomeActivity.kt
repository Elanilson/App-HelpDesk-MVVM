package com.apkdoandroid.helpdesk20.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apkdoandroid.helpdesk20.R
import com.apkdoandroid.helpdesk20.adapter.SolicitacoesAdapter
import com.apkdoandroid.helpdesk20.databinding.ActivityHomeBinding
import com.apkdoandroid.helpdesk20.interfaces.OnHelpDeskListener
import com.apkdoandroid.helpdesk20.model.HelpDesk
import com.apkdoandroid.helpdesk20.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityHomeBinding
    private val adapter = SolicitacoesAdapter()
    private lateinit var viewModel : HomeViewModel
    private var buttonAndamento = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarSair.toolbar.setTitle("")
        setSupportActionBar(binding.toolbarSair.toolbar)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        binding.buttonMinhasSolicitacoes.setOnClickListener {
            binding.buttonMinhasSolicitacoes.setTextColor(resources.getColor(R.color.white))
            binding.buttonTodas.setTextColor(resources.getColor(R.color.white))
            binding.buttonMinhasSolicitacoes.background =resources.getDrawable(R.drawable.background_button_meus_chamados)
            binding.buttonTodas.background =resources.getDrawable(R.drawable.background_button_chamados)
            // binding.buttonMinhasSolicitacoes.setTextAppearance(R.style.buttonCustom_chamado_em_andamento)
            //  binding.buttonMinhasSolicitacoes.setBackgroundResource(R.drawable.background_button_meus_chamados)
            adapter.limpar()
            if(buttonAndamento){
                viewModel.carregar_Solicitacoes_Em_Andamento()
            }else{
                viewModel.carregar_Solicitacoes_finalizadas()

            }
        }
        binding.buttonTodas.setOnClickListener {
            //   binding.buttonTodas.setTextColor(resources.getColor(R.color.laranja))
            binding.buttonTodas.background =resources.getDrawable(R.drawable.background_button_meus_chamados)
            binding.buttonMinhasSolicitacoes.background =resources.getDrawable(R.drawable.background_button_chamados)
            // binding.buttonMinhasSolicitacoes.setTextAppearance(R.style.buttonCustom_chamado_em_andamento)
            //  binding.buttonMinhasSolicitacoes.setBackgroundResource(R.drawable.background_button_meus_chamados)
            adapter.limpar()
            viewModel.carregarTodasSolicitacoes()
        }
        binding.buttonAndamento.setOnClickListener {
            binding.buttonAndamento.setTextColor(resources.getColor(R.color.vermleho500))
            binding.buttonAndamento.background =resources.getDrawable(R.drawable.background_button_chamado_em_andamento)
            binding.buttonFinalizadas.setTextColor(resources.getColor(R.color.white))
            binding.buttonFinalizadas.background =resources.getDrawable(R.drawable.background_button_chamados)
            //  binding.buttonMinhasSolicitacoes.setBackgroundResource(R.drawable.background_button_meus_chamados)
            // binding.buttonMinhasSolicitacoes.setTextAppearance(R.style.buttonCustom_chamado_em_andamento)
            buttonAndamento = true
            adapter.limpar()
            viewModel.carregar_Solicitacoes_Em_Andamento()
        }
        binding.buttonFinalizadas.setOnClickListener {
            binding.buttonFinalizadas.setTextColor(resources.getColor(R.color.verde100))
            binding.buttonAndamento.setTextColor(resources.getColor(R.color.white))
            binding.buttonAndamento.background =resources.getDrawable(R.drawable.background_button_chamados)
            binding.buttonFinalizadas.background =resources.getDrawable(R.drawable.background_button_chamado_finalizado)
            // binding.buttonMinhasSolicitacoes.setTextAppearance(R.style.buttonCustom_chamado_em_andamento)
            //  binding.buttonMinhasSolicitacoes.setBackgroundResource(R.drawable.background_button_meus_chamados)
            buttonAndamento = false
            adapter.limpar()
            viewModel.carregar_Solicitacoes_finalizadas()
        }

        binding.buttonNovaSolicitacao.setOnClickListener { startActivity(
            Intent(this,
            SolicitacaoActivity::class.java)
        ) }
        val listener = object : OnHelpDeskListener{
            override fun onClick(helpDesk: HelpDesk) {

                val bundle = Bundle()
                bundle.putLong("id",helpDesk.id)
                val intent = Intent(binding.root.context,EncerrarSolicitacaoMainActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)

            }
        }
        adapter.attackListener(listener)

        configurarRecyclewview()

        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sair,menu)
        // return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sair -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observe() {
        viewModel.resposta.observe(this){

            if(it){
                binding.layoutSemSolicitacao.visibility = View.GONE
            }else{
                binding.layoutSemSolicitacao.visibility = View.VISIBLE

            }

        }
        viewModel.mensagem.observe(this){
          //  Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }
        viewModel.solicitacoes.observe(this){
            if(!it.isNullOrEmpty()){
                adapter.attackSolicitacoes(it)
            }
        }
    }

    private fun configurarRecyclewview(){
        binding.recyclerviewSolicitacoes.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSolicitacoes.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.limpar()
        viewModel.carregar_Solicitacoes_Em_Andamento()
    }
}