package kz.growit.citysearch.Adapters;

/**
 * Created by Талгат on 10.03.2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import kz.growit.citysearch.Models.Category;

public class CategoriesRVAdapter extends RecyclerView.Adapter<CategoriesRVAdapter.CategoryViewHolder>{
    private Context context;
    private RealmResults<Category> categories;

    public CategoriesRVAdapter(Context context, RealmResults<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryName;


        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
