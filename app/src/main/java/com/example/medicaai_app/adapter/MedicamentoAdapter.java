package com.example.medicaai_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicaai_app.MedicamentoDialogFragment;
import com.example.medicaai_app.R;
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

        // Verifica se o medicamento já está favoritado ao carregar a lista
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            DatabaseReference favoritosRef = FirebaseDatabase.getInstance()
                    .getReference("favoritos")
                    .child(userId);

            favoritosRef.child(medicamento.getMedicamentoNome()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    // Medicamento já está favoritado, atualiza o ícone para o coração preenchido
                    holder.binding.btnCurtir.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_heart_fill, 0, 0);
                } else {
                    // Medicamento não está favoritado, exibe o ícone vazio
                    holder.binding.btnCurtir.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_heart_line, 0, 0);
                }
            });
        }

        // Set click listener for "Curtir" button
        holder.binding.btnCurtir.setOnClickListener(v ->
                salvarFavoritoNoFirebase(medicamento, holder.binding.btnCurtir)
        );

        // Open MedicamentoDialogFragment on item click
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
        this.medicamentoList.clear();
        this.medicamentoList.addAll(medicamentos);
        notifyDataSetChanged();
    }

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        MedicamentoItemBinding binding;

        public MedicamentoViewHolder(MedicamentoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Method to save favorite in Firebase
    private void salvarFavoritoNoFirebase(Medicamento medicamento, android.widget.Button btnCurtir) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(context, "É necessário estar logado para salvar favoritos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        DatabaseReference favoritosRef = FirebaseDatabase.getInstance()
                .getReference("favoritos")
                .child(userId);

        // Verifica se o medicamento já está favoritado
        favoritosRef.child(medicamento.getMedicamentoNome()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                // Se o medicamento já está nos favoritos, remove
                favoritosRef.child(medicamento.getMedicamentoNome()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Medicamento removido dos favoritos!", Toast.LENGTH_SHORT).show();
                            btnCurtir.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_heart_line, 0, 0); // Ícone vazio
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(context, "Erro ao remover favorito: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                // Se o medicamento não está nos favoritos, salva
                favoritosRef.child(medicamento.getMedicamentoNome()).setValue(medicamento)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Medicamento adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
                            btnCurtir.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_heart_fill, 0, 0); // Ícone preenchido
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(context, "Erro ao salvar favorito: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }


}
