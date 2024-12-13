package com.example.medicaai_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicaai_app.model.Medicamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        edtAnotacoes = view.findViewById(R.id.edtAnotacoes);

        // Configura os dados do medicamento
        txtNome.setText(medicamento.getMedicamentoNome());
        txtClasseFarmaceutica.setText("Classe: " + medicamento.getClasseFarmaceutica());
        txtIndicacao.setText("Indicação: " + medicamento.getMedicamentoIndicacao());
        txtContraIndicacao.setText("Contraindicação: " + medicamento.getContraIndicacao());

        // Implementar clique no botão fechar
        btnFechar.setOnClickListener(v -> dismiss());

        // Implementar botão favoritar
        btnFavoritar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Medicamento favoritado!", Toast.LENGTH_SHORT).show();
            // Salvar no Firebase ou banco local
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
    }
}
