package com.example.idea.customviewcollection.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.model.PreviewInfo;

import java.util.List;

/**
 * Created by idea on 2017/1/4.
 */

public class PreviewInfoAdapter extends RecyclerView.Adapter<PreviewInfoAdapter.MyViewHolder> {

    private Context context;
    private List<PreviewInfo> datas;
    private OnClickListener listener;
    public PreviewInfoAdapter(Context context, List<PreviewInfo> datas) {
        this.context=context;
        this.datas=datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_previewinfo,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_text.setText(datas.get(position).getName());
        holder.cv_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(position,datas.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_text;
         private final CardView cv_cardview;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_text= (TextView) itemView.findViewById(R.id.tv_text);
            cv_cardview= (CardView) itemView.findViewById(R.id.cv_cardview);
        }
    }


   public interface OnClickListener{
        void OnClick(int position,PreviewInfo previewInfo);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.listener=onClickListener;
    }

}
