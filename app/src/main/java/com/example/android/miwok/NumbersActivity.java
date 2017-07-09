package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
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
        words.add(new Word("one","siji",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","loro",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","telu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","papat",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","limo",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","enam",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","pitu",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","wolu",R.drawable.number_eight,R.raw.number_eight));


        //declare root view as linear Layout object.
        WordAdapter wordAdapter = new WordAdapter(this, words,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                //get current word
                Word word = words.get(i);


                //create audio file

                mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getmMediaResourceId());
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
