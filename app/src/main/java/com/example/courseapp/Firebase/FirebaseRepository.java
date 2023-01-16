package com.example.courseapp.Firebase;


import com.example.courseapp.Firebase.Constants.FirebaseConstants;
import com.example.courseapp.Interfaces.Callback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;
public class FirebaseRepository {

    public static void getDocuments(CollectionReference reference,Callback retriever){
        reference.get()
                .addOnCompleteListener(task -> retriever.onSuccess(task))
                .addOnFailureListener(e -> retriever.onFailure(e.getMessage()));

    }
    public static void getDocument(DocumentReference reference, Callback retriever){
        reference.get()
                .addOnCompleteListener(task -> retriever.onSuccess(task))
                .addOnFailureListener(e -> retriever.onFailure(e.getMessage()));

    }
    public static void getDocuments(DocumentReference reference, Callback callback){
        reference.get()
                .addOnSuccessListener(v-> callback.onSuccess(v))
                .addOnFailureListener(e->callback.onFailure(e));
    }
    public static void getDocumentsInCollection(CollectionReference reference, Callback callback){
        reference.get()
                .addOnCompleteListener(v-> callback.onSuccess(v))
                .addOnFailureListener(e->callback.onFailure(e));
    }

    public static void setDocument(Map<String,Object> map, DocumentReference reference, Callback call){
        reference.set(map)
                .addOnSuccessListener(v-> call.onSuccess(v))
                .addOnFailureListener(e->call.onFailure(e));
    }
    public static void setDocumentInCollection(Map<String,Object> map, CollectionReference reference, Callback call){
        reference.document().set(map)
                .addOnSuccessListener(v-> call.onSuccess(v))
                .addOnFailureListener(e->call.onFailure(e));
    }
    public static void checkIfDocumentExists(CollectionReference reference, String key, String object, Callback callback){
        Query query=reference.whereEqualTo(key,object);
        query.get().addOnSuccessListener(v->{

                            if (!v.isEmpty()) callback.onSuccess(v);
                            else callback.onFailure(v);
                        }

                )
                .addOnFailureListener(e -> callback.onFailure(e));
    }
    public static void setDocument(Map details, DocumentReference documentReference, SetOptions merge, Callback callback) {
        documentReference.set(details,merge).addOnCompleteListener(task -> runTaskValidation(task, callback));
    }
    private static void runTaskValidation(Task task, Callback callback) {
        if (task.isSuccessful()) callback.onSuccess(task);
        else callback.onFailure("fail");
    }
    public static DocumentReference createDocumentReference(String path) {
        return FirebaseConstants.db.document(path);
    }



}
