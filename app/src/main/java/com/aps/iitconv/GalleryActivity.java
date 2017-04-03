package com.aps.iitconv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by pulkitkariryaa on 02/04/17.
 */

public class GalleryActivity extends AppCompatActivity
{

    private final Integer image_ids[] = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,
            R.drawable.img11,
            R.drawable.img12,
            R.drawable.img13,
            R.drawable.img14,
            R.drawable.img15,
            R.drawable.img16,
            R.drawable.img17,
            R.drawable.img18,
            R.drawable.img19,
            R.drawable.img20,
            R.drawable.img21,
            R.drawable.img22,
            R.drawable.img23,
            R.drawable.img24,
            R.drawable.img25,
            R.drawable.img26,
            R.drawable.img27,
            R.drawable.img28,
            R.drawable.img29,
            R.drawable.img30,
            R.drawable.img31,
            R.drawable.img32,
            R.drawable.img33,
            R.drawable.img34,
            R.drawable.img35,
            R.drawable.img36,
            R.drawable.img37,
            R.drawable.img38,
            R.drawable.img39,
            R.drawable.img40,
            R.drawable.img41,
            R.drawable.img42,
            R.drawable.img43,
            R.drawable.img44,
            R.drawable.img45,
            R.drawable.img46,
            R.drawable.img47,
            R.drawable.img48,


    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imageGallery);
//        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);


    }

    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_ids.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }
}
