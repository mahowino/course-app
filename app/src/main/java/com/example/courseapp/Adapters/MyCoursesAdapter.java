package com.example.courseapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseapp.Interfaces.onCardItemClick;
import com.example.courseapp.Model.Course;
import com.example.courseapp.R;


import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.HolderView> {
    List<Course> courses;
    Context mContext;
    com.example.courseapp.Interfaces.onCardItemClick onCardItemClick;

    public MyCoursesAdapter(List<Course> courses, Context mContext, onCardItemClick onCardItemClick) {
        this.courses = courses;
        this.mContext = mContext;
        this.onCardItemClick=onCardItemClick;
    }

    @NonNull
    @Override
    public MyCoursesAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(mContext).inflate(R.layout.category_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCoursesAdapter.HolderView holder, int position) {

        holder.courseName.setText(courses.get(position).getTitle());
        holder.grade.setText("Grade: "+courses.get(position).getGrade());
        holder.cardView.setOnClickListener(view -> onCardItemClick.onClick(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView courseName,grade;
        CardView cardView;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            courseName =itemView.findViewById(R.id.txtCategoryNames);
            cardView=itemView.findViewById(R.id.card_item);
            imageView=itemView.findViewById(R.id.imageCard);
            grade=itemView.findViewById(R.id.txtGrade);
        }
    }
}
