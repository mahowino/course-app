package com.example.courseapp.Helpers;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.courseapp.Firebase.Constants.FirebaseCollections;
import com.example.courseapp.Firebase.Constants.FirebaseConstants;
import com.example.courseapp.Firebase.Constants.FirebaseFields;
import com.example.courseapp.Interfaces.getItemsCallback;
import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseHelper {
    public static void getAllCourses(List<Course> courseList,getItemsCallback callback) {
        FirebaseCollections.COURSE_REFERENCE.get().addOnSuccessListener(snapshot -> {
            List<Course> courses=new ArrayList<>();
            for (DocumentSnapshot snapshotDocs:snapshot){
                Course course=new Course();
                    course.setCourseID(snapshotDocs.getId());
                    course.setTitle(snapshotDocs.getString(FirebaseFields.COURSE_TITLE));
                    course.setDescription(snapshotDocs.getString(FirebaseFields.COURSE_DESCRIPTION));
                    course.setDepartment(snapshotDocs.getString(FirebaseFields.COURSE_DEPARTMENT));
                    course.setGrade(snapshotDocs.getString(FirebaseFields.GRADE));
                    courses.add(course);


            }
            for ( int x=0;x<courseList.size();x++){
                for (int y=0;y<courses.size();y++){
                    if (courses.get(y).getCourseID().equals(courseList.get(x).getCourseID()))
                        courses.remove(y);

                }
            }

            callback.onSuccess(courses);
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            callback.onError();
        });
    }
    public static void getMyCourses(getItemsCallback callback) {
        FirebaseCollections.MY_COURSES_REFERENCE
                .document(FirebaseConstants.user.getUid())
                .collection("courses")
                .get().addOnSuccessListener(snapshot -> {

            List<Course> courses=new ArrayList<>();
            for (DocumentSnapshot snapshotDocs:snapshot){
                Course course=new Course();
                course.setCourseID(snapshotDocs.getId());
                course.setTitle(snapshotDocs.getString(FirebaseFields.COURSE_TITLE));
                course.setDescription(snapshotDocs.getString(FirebaseFields.COURSE_DESCRIPTION));
                course.setDepartment(snapshotDocs.getString(FirebaseFields.COURSE_DEPARTMENT));
                course.setGrade(snapshotDocs.getString(FirebaseFields.GRADE));
                courses.add(course);
            }
            callback.onSuccess(courses);
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            callback.onError();
        });
    }
    public static void addCoursesToUser(List<Course> courses, onResult onresult){
        // Get a new write batch
        WriteBatch batch = FirebaseConstants.db.batch();

        for (Course course:courses) {
            DocumentReference reference= FirebaseCollections.MY_COURSES_REFERENCE
                    .document(FirebaseConstants.user.getUid())
                    .collection("courses")
                    .document(course.getCourseID());

            batch.set(reference,createCourse(course));
        }

        batch.commit().addOnSuccessListener(unused ->onresult.onSuccess() ).
                addOnFailureListener(e -> {
                    e.printStackTrace();
                    onresult.onError(e.getMessage());
                });

    }

    private static Object createCourse(Course course) {
        Map<String,Object> map=new HashMap<>();
        map.put(FirebaseFields.COURSE_TITLE,course.getTitle());
        map.put(FirebaseFields.COURSE_DEPARTMENT,course.getDepartment());
        map.put(FirebaseFields.COURSE_DESCRIPTION,course.getDescription());
        map.put(FirebaseFields.GRADE,course.getGrade());
        return map;
    };

    public static void gradeCourse(Course course, onResult onResult) {
      FirebaseCollections.MY_COURSES_REFERENCE
                .document(FirebaseConstants.user.getUid())
                .collection("courses")
                .document(course.getCourseID())
              .set(createCourse(course)) .addOnSuccessListener(unused ->onResult.onSuccess() ).
                addOnFailureListener(e -> {
                    e.printStackTrace();
                    onResult.onError(e.getMessage());
                });
    }


}
