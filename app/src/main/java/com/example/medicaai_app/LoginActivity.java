package com.example.medicaai_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medicaai_app.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.textCadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textRecuperaConta.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperaContaActivity.class));
        });

        binding.btnLogin.setOnClickListener(v -> validaDados());
    }
    private void validaDados(){
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();
        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                loginFirebase(email,senha);
                binding.progressBar.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(this, "Informe uma senha.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Informe seu e-mail.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginFirebase(String email, String senha){
        mAuth.signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this,  MainActivity.class));
            }else{
                binding.progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Ocorreu um erro.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}