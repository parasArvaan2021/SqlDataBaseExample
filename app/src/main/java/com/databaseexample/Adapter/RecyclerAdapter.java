package com.databaseexample.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.databaseexample.DataBase.MyDataBase;
import com.databaseexample.MainActivity;
import com.databaseexample.Model.DataModel;
import com.databaseexample.R;
import com.databaseexample.RecyclerInterface;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<DataModel> recycler_list;
    private RecyclerInterface recyclerInterface;


    public RecyclerAdapter(Context context, ArrayList<DataModel> recycler_list,RecyclerInterface recyclerInterface) {
        layoutInflater =LayoutInflater.from(context);
        this.recycler_list = recycler_list;
        this.recyclerInterface=recyclerInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.recycle_raw_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DataModel model=recycler_list.get(position);
            holder.txt_show_name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return recycler_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_show_name;
        ImageView img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_show_name = itemView.findViewById(R.id.raw_txt_showName);
            img_delete = itemView.findViewById(R.id.delete);
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerInterface.removeData(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerInterface.createToast(getAdapterPosition());
                }
            });
        }
    }
}


