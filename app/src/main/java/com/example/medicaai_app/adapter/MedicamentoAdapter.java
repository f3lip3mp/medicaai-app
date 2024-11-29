package com.example.medicaai_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicaai_app.databinding.MedicamentoItemBinding;
import com.example.medicaai_app.model.Medicamento;

import java.util.ArrayList;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {
    private final ArrayList<Medicamento> medicamentoList;
    private final Context context;

    public MedicamentoAdapter(ArrayList<Medicamento> medicamentoList, Context context) {
        this.medicamentoList = medicamentoList;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicamentoItemBinding listItem;
        listItem = MedicamentoItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MedicamentoViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        holder.binding.imgMedicamento.setBackgroundResource(medicamentoList.get(position).getMedicamentoImg());
        holder.binding.txtMedicamentoNome.setText(medicamentoList.get(position).getMedicamentoNome());
        holder.binding.txtMedicamentoDescricao.setText(medicamentoList.get(position).getMedicamentoDescricao());
        holder.binding.txtMedicamentoIndicacao.setText(medicamentoList.get(position).getMedicamentoIndicacao());
    }

    @Override
    public int getItemCount() {
        return medicamentoList.size();
    }

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder{
        MedicamentoItemBinding binding;
        public MedicamentoViewHolder(MedicamentoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
