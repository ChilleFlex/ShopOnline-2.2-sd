package com.example.shoponlinefin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponlinefin.Interface.ItemClickListner;
import com.example.shoponlinefin.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{


    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.prod_image);
        txtProductName = (TextView) itemView.findViewById(R.id.prod_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.prod_desc);
        txtProductPrice = (TextView) itemView.findViewById(R.id.prod_price);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
