package com.example.courseapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.Course;
import com.example.courseapp.R;

import java.util.List;

public class CourseItemsAdapter extends RecyclerView.Adapter<CourseItemsAdapter.ViewHolder> {

    Context mContext;
    List<Course> courses;
    com.example.courseapp.Interfaces.onCardItemClick onCardItemClick;


    public CourseItemsAdapter(Context mContext, List<Course> courses, com.example.courseapp.Interfaces.onCardItemClick onCardItemClick) {
        this.mContext = mContext;
        this.courses = courses;
        this.onCardItemClick = onCardItemClick;
    }

    @NonNull
    @Override
    public CourseItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.course_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseItemsAdapter.ViewHolder holder, int position) {
        Course course=courses.get(position);

        holder.productName.setText(course.getTitle());
        holder.productDescription.setText(course.getDepartment());
        holder.addToCart.setOnClickListener(view -> onCardItemClick.onClick(position));

    }

    private void showDialog() {
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription;
        ImageView addToCart,productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtCourseTitle);
            productDescription=itemView.findViewById(R.id.txtCourseDescription);
            addToCart=itemView.findViewById(R.id.btnAddToCart);
            productImage=itemView.findViewById(R.id.productImage);
        }
    }
}
