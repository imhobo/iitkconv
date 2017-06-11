package com.aps.iitkconv.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by imhobo on 31/3/17.
 */

public class DataObject {
    private ArrayList<String> mTexts = new ArrayList<String>();
    private Bitmap mImg;

    public DataObject(String... texts) {
        for (String text : texts)
            mTexts.add(text);
    }

    public DataObject(Bitmap img, String... texts) {
        mImg = img;
        for (String text : texts)
            mTexts.add(text);
    }


    public Bitmap getmImg() {
        return mImg;
    }

    public void setmImg(Bitmap img) {
        mImg = img;
    }

    public String getmText1() {
        String value = "";
        try {
            value = mTexts.get(0);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText1(String mText1) {
        mTexts.set(0, mText1);
    }

    public String getmText2() {
        String value = "";
        try {
            value = mTexts.get(1);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText2(String mText2) {
        mTexts.set(1, mText2);
    }

    public String getmText3() {
        String value = "";
        try {
            value = mTexts.get(2);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText3(String mText3) {
        mTexts.set(2, mText3);
    }

    //Ideally do this everywhere
    public String getmText4() {
        String value = "";
        try {
            value = mTexts.get(3);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText4(String mText4) {
        mTexts.set(3, mText4);

    }

    public String getmText5() {
        String value = "";
        try {
            value = mTexts.get(4);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText5(String mText5) {
        mTexts.set(4, mText5);

    }

    public String getmText6() {
        String value = "";
        try {
            value = mTexts.get(5);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText6(String mText6) {
        mTexts.set(5, mText6);
    }

    public String getmText7() {

        String value = "";
        try {
            value = mTexts.get(6);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText7(String mText7) {
        mTexts.set(6, mText7);

    }

    public String getmText8() {
        return mTexts.get(7) == null ? "" : mTexts.get(7);
    }

    public void setmText8(String mText8) {
        mTexts.set(7, mText8);

    }

    public String getmText9() {

        String value = "";
        try {
            value = mTexts.get(8);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
        return value;
    }

    public void setmText9(String mText9) {
        mTexts.set(8, mText9);

    }
}
