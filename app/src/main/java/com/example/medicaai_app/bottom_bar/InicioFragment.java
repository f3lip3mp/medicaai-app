package com.example.medicaai_app.bottom_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicaai_app.R;
import com.example.medicaai_app.adapter.MedicamentoAdapter;
import com.example.medicaai_app.model.Medicamento;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedicamentoAdapter adapter;
    private FirebaseFirestore db;
    private List<Medicamento> medicamentoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Inicializando o Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializando o RecyclerView
        recyclerView = view.findViewById(R.id.RecyclerViewMedicamento); // Referência ao RecyclerView no layout
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // Usando requireContext()

        // Inicializando a lista e o Adapter
        medicamentoList = new ArrayList<>();
        adapter = new MedicamentoAdapter((ArrayList<Medicamento>) medicamentoList, requireContext()); // Usando requireContext() aqui
        recyclerView.setAdapter(adapter);

        // Carregar os medicamentos do Firestore
        carregarMedicamentos();

        return view;
    }

    private void carregarMedicamentos() {
        db.collection("medicamentos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        medicamentoList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Medicamento medicamento = document.toObject(Medicamento.class);
                            medicamentoList.add(medicamento);
                        }
                        // Atualiza o adapter com a lista de medicamentos
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(requireContext(), "Erro ao carregar medicamentos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}




