package com.example.medicaai_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.medicaai_app.MedicamentoDialogFragment;
import com.example.medicaai_app.databinding.MedicamentoItemBinding;
import com.example.medicaai_app.model.Medicamento;

import java.util.ArrayList;;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicaai_app.databinding.MedicamentoItemBinding;
import com.example.medicaai_app.model.Medicamento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {
    private final ArrayList<Medicamento> medicamentoList;
    private final Context context;

    public MedicamentoAdapter(ArrayList<Medicamento> medicamentoList, Context context) {
        this.medicamentoList = (medicamentoList != null) ? medicamentoList : new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicamentoItemBinding listItem;
        listItem = MedicamentoItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MedicamentoViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento medicamento = medicamentoList.get(position);

        // Bind data
        holder.binding.imgMedicamento.setBackgroundResource(medicamento.getMedicamentoImg());
        holder.binding.txtMedicamentoNome.setText(medicamento.getMedicamentoNome());
        holder.binding.txtMedicamentoDescricao.setText(medicamento.getMedicamentoDescricao());
        holder.binding.txtMedicamentoIndicacao.setText(medicamento.getMedicamentoIndicacao());

        // Set click listener for "Curtir" button
        holder.binding.btnCurtir.setOnClickListener(v -> salvarFavoritoNoFirebase(medicamento));

        holder.itemView.setOnClickListener(v -> {
            FragmentActivity activity = (FragmentActivity) context;
            MedicamentoDialogFragment dialog = new MedicamentoDialogFragment(medicamento);
            dialog.show(activity.getSupportFragmentManager(), "MedicamentoDialog");
        });
    }

    @Override
    public int getItemCount() {
        return medicamentoList.size();
    }

    public void updateMedicamentos(List<Medicamento> medicamentos) {
    }

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        MedicamentoItemBinding binding;

        public MedicamentoViewHolder(MedicamentoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Method to save favorite in Firebase
    private void salvarFavoritoNoFirebase(Medicamento medicamento) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(context, "É necessário estar logado para salvar favoritos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        DatabaseReference favoritosRef = FirebaseDatabase.getInstance()
                .getReference("favoritos")
                .child(userId);

        favoritosRef.child(medicamento.getMedicamentoNome()).setValue(medicamento)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Medicamento adicionado aos favoritos!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Erro ao salvar favorito: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


}
