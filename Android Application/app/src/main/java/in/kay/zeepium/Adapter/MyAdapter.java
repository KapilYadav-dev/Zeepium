package in.kay.zeepium.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import in.kay.zeepium.Model.ResponseModel;
import in.kay.zeepium.R;
import in.kay.zeepium.Views.Player;
import in.kay.zeepium.Views.Scan;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<ResponseModel> list;
    Context context;

    public MyAdapter(List<ResponseModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getTitle());
        holder.tvDate.setText(list.get(position).getDate());
        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvName=itemView.findViewById(R.id.tvName);

        }
    }
}
