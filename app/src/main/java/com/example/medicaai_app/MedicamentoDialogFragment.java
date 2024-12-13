package com.example.medicaai_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicaai_app.model.Medicamento;

public class MedicamentoDialogFragment extends DialogFragment {

    private Medicamento medicamento;

    public MedicamentoDialogFragment(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_medicamento, container, false);

        // Referências aos elementos do layout
        ImageView imgMedicamento = view.findViewById(R.id.imgMedicamentoDetalhe);
        TextView txtNome = view.findViewById(R.id.txtMedicamentoNomeDetalhe);
        TextView txtDescricao = view.findViewById(R.id.txtMedicamentoDescricaoDetalhe);
        Button btnFechar = view.findViewById(R.id.btnFechar);

        // Define os valores do medicamento
        txtNome.setText(medicamento.getMedicamentoNome());
        txtDescricao.setText(medicamento.getMedicamentoDescricao());
        imgMedicamento.setImageResource(medicamento.getMedicamentoImg()); // Imagem local, altere conforme necessário.

        // Fecha o DialogFragment
        btnFechar.setOnClickListener(v -> dismiss());

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
    }
}
