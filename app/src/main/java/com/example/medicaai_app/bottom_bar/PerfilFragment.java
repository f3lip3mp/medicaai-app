package com.example.medicaai_app.bottom_bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.medicaai_app.CadastroMedicamentoActivity;
import com.example.medicaai_app.LoginActivity;
import com.example.medicaai_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilFragment extends Fragment {

    private Button btnCadastroMedicamento, btnDadosPessoais, btnLogoff;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar os botões
        btnCadastroMedicamento = view.findViewById(R.id.btnCadastroMedicamento);
        btnDadosPessoais = view.findViewById(R.id.btnDadosPessoais);
        btnLogoff = view.findViewById(R.id.btnLogoff);

        // Inicializar FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Verificar se o usuário é admin e configurar a visibilidade do botão
        verificarTipoUsuario();

        // Configurar ações dos botões
        btnCadastroMedicamento.setOnClickListener(v -> irParaCadastroMedicamento());
        btnLogoff.setOnClickListener(v -> fazerLogoff());

        return view;
    }

    private void verificarTipoUsuario() {
        // Exemplo de como verificar se o usuário é admin
        String tipoUsuario = firebaseAuth.getCurrentUser().getUid(); // Obter UID do usuário logado
        // Aqui você deve buscar no banco de dados do Firebase ou usar algum outro método para verificar o tipo de usuário
        // Exemplo: Verifique o campo "tipo_usuario" no Firebase Firestore


    }

    private void irParaCadastroMedicamento() {
        Intent intent = new Intent(getContext(), CadastroMedicamentoActivity.class);
        startActivity(intent);
    }

    private void fazerLogoff() {
        firebaseAuth.signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();  // Finaliza a atividade de perfil
    }
}
