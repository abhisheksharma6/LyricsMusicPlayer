package com.vs.lyricsmusicplayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RagasAdapter extends RecyclerView.Adapter<RagasAdapter.MyViewHolder> {
    private List<RagasModel> ragasModelList;
    private AsynResult<RagasModel> asynResult;

    public RagasAdapter(List<RagasModel> ragasModelList, AsynResult<RagasModel> asynResult){
        this.ragasModelList = ragasModelList;
        this.asynResult = asynResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ragas_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
         RagasModel ragasModel = ragasModelList.get(position);
         holder.title.setText(ragasModel.getTitle());
         holder.subTitle.setText(ragasModel.getSubTitle());
         holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 asynResult.success(ragasModelList.get(position));
             }
         });
    }


    @Override
    public int getItemCount() {
        return ragasModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subTitle;
        public RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

}
