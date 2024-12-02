package com.example.medicaai_app.bottom_bar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.medicaai_app.R;
import com.example.medicaai_app.adapter.MedicamentoAdapter;
import com.example.medicaai_app.model.Medicamento;

import java.util.ArrayList;

public class BuscaFragment extends Fragment {

    private EditText searchBar;
    private RecyclerView recyclerViewSearchResults;
    private MedicamentoAdapter medicamentoAdapter;
    private ArrayList<Medicamento> medicamentoList = new ArrayList<>();
    private ArrayList<Medicamento> filteredList = new ArrayList<>();

    public BuscaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar o layout do fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBar = view.findViewById(R.id.search_bar);
        recyclerViewSearchResults = view.findViewById(R.id.recyclerViewSearchResults);
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));

        // Preencher a lista de medicamentos
        getMedicamento();

        // Configurar o adaptador
        medicamentoAdapter = new MedicamentoAdapter(filteredList, getContext());
        recyclerViewSearchResults.setAdapter(medicamentoAdapter);

        // Filtrar medicamentos com base no texto da busca
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMedicamentos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void getMedicamento() {
        // Adicione os medicamentos à lista original
        medicamentoList.add(new Medicamento(
                R.drawable.medica_ai_logo,
                "Paracetamol",
                "Indicado em adultos para a redução da febre e para o alívio temporário de dores leves a moderadas.",
                "Analgésico/Antitérmico"
        ));
        medicamentoList.add(new Medicamento(
                R.drawable.medica_ai_logo,
                "Adenosina",
                "Indicado para tratamento de Herpes zoster e demais infecções de pele e mucosas causadas pelo vírus Herpes simplex.",
                "Antiviral"
        ));
        // Adicione outros medicamentos...

        // Inicialmente, a lista filtrada contém todos os medicamentos
        filteredList.addAll(medicamentoList);
    }

    private void filterMedicamentos(String query) {
        filteredList.clear();
        for (Medicamento medicamento : medicamentoList) {
            if (medicamento.getMedicamentoNome().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(medicamento);
            }
        }
        medicamentoAdapter.notifyDataSetChanged();
    }
}
