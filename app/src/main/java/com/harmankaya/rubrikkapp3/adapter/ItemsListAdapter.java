package com.harmankaya.rubrikkapp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.model.Item;

import java.util.ArrayList;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ViewHolder>
{
    private ArrayList<Item> items = new ArrayList<>();
    private Context context;

    public ItemsListAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsListAdapter.ViewHolder holder, final int position)
    {
        holder.txtItemName.setText(items.get(position).getItemName());
        holder.txtPrice.setText("Price: " + items.get(position).getPrice() + "kr");
        holder.cardItemList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, items.get(position).getItemName(), Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(context)
                .asBitmap()
                .load("https://picsum.photos/200")
                .into(holder.image);

    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtItemName, txtPrice;
        private CardView cardItemList;
        private ImageView image;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.itemName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            cardItemList = itemView.findViewById(R.id.itemListCard);
            image = itemView.findViewById(R.id.itemImage);
        }
    }

    public void setItems(ArrayList<Item> items)
    {
        this.items = items;
        notifyDataSetChanged();
    }
}
