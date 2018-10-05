package com.vs.lyricsmusicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RagasActivity extends AppCompatActivity {
    private List<RagasModel> ragasModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RagasAdapter ragasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragas);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ragasAdapter = new RagasAdapter(ragasModelList, asynResult);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ragasAdapter);
        //This function is called for setting the title and subtitle in RecyclerView
        prepareRagasData();
    }

    AsynResult<RagasModel> asynResult = new AsynResult<RagasModel>() {
        @Override
        public void success(RagasModel ragasModel) {
            Intent playIntent = new Intent(RagasActivity.this, PlayActivity.class);
            startActivity(playIntent);
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passVal(String message) {

        }

        @Override
        public void passMediaDuration(double startDurationTime, double finalDurationTime) {

        }
    };

    private void prepareRagasData(){
        RagasModel ragasModel = new RagasModel("Patake", "Sunanda Sharma");
        ragasModelList.add(ragasModel);

        ragasModel = new RagasModel("Raga Majh", "Ang 94-150|8 Banis");
        ragasModelList.add(ragasModel);

        ragasModel = new RagasModel("Raga Gauri", "Ang 151-346|16 Banis");
        ragasModelList.add(ragasModel);

        ragasModel = new RagasModel("Raga Asa", "Ang 347-488|10 Banis");
        ragasModelList.add(ragasModel);

        ragasModel = new RagasModel("Raga Gujari", "Ang 489-526|4 Banis");
        ragasModelList.add(ragasModel);

        ragasModel = new RagasModel("Raga Devgandhari", "Ang 527-536|1 Bani");
        ragasModelList.add(ragasModel);

        ragasAdapter.notifyDataSetChanged();
    }

}
