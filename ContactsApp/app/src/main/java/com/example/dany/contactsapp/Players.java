package com.example.dany.contactsapp;

/**
 * Created by dany on 1/20/18.
 */

public class Players {

    private int mPlayerNameResId;
    private int mImageResId;
    private String mLocation;
    private float skill;
    private boolean mFound;


    public float getSkill(){return skill;}

    public void setSkill(float poder){this.skill = poder;}


    public int getmPlayerNameResId(){return mPlayerNameResId; }

    public void setmPlayerNameResId(int mPlayerNameResId)
    {
        this.mPlayerNameResId = mPlayerNameResId;
    }

    public int getmImageResId(){ return mImageResId;}

    public void setmImageResId(int mImageResId){this.mImageResId = mImageResId;}

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }



    public Players(int nameResId,int imageResId)
    {
        mPlayerNameResId = nameResId;
        mImageResId = imageResId;
        mLocation = "???";
        skill = 0;

    }


}
