package com.example.medicaai_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medicaai_app.databinding.ActivityRecuperaContaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperaContaActivity extends AppCompatActivity {
    private ActivityRecuperaContaBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.btnRecuperaConta.setOnClickListener(v -> validaDados());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void validaDados(){
        String email = binding.editEmail.getText().toString().trim();
        if(!email.isEmpty()){
            binding.progressBar.setVisibility(View.VISIBLE);
            recuperaContaFirebase(email);
        }else{
            Toast.makeText(this, "Informe seu e-mail.", Toast.LENGTH_SHORT).show();
        }
    }
    private void recuperaContaFirebase(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
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