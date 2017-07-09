package com.example.android.miwok;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Delayed;

import static android.R.attr.delay;
import static android.R.attr.text;
import static java.lang.reflect.Modifier.FINAL;

/**
 * Created by Adylan Roaffa on 6/6/2017.
 */

public class WordAdapter extends ArrayAdapter<Word>{

    private int mColorResourceId;
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(MediaPlayer mMediaPlayer) {
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

    public WordAdapter (Activity context, ArrayList<Word> word,int colorResourceId){

        //i dont know what this does
        super(context,0,word);
        mColorResourceId = colorResourceId;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }


        /*
        *
        * */
        final View textContainer = listItemView.findViewById(R.id.text_container);
        final int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);



        //get object in the list position
        final Word currentWord = getItem(position);
        //find TextView in the list_item.xml with the ID default_textView and set the text to the current object
        TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.default_textView);
        defaultTranslation.setText(currentWord.getDefaultTranslation());


        //find TextView in the list_item.xml with the ID miwok_textView and set the text to the current object
        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.miwok_textView);
        miwokTranslation.setText(currentWord.getMiwokTranslation());


        if (currentWord.hasImage()) {
            ImageView image = (ImageView) listItemView.findViewById(R.id.image);
            image.setVisibility(View.VISIBLE);
            image.setImageResource(currentWord.getImage());
        }
        else {
            ImageView image = (ImageView) listItemView.findViewById(R.id.image);
            image.setVisibility(View.GONE);
        }

        return listItemView;
    }

}
