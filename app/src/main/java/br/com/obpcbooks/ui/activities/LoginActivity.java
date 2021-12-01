package br.com.obpcbooks.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.UserDTO;
import br.com.obpcbooks.enums.Roles;
import br.com.obpcbooks.repositories.UserRepository;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail;
    private EditText campoSenha;
    private TextView campoMensagemError;
    private TextView campoMensagemSucesso;
    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inicializaCampos();
        userRepository = new UserRepository(LoginActivity.this);

    }

    private void inicializaCampos(){
        this.campoEmail = findViewById(R.id.login_username);
        this.campoSenha = findViewById(R.id.login_password);
        this.campoMensagemError = findViewById(R.id.login_text_menssagem_error);
        this.campoMensagemSucesso = findViewById(R.id.login_text_menssagem_success);
    }

    public void fazerRegistro(View v){
        String email = getEmailTexto();
        String senha = getSenhaTexto();
        Boolean ativo = false;

        UserDTO userDTO = new UserDTO(email, senha, Roles.ROLE_BASIC.getDescricao(), ativo);

        userRepository.fazerRegistro(userDTO, new CallbackAction<User>() {
            @Override
            public void quandoSucesso(User resultado) {
                showMessageSuccess("EMAIL REGISTRADO COM SUCESSO E LINK DE ATIVAÇÃO ENVIADO");
            }

            @Override
            public void quandoFalha(String erro) {
                showMessageError(erro);
            }
        });


    }



    public void fazerLogin(View v){
        String email = getEmailTexto();
        String senha = getSenhaTexto();

        userRepository.doLogin(email, senha, new CallbackAction<User>() {
            @Override
            public void quandoSucesso(User user) {
                if(user != null){
                    hideFeedbackMessage();
                    abreTelaDePesquisa();
                }
            }

            @Override
            public void quandoFalha(String erro) {
                showMessageError(erro);
            }
        });
    }

    private void abreTelaDePesquisa(){
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @SuppressLint("ResourceAsColor")
    private void showMessageError(String msg){
        hideFeedbackMessage();
        campoMensagemError.setText(msg);
        campoMensagemError.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    private void showMessageSuccess(String msg){
        hideFeedbackMessage();
        campoMensagemSucesso.setText(msg);
        campoMensagemSucesso.setVisibility(View.VISIBLE);
    }

    private void hideFeedbackMessage(){
        campoMensagemError.setVisibility(View.INVISIBLE);
        campoMensagemSucesso.setVisibility(View.INVISIBLE);
    }



    private String getEmailTexto(){
        return campoEmail.getText().toString();
    }

    private String getSenhaTexto(){
        return campoSenha.getText().toString();
    }


}