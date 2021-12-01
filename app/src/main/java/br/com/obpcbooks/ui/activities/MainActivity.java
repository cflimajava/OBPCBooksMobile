package br.com.obpcbooks.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.model.DadosDeSessao;
import br.com.obpcbooks.ui.fragments.CestaFragment;
import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.LivroDTO;
import br.com.obpcbooks.model.Cesta;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.repositories.UserRepository;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.ui.fragments.PesquisaLivrosFragment;
import br.com.obpcbooks.ui.fragments.ReservasFragment;

public class MainActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private List<LivroDTO> livrosEncontrados = new ArrayList<>();

    private BottomNavigationView bottomNavigationBar;
    private Cesta cesta;
    private DadosDeSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pesquisa de Livros");
        userRepository = new UserRepository(this);

        inicializaCampos();
        getUsuarioLogado();


    }

    private void openFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putSerializable("sessao", sessao);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.main_fragment, fragment.getClass(), bundle)
                .commit();
    }



    private void inicializaCampos(){
        bottomNavigationBar = findViewById(R.id.main_botton_navigation);
        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_menu_pesquisar:
                        openFragment(new PesquisaLivrosFragment());
                        return true;
                    case R.id.bottom_menu_cesta:
                        openFragment(new CestaFragment());
                        return true;
                    case R.id.bottom_menu_reservas:
                        openFragment(new ReservasFragment());
                        return true;
                    case R.id.bottom_menu_sair:
                        mostarConfirmacaoDeLogout();
                        return true;
                }
                return false;
            }
        });

    }

    private void mostarConfirmacaoDeLogout(){
        new AlertDialog.Builder(this)
                .setTitle("Fazer logout")
                .setMessage("Deseja mesmo realizar logout ?")
                .setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fazerLogout();
                            }
                        })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void getUsuarioLogado() {
        userRepository.getUsuarioLogado(new CallbackAction<User>() {
            @Override
            public void quandoSucesso(User user) {
                if(user != null){
                    inicializaComUsuarioLogado(user);
                }
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(MainActivity.this, "Falha ao buscar usuario logado: "+ erro, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void inicializaComUsuarioLogado(User user) {
        this.sessao = DadosDeSessao.getSessao().comUsuarioLogado(user);
        this.cesta = Cesta.getCesta();
        openFragment(new PesquisaLivrosFragment());
    }

    public void fazerLogout(){
        userRepository.doLogout();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }
}