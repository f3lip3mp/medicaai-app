package com.example.medicaai_app.bottom_bar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicaai_app.R;
import com.example.medicaai_app.adapter.MedicamentoAdapter;
import com.example.medicaai_app.model.Medicamento;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BuscaFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedicamentoAdapter adapter;
    private FirebaseFirestore db;
    private EditText searchEditText;

    private List<Medicamento> medicamentoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inicializando o RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewSearchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializando o EditText de busca
        searchEditText = view.findViewById(R.id.search_bar);

        // Inicializando o Firestore
        db = FirebaseFirestore.getInstance();

        // Configurando o Adapter
        adapter = new MedicamentoAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        // Carregar medicamentos iniciais
        carregarMedicamentos();

        // Configurar filtro de busca
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarMedicamentos(s.toString().trim());  // Chama o filtro a cada alteração
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        return view;
    }

    private void carregarMedicamentos() {
        db.collection("medicamentos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        medicamentoList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Medicamento medicamento = document.toObject(Medicamento.class);
                            medicamentoList.add(medicamento);
                        }
                        adapter.updateMedicamentos(medicamentoList);
                    } else {
                        Toast.makeText(getContext(), "Nenhum medicamento encontrado.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao carregar medicamentos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }


    private void filtrarMedicamentos(String texto) {
        List<Medicamento> medicamentosFiltrados = new ArrayList<>();
        if (texto.isEmpty()) {
            // Se o campo de busca estiver vazio, mostrar todos os medicamentos
            medicamentosFiltrados.addAll(medicamentoList);
        } else {
            // Filtrar medicamentos com base no texto digitado
            for (Medicamento medicamento : medicamentoList) {
                if (medicamento.getMedicamentoNome().toLowerCase().contains(texto.toLowerCase())) {
                    medicamentosFiltrados.add(medicamento);
                }
            }
        }
        adapter.updateMedicamentos(medicamentosFiltrados);
    }

}
