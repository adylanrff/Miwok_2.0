package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

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

        final ArrayList<Word> phrases = new ArrayList<>();
        phrases.add(new Word("Good Morning","Selamat Pagi",R.raw.phrase_my_name_is));
        phrases.add(new Word("Good Evening","Selamat Sore",R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?","Siapa nama Anda?",R.raw.phrase_are_you_coming));
        phrases.add(new Word("My name is Ody","Nama saya Ody",R.raw.phrase_what_is_your_name));
        phrases.add(new Word("Nice to meet you","Senang bertemu dengan kamu",R.raw.phrase_im_feeling_good));



        //declare adapter and view as linear layout
        WordAdapter wordAdapter = new WordAdapter(this,phrases,R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                //get current word
                Word word = phrases.get(i);


                //create audio file

                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,word.getmMediaResourceId());
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
