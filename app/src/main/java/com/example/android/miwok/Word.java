package com.example.android.miwok;

public class Word {
    /* Why we are creating this java file because previosly we created a list_item.xml with two text views,
    *we also displayed the TextView with a text.
    * We cannot do that since we are using an adapter it sends data depending upon user so we cannot use hardcoded values there
    * Instead of that create a java file i.e custom java class with two fields as mentioned below.
     */
    private String mDefaultTranslation;
    private String mMewakTranslation;
    private int mAudioResourceId;
    private int mImageResourceId=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    public Word(String defaultTranslation,String mewakTranslation,int imageResourceId,int audioResourceId)
    {
        mDefaultTranslation=defaultTranslation;
        mMewakTranslation=mewakTranslation;
        mImageResourceId=imageResourceId;
        mAudioResourceId=audioResourceId;
    }
    public Word(String defaultTranslation,String mewakTranslation,int audioResourceId)
    {
        mDefaultTranslation=defaultTranslation;
        mMewakTranslation=mewakTranslation;
        mAudioResourceId=audioResourceId;
    }
    public String getDefaultTranslation()
    {
        return mDefaultTranslation;
    }
    public String getMewakTranslation()
    {
        return mMewakTranslation;
    }
    public int getImageResourceId()
    {
        return mImageResourceId;
    }
    public int getAudioResourceId()
    {
        return mAudioResourceId;
    }

    public boolean hasImage()
    {
        return mImageResourceId!=NO_IMAGE_PROVIDED;
    }


}
