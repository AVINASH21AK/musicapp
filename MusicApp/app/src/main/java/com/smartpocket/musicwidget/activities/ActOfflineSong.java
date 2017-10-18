package com.smartpocket.musicwidget.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.smartpocket.musicwidget.App;
import com.smartpocket.musicwidget.MusicWidget;
import com.smartpocket.musicwidget.R;
import com.smartpocket.musicwidget.backend.SongListLoader;
import com.smartpocket.musicwidget.model.Song;
import com.smartpocket.musicwidget.service.MusicService;

public class ActOfflineSong extends AppCompatActivity implements SearchView.OnQueryTextListener{

    String TAG = "ActOfflineSong";


    private SongListAdapter adapter;
    public static LinearLayout llPlayer, layoutTextViews;
    ListView list;
    public static ImageView ivStopMusic, button_play_pause, button_shuffle, button_prev, button_next;
    public static TextView textViewTitle, textViewArtist, textViewDuration;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_song_list));
        getSupportActionBar().setLogo(R.drawable.ic_launcher);

        handleIntent(getIntent());

        initialize();
        setAdapter();
        setClickEvent();

    }


    public void initialize() {
        try {


            list = (ListView) findViewById(R.id.listView);
            llPlayer = (LinearLayout) findViewById(R.id.llPlayer);
            layoutTextViews = (LinearLayout) findViewById(R.id.layoutTextViews);

            ivStopMusic = (ImageView) findViewById(R.id.ivStopMusic);
            button_play_pause = (ImageView) findViewById(R.id.button_play_pause);
            button_prev = (ImageView) findViewById(R.id.button_prev);
            button_next = (ImageView) findViewById(R.id.button_next);
            button_shuffle = (ImageView) findViewById(R.id.button_shuffle);

            textViewTitle = (TextView) findViewById(R.id.textViewTitle);
            textViewArtist = (TextView) findViewById(R.id.textViewArtist);
            textViewDuration = (TextView) findViewById(R.id.textViewDuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdapter() {
        try {
            Cursor listCursor = SongListLoader.getInstance(this).getCursor();
            adapter = new SongListAdapter(this, listCursor);
            adapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    return SongListLoader.getInstance(ActOfflineSong.this).getFilteredCursor(constraint);
                }
            });

            list.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClickEvent() {
        try {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Song song = ((SongListAdapter) parent.getAdapter()).getSong(position);
                    App.showLog("SongListactivity click", song.toString());

                    if (llPlayer.getVisibility() != View.VISIBLE) {
                        llPlayer.setVisibility(View.VISIBLE);

                    }

                    Intent serviceIntent = new Intent(ActOfflineSong.this, MusicService.class);
                    serviceIntent.setAction(MusicWidget.ACTION_JUMP_TO);
                    serviceIntent.putExtra("song", song);
                    ActOfflineSong.this.startService(serviceIntent);
                }
            });


            button_play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent i1 = new Intent(ActOfflineSong.this, MusicService.class);
                        i1.setAction(MusicWidget.ACTION_PLAY_PAUSE);
                        ActOfflineSong.this.startService(i1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });


            ivStopMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llPlayer.setVisibility(View.GONE);

                    Intent i1 = new Intent(ActOfflineSong.this, MusicService.class);
                    i1.setAction(MusicWidget.ACTION_STOP);
                    ActOfflineSong.this.startService(i1);
                }
            });


            button_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent i1 = new Intent(ActOfflineSong.this, MusicService.class);
                        i1.setAction(MusicWidget.ACTION_PREVIOUS);
                        ActOfflineSong.this.startService(i1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

            button_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i1 = new Intent(ActOfflineSong.this, MusicService.class);
                        i1.setAction(MusicWidget.ACTION_NEXT);
                        ActOfflineSong.this.startService(i1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            button_shuffle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1 = new Intent(ActOfflineSong.this, MusicService.class);
                    i1.setAction(MusicWidget.ACTION_SHUFFLE);
                    ActOfflineSong.this.startService(i1);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SongListAdapter extends CursorAdapter implements SectionIndexer {
        private final AlphabetIndexer mAlphabetIndexer;

        public SongListAdapter(Activity context, Cursor c) {
            super(context, c, false);

            mAlphabetIndexer = new AlphabetIndexer(c,
                    c.getColumnIndex(MediaStore.Audio.Media.ARTIST),
                    " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ");
            mAlphabetIndexer.setCursor(c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.song_list_row, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvArtist   = (TextView) view.findViewById(R.id.textArtist);
            TextView tvTitle    = (TextView) view.findViewById(R.id.textTitle);
            TextView tvDuration = (TextView) view.findViewById(R.id.textDuration);

            String artist   = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String title    = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            Song song = new Song(0, title, artist, duration);

            tvArtist.setText(artist);
            tvTitle.setText(title);
            tvDuration.setText(song.getDurationStr());
        }

        public Song getSong(int position) {
            Cursor cursor = (Cursor) getItem(position);
            String artist   = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String title    = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            return new Song(0, title, artist, duration);
        }

        @Override
        public void changeCursor(Cursor cursor) {
            super.changeCursor(cursor);
            mAlphabetIndexer.setCursor(cursor);
        }

        @Override
        public Object[] getSections() {
            return mAlphabetIndexer.getSections();
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            return mAlphabetIndexer.getPositionForSection(sectionIndex);
        }

        @Override
        public int getSectionForPosition(int position) {
            return mAlphabetIndexer.getSectionForPosition(position);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.song_list, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchText = intent.getStringExtra(SearchManager.QUERY);

            App.showLog("ActOfflineSong", "Searched for: " + searchText);
            adapter.getFilter().filter(searchText);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        App.showLog("ActOfflineSong", "Searched for: " + newText);
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
