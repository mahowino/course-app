package com.example.courseapp.Firebase.Constants;

import com.google.firebase.firestore.CollectionReference;

public class FirebaseCollections {
    private static final String STORE_PATH="courses";
    private static final String CUSTOMER_PATH="users";


    public static final CollectionReference COURSE_REFERENCE = FirebaseConstants.db.collection(STORE_PATH);
    public static final CollectionReference MY_COURSES_REFERENCE=FirebaseConstants.db.collection(CUSTOMER_PATH);

}
