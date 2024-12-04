package com.example.medicaai_app.bottom_bar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medicaai_app.R;
import com.example.medicaai_app.adapter.MedicamentoAdapter;
import com.example.medicaai_app.model.Medicamento;
import com.example.medicaai_app.databinding.FragmentFavoritoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritoFragment extends Fragment {
    private FragmentFavoritoBinding binding;
    private MedicamentoAdapter medicamentoAdapter;
    private ArrayList<Medicamento> listaFavoritos;
    private DatabaseReference favoritosRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritoBinding.inflate(inflater, container, false);

        // Configurar RecyclerView
        listaFavoritos = new ArrayList<>();
        medicamentoAdapter = new MedicamentoAdapter(listaFavoritos, requireContext());
        binding.recyclerViewFavoritos.setAdapter(medicamentoAdapter);
        binding.recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewFavoritos.setHasFixedSize(true);

        carregarFavoritos();

        return binding.getRoot();
    }

    private void carregarFavoritos() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            Toast.makeText(requireContext(), "Faça login para visualizar seus favoritos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        favoritosRef = FirebaseDatabase.getInstance().getReference("favoritos").child(userId);

        favoritosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaFavoritos.clear();
                for (DataSnapshot medicamentoSnapshot : snapshot.getChildren()) {
                    Medicamento medicamento = medicamentoSnapshot.getValue(Medicamento.class);
                    if (medicamento != null) {
                        listaFavoritos.add(medicamento);
                    }
                }
                medicamentoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Erro ao carregar favoritos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
