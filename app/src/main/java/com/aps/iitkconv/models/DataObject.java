package com.aps.iitkconv.models;

import java.util.ArrayList;

/**
 * Created by imhobo on 31/3/17.
 */

public class DataObject
{
    private ArrayList<String> mTexts = new ArrayList<String>();

    public DataObject (String... texts)
    {
        for(String text: texts)
            mTexts.add(text);
    }

    public String getmText1() {
        return mTexts.get(0) == null ? "" : mTexts.get(0);
    }

    public void setmText1(String mText1) {
        mTexts.set(0,mText1);
    }

    public String getmText2() {
        return mTexts.get(1) == null ? "" : mTexts.get(1);
    }

    public void setmText2(String mText2) {
        mTexts.set(1,mText2);
    }

    public String getmText3() {
        return mTexts.get(2) == null ? "" : mTexts.get(2);
    }

    public void setmText3(String mText3) {
        mTexts.set(2,mText3);
    }

    //Ideally do this everywhere
    public String getmText4()
    {
        String value = "";
        try
        {
            value =  mTexts.get(3);
        }catch (IndexOutOfBoundsException e)
        {
            return value;
        }
        return value;
    }

    public void setmText4(String mText4) {
        mTexts.set(3,mText4);

    }

    public String getmText5() {
        return mTexts.get(4) == null ? "" : mTexts.get(4);
    }

    public void setmText5(String mText5) {
        mTexts.set(4,mText5);

    }

    public String getmText6() {
        return mTexts.get(5) == null ? "" : mTexts.get(5);
    }

    public void setmText6(String mText6) {
        mTexts.set(5,mText6);
    }

    public String getmText7() {
        return mTexts.get(6) == null ? "" : mTexts.get(6);
    }

    public void setmText7(String mText7) {
        mTexts.set(6,mText7);

    }

    public String getmText8() {
        return mTexts.get(7) == null ? "" : mTexts.get(7);
    }

    public void setmText8(String mText8) {
        mTexts.set(7,mText8);

    }
}
