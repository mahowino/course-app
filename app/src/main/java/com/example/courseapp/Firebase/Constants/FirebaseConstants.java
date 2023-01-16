package com.example.courseapp.Firebase.Constants;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConstants {

    public static final FirebaseFirestore db=FirebaseFirestore.getInstance();
    public static final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public static final FirebaseUser user=mAuth.getCurrentUser();

}
