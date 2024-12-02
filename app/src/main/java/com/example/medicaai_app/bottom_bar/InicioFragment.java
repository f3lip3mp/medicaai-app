package com.example.medicaai_app.bottom_bar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicaai_app.R;
import com.example.medicaai_app.adapter.MedicamentoAdapter;
import com.example.medicaai_app.model.Medicamento;

import java.util.ArrayList;

public class InicioFragment extends Fragment {

    private RecyclerView recyclerViewMedicamento;
    private MedicamentoAdapter medicamentoAdapter;
    private ArrayList<Medicamento> medicamentoList;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar o layout do fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Configurar o RecyclerView
        recyclerViewMedicamento = view.findViewById(R.id.RecyclerViewMedicamento);
        recyclerViewMedicamento.setLayoutManager(new LinearLayoutManager(getContext()));
        medicamentoList = getMedicamentoList();
        medicamentoAdapter = new MedicamentoAdapter(medicamentoList, getContext());
        recyclerViewMedicamento.setAdapter(medicamentoAdapter);

        return view;
    }

    private ArrayList<Medicamento> getMedicamentoList() {
        ArrayList<Medicamento> list = new ArrayList<>();
        list.add(new Medicamento(R.drawable.medica_ai_logo, "Paracetamol",
                "Reduz febre e alivia dores leves a moderadas.", "Analgésico/Antitérmico"));
        list.add(new Medicamento(R.drawable.medica_ai_logo, "Adenosina",
                "Tratamento de taquicardia paroxística supraventricular.", "Antiarrítmico"));
        // Adicione outros medicamentos aqui...
        return list;
    }
}
