package com.example.medicaai_app.repository;

import android.content.Context;
import android.widget.Toast;

import com.example.medicaai_app.model.Medicamento;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreRepository {
    private final FirebaseFirestore db;
    private final Context context;

    // Construtor que recebe o contexto da Activity
    public FirestoreRepository(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    // Método para salvar o medicamento no Firestore
    public void salvarMedicamento(Medicamento medicamento) {
        db.collection("medicamentos")
                .add(medicamento)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Medicamento salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(context, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
