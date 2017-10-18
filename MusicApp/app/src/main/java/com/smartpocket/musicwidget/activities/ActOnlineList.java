package com.smartpocket.musicwidget.activities;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smartpocket.musicwidget.R;
import com.smartpocket.musicwidget.model.OnlneMusicModel;

import java.io.IOException;
import java.util.ArrayList;

public class ActOnlineList extends AppCompatActivity {


    RecyclerView recyleview;
    ArrayList<OnlneMusicModel> arrMusicModel = new ArrayList<OnlneMusicModel>();
    MusicAdapter musicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_online_musiclist);


        initialize();


    }



    public void initialize(){
        try{
            recyleview = (RecyclerView) findViewById(R.id.recyleview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActOnlineList.this);
            recyleview.setLayoutManager(linearLayoutManager);
            recyleview.setNestedScrollingEnabled(false);  //recycleview smooth scrool inside nestedScrollview
            recyleview.setHasFixedSize(true);


            //Golmall -- https://www.freshmaza.net/beta/files/17735/Golmaal%20Again%20(2017)%20Mp3%20songs/1/1.html
            String commontPath = "/storage/emulated/0/Xender";
            String link1 = "https://www.fmdload.xyz/upload_file/Music/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Baadshaho%20(2017)%20Mp3%20songs/09%20Chor%20Aavega%20-%20Baadshaho%20190Kbps.mp3";
            String link2 = "https://www.fmdload.xyz/upload_file/Music/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Baadshaho%20(2017)%20Mp3%20songs/01%20Mere%20Rashke%20Qamar%20-%20Baadshaho%20190Kbps.mp3";
            String link3 = "https://www.fmdload.xyz/upload_file/Music/Latest%20Bollywood%20Hindi%20Mp3%20Songs%20-%202017/Baadshaho%20(2017)%20Mp3%20songs/02%20Piya%20More%20-%20Baadshaho%20-%20190Kbps.mp3";

            String id[] = {"1", "2", "3"};
            String name[] = {"Chor Aavega", "Mere Rashke Qamar", " Piya More"};
            String path[] = {link1, link2, link3};
            //String path[] = {commontPath+"/GolmaalAgain.mp3", commontPath+"/HumNahiSudhrenge.mp3", commontPath+"/ItnaSannataKyunHai.mp3"};


            for(int i=0; i<id.length; i++){
                arrMusicModel.add(new OnlneMusicModel(id[i], name[i], path[i]));
            }


            musicAdapter = new MusicAdapter(ActOnlineList.this, arrMusicModel);
            recyleview.setAdapter(musicAdapter);
            recyleview.setItemAnimator(new DefaultItemAnimator());

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.VersionViewHolder> {
        ArrayList<OnlneMusicModel> musicArray;
        Context mContext;


        public MusicAdapter(Context context, ArrayList<OnlneMusicModel> musicArray) {
            this.musicArray = musicArray;
            mContext = context;
        }

        @Override
        public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_list_row, viewGroup, false);
            VersionViewHolder viewHolder = new VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
            try {

                versionViewHolder.textTitle.setText(musicArray.get(i).name);
                versionViewHolder.textTitle.setTextColor(Color.BLACK);
                versionViewHolder.textArtist.setText("");
                versionViewHolder.textDuration.setText("");

                Log.d("=====ONline List======", "name: "+musicArray.get(i).name);
                Log.d("=====ONline List======", "path: "+musicArray.get(i).path);

                versionViewHolder.textTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MediaPlayer mPlayer =  new MediaPlayer();
                        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                        try {
                            mPlayer.setDataSource(musicArray.get(i).path);
                        } catch (IllegalArgumentException e) {
                            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                        } catch (SecurityException e) {
                            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                        } catch (IllegalStateException e) {
                            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            mPlayer.prepare();
                        } catch (IllegalStateException e) {
                            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                        }


                        mPlayer.start();



                       /* Song obj = new Song(Long.parseLong(musicArray.get(i).id), musicArray.get(i).path, "ArtistName", 3);

                        Intent serviceIntent = new Intent(ActOnlineList.this, MusicService.class);
                        serviceIntent.setAction(MusicWidget.ACTION_JUMP_TO);
                        serviceIntent.putExtra("song", obj);
                        ActOnlineList.this.startService(serviceIntent);*/
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return musicArray.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {
            
            TextView textTitle, textArtist, textDuration;

            public VersionViewHolder(View itemView) {
                super(itemView);

                textTitle = (TextView) itemView.findViewById(R.id.textTitle);
                textArtist = (TextView) itemView.findViewById(R.id.textArtist);
                textDuration = (TextView) itemView.findViewById(R.id.textDuration);
            }

        }
    }
}
