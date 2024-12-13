package com.example.medicaai_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicaai_app.databinding.ActivityCadastroMedicamentoBinding;
import com.example.medicaai_app.model.Medicamento;
import com.example.medicaai_app.repository.FirestoreRepository;

public class CadastroMedicamentoActivity extends AppCompatActivity {

    private ActivityCadastroMedicamentoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroMedicamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Exemplo de como salvar medicamento
        binding.btnSalvar.setOnClickListener(v -> salvarMedicamento());
    }

    private void salvarMedicamento() {
        Medicamento medicamento = new Medicamento();
        medicamento.setMedicamentoNome(binding.editNome.getText().toString());
        medicamento.setMedicamentoDescricao(binding.editDescricao.getText().toString());
        medicamento.setMedicamentoIndicacao(binding.editIndicacao.getText().toString());
        medicamento.setContraIndicacao(binding.editContraIndicacao.getText().toString());

        // Criar uma instância do FirestoreRepository e passar o contexto
        FirestoreRepository firestoreRepository = new FirestoreRepository(CadastroMedicamentoActivity.this);
        firestoreRepository.salvarMedicamento(medicamento);
    }
}
