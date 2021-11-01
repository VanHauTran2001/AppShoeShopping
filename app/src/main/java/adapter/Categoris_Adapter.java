package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appprojectcuoikhoa.AdidasActivity;
import com.example.appprojectcuoikhoa.GucciActivity;
import com.example.appprojectcuoikhoa.MainActivity;
import com.example.appprojectcuoikhoa.NikeActivity;
import com.example.appprojectcuoikhoa.PumaActivity;
import com.example.appprojectcuoikhoa.R;
import com.example.appprojectcuoikhoa.VansActivity;

import java.util.List;
import model.Categories;
import model.Nike;

public class Categoris_Adapter extends RecyclerView.Adapter<Categoris_Adapter.ViewHolder>{
    private Context context;
    List<Categories> categoriesList;

    public Categoris_Adapter(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_categories,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Categoris_Adapter.ViewHolder holder, int position) {
        holder.categoryName.setText(categoriesList.get(position).getNameCategories());
        Glide.with(context).load(categoriesList.get(position).getImageCategories()).into(holder.categoryPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context,NikeActivity.class);
                        intent.putExtra("name",categoriesList.get(position).getNameCategories());
                        intent.putExtra("image",categoriesList.get(position).getImageCategories());
                        context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryPic;
        TextView categoryName;
        View constraintLayout;
        public ViewHolder( View itemView) {
            super(itemView);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            categoryName = itemView.findViewById(R.id.categoryName);
            constraintLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
