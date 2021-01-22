package in.kay.zeepium.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import in.kay.zeepium.Api.RetrofitClient;
import in.kay.zeepium.Model.ResponseModel;
import in.kay.zeepium.R;
import in.kay.zeepium.Views.Player;
import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
      /*  holder.ivDelete.setOnClickListener(view -> {
            list.remove(position);
            notifyDataSetChanged();
            Paper.book().write("History", list);

        });
       */
        holder.itemView.setOnClickListener(view -> {
            DoWork(list.get(position).getUrl(), list.get(position).getTitle());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            ivDelete = itemView.findViewById(R.id.imageView4);
        }
    }

    public void DoWork(String url, String title) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().search(url);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    ResponseModel responseModel;
                    String json = null;
                    try {
                        json = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    responseModel = gson.fromJson(json, ResponseModel.class);
                    String streamUrl = responseModel.getUrl();
                    Intent intent = new Intent(context, Player.class);
                    intent.putExtra("url", streamUrl);
                    intent.putExtra("title", title);
                    context.startActivity(intent);
                } else if (response.code() == 404) {
                    Toast.makeText(context, "Desired content isn't swappable : (", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Server is down...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
