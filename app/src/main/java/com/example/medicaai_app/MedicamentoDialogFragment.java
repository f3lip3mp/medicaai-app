package com.example.medicaai_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.example.medicaai_app.adapter.MedicamentoAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicaai_app.model.Medicamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MedicamentoDialogFragment extends DialogFragment {

    private Medicamento medicamento;
    private ImageButton btnFechar;
    private FloatingActionButton btnFavoritar;
    private EditText edtAnotacoes;

    public MedicamentoDialogFragment(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_medicamento, container, false);

        // Configurar visualizações
        ImageView imgMedicamento = view.findViewById(R.id.imgMedicamentoDetalhe);
        TextView txtNome = view.findViewById(R.id.txtNomeDetalhe);
        TextView txtClasseFarmaceutica = view.findViewById(R.id.txtClasseFarmaceutica);
        TextView txtIndicacao = view.findViewById(R.id.txtIndicacaoDetalhe);
        TextView txtContraIndicacao = view.findViewById(R.id.txtContraIndicacao);
        btnFechar = view.findViewById(R.id.btnFechar);
        btnFavoritar = view.findViewById(R.id.btnFavoritar);
        edtAnotacoes = view.findViewById(R.id.editTextAnotacao);

        // Configura os dados do medicamento
        txtNome.setText(medicamento.getMedicamentoNome());
        txtClasseFarmaceutica.setText("Classe: " + medicamento.getMedicamentoIndicacao());
        txtIndicacao.setText("Indicação: " + medicamento.getMedicamentoDescricao());
        txtContraIndicacao.setText("Contraindicação: " + medicamento.getContraIndicacao());

        // Implementar clique no botão fechar
        btnFechar.setOnClickListener(v -> dismiss());

        // Verifica se o medicamento já está favoritado
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            DatabaseReference favoritosRef = FirebaseDatabase.getInstance()
                    .getReference("favoritos")
                    .child(userId);

            favoritosRef.child(medicamento.getMedicamentoNome()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    // Medicamento já favoritado, exibe o ícone de coração preenchido
                    btnFavoritar.setImageResource(R.drawable.ic_heart_fill);
                } else {
                    // Medicamento não favoritado, exibe o ícone de coração vazio
                    btnFavoritar.setImageResource(R.drawable.ic_heart_line);
                }
            });
        }

        // Implementar botão favoritar
        btnFavoritar.setOnClickListener(v -> {
            // Verifica o estado atual do favorito
            FirebaseAuth auth1 = FirebaseAuth.getInstance();
            if (auth1.getCurrentUser() == null) {
                Toast.makeText(getContext(), "É necessário estar logado para salvar favoritos!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = auth1.getCurrentUser().getUid();
            DatabaseReference favoritosRef = FirebaseDatabase.getInstance()
                    .getReference("favoritos")
                    .child(userId);

            favoritosRef.child(medicamento.getMedicamentoNome()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    // Se o medicamento já está nos favoritos, remove
                    favoritosRef.child(medicamento.getMedicamentoNome()).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Medicamento removido dos favoritos!", Toast.LENGTH_SHORT).show();
                                btnFavoritar.setImageResource(R.drawable.ic_heart_line); // Ícone vazio
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(getContext(), "Erro ao remover favorito: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    // Se o medicamento não está nos favoritos, salva
                    favoritosRef.child(medicamento.getMedicamentoNome()).setValue(medicamento)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Medicamento adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
                                btnFavoritar.setImageResource(R.drawable.ic_heart_fill); // Ícone preenchido
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(getContext(), "Erro ao salvar favorito: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
        });

        // Salvar anotação no Firestore
        Button btnSalvarAnotacao = view.findViewById(R.id.btnSalvarAnotacao);
        btnSalvarAnotacao.setOnClickListener(v -> {
            String novaAnotacao = edtAnotacoes.getText().toString().trim();

            if (!novaAnotacao.isEmpty()) {
                // Atualiza o medicamento no Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("medicamentos")
                        .document(medicamento.getId()) // ID do medicamento
                        .update("anotacao", novaAnotacao)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Anotação salva!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Erro ao salvar anotação", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Configurar tamanho do DialogFragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    (int) (requireContext().getResources().getDisplayMetrics().widthPixels * 0.9),
                    (int) (requireContext().getResources().getDisplayMetrics().heightPixels * 0.75)
            );
        }

        // Carregar anotação salva, se houver
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("medicamentos")
                .document(medicamento.getId()) // ID do medicamento
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String anotacaoSalva = documentSnapshot.getString("anotacao");
                        edtAnotacoes.setText(anotacaoSalva); // Preenche o campo
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao carregar anotação", Toast.LENGTH_SHORT).show();
                });
    }
}

