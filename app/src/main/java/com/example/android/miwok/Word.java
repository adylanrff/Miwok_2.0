package com.example.android.miwok;

/**
 * Created by Adylan Roaffa on 6/5/2017.
 */

public class Word {

    /*Default translation*/
    private String mDefaultTranslation;

    /*Miwok translation*/
    private String mMiwokTranslation;

    /*Image*/
    private final int  NO_IMAGE_PROVIDED = -1;
    private int mImage = NO_IMAGE_PROVIDED;

    private int mMediaResourceId;

    /*Two constructor (Without  image)*/
    public Word(String defaultTranslation, String miwokTranslation, int media){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mMediaResourceId = media;
    }

    public Word(String defaultTranslation, String miwokTranslation, int image, int media){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImage = image;
        mMediaResourceId = media;
    }

    /*get Default translation*/
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    /*get Miwok translation*/
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    /*get Miwok translation*/
    public int getImage(){
        return mImage;
    }

    public boolean hasImage(){
        return mImage!=NO_IMAGE_PROVIDED;
    }

    public int getmMediaResourceId(){
        return mMediaResourceId;
    }

}
