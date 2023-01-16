package com.example.courseapp.Helpers;

import static com.example.courseapp.Firebase.Constants.FirebaseConstants.mAuth;

import com.example.courseapp.Firebase.Constants.FirebaseFields;
import com.example.courseapp.Firebase.Constants.FirebaseConstants;
import com.example.courseapp.Firebase.FirebaseRepository;
import com.example.courseapp.Interfaces.Callback;
import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.User;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class UserHelper {
    public static void logInUser(User user, onResult result){
        String response=validateInput(user);

        if (response!=null)result.onError(response);
        else {
            signInUser(user.getEmail(), user.getPassword(), new onResult() {
                @Override
                public void onSuccess() {
                   result.onSuccess();
                }

                @Override
                public void onError(String e) {
                    result.onError(e);
                }
            });

        }

    }
    public static void createNewUser(User user, onResult result){
        String response=validateInput(user);

        if (response!=null)result.onError(response);
        else {
            createUser(user.getEmail(), user.getPassword(), new onResult() {
                    @Override
                    public void onSuccess() {
                        createUserCollectionInFirebase(user, new Callback() {
                            @Override
                            public void onSuccess(Object o) {
                                result.onSuccess();
                            }

                            @Override
                            public void onFailure(Object o) {
                                Exception e=(Exception)o;
                                result.onError(e.getMessage());
                            }
                        });

                    }

                    @Override
                    public void onError(String e) {
                        result.onError(e);
                    }
                });

            }
    }

    public static void createUserCollectionInFirebase(User user, Callback callback) {
        DocumentReference reference= FirebaseConstants.db.collection("users").document(FirebaseConstants.user.getUid());
        FirebaseRepository.setDocument(createUser(user),reference,callback);


    }
    private static Map<String, Object> createUser(User user) {
        Map<String, Object> map=new HashMap<>();
        map.put(FirebaseFields.EMAIL,user.getEmail());
        return map;
    }
    private static String validateInput(User user) {
        if (user.getEmail().isEmpty()) {return "Email is required";}
        if (user.getPassword().isEmpty()) {return"Password is required";}
        if (user.getPassword().length() < 8) {return"Password must be more than 8 characters";}

        return null;
    }

    public static void signOut() {
        if (FirebaseConstants.user != null) {
            mAuth.signOut();
        }
    }
        public static void signInUser(String email, String password, onResult result){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(v -> result.onSuccess())
                    .addOnFailureListener(e -> result.onError(e.getMessage()));
        }
    public static void createUser(String email, String password, onResult result){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(v -> result.onSuccess())
                .addOnFailureListener(e -> result.onError(e.getMessage()));
    }
}
