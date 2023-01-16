package com.example.courseapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseapp.Model.Course;
import com.example.courseapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context mContext;
    List<Course> courseList;



    public CartAdapter(Context mContext, List<Course> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course=courseList.get(position);

        holder.productName.setText(course.getTitle());
        holder.productDescription.setText(course.getDescription());
        holder.subtract.setOnClickListener(view -> subtractNumberOfGoodInCart(position));

    }


    private void subtractNumberOfGoodInCart(int pos ) {

        courseList.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productDescription,productNumber;
        Button add,subtract;
        ImageView productImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.txtGoodNameCart);
            productDescription=itemView.findViewById(R.id.txtGoodDescriptionCart);
            subtract=itemView.findViewById(R.id.btnReduceItemCount);
            productImageView=itemView.findViewById(R.id.productImage);
        }
    }
}
