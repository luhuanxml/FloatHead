package com.luhuan.floatinghead;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class FloatAdapter extends RecyclerView.Adapter<FloatAdapter.FloadViewHolder> {

    private List<String> list;
    private Context context;

    public FloatAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public FloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.float_item,parent,false);
        return new FloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FloadViewHolder holder, int position) {
        holder.item.setText(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class FloadViewHolder extends RecyclerView.ViewHolder{
        Button item;

        public FloadViewHolder(View itemView) {
            super(itemView);
            item= (Button) itemView.findViewById(R.id.item);
        }
    }
}
