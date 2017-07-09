package com.example.android.miwok;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        if(getActionBar()!=null){
            getActionBar().setDisplayHomeAsUpEnabled(true);};

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("black","hitam",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("yellow","kuning",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("Green","hijau",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("Red","merah",   R.drawable.color_red,R.raw.color_red));
        words.add(new Word("Orange","jingga",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("white","Putih",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("gray","Abu-abu",R.drawable.color_gray,R.raw.color_gray));


        //declare root view as linear Layout object.
        WordAdapter wordAdapter = new WordAdapter(this, words,R.color.category_colors);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                //get current word
                Word word = words.get(i);


                //create audio file

                mMediaPlayer = MediaPlayer.create(ColorsActivity.this,word.getmMediaResourceId());
                //play
                mMediaPlayer.start();


                //release the audio file as soon as it ends.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);


            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
}
