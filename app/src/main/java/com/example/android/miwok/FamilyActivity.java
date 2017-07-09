package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;


public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager am;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // stop playback and release
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);


                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                    }
                }
            };

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

            am.abandonAudioFocus(afChangeListener);
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
        words.add(new Word("father","ayah",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","ibu",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("grandfather","kakek",R.drawable.family_grandfather,R.raw.family_grandfather));
        words.add(new Word("grandmother","nenek",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("son","anak laki-laki",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","anak perempuan",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("Older sibling","kakak",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger sibling","adik",R.drawable.family_younger_sister,R.raw.family_younger_sister));

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        //declare root view as linear Layout object.
        WordAdapter wordAdapter = new WordAdapter(this, words,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                Word word = words.get(i);

                // Request audio focus for playback
                int result = am.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //get current word
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // got the audio focus now.

                    //create audio file
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this,word.getmMediaResourceId());
                    //play
                    mMediaPlayer.start();

                    //release the audio file as soon as it ends.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }


            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


}
