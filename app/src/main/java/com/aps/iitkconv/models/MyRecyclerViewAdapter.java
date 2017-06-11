package com.aps.iitkconv.models;

/**
 * Created by imhobo on 31/3/17.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aps.iitconv.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_HEADER = 1;
    public static final int ITEM_TYPE_PHD = 2;
    public static final int ITEM_TYPE_NON_PHD = 3;
    public static final int ITEM_TYPE_PIC_AWARD = 4;
    public static final int ITEM_TYPE_NON_PIC_AWARD = 5;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private static int val;
    ArrayList<String> palette = new ArrayList<>();
    private ArrayList<DataObject> mDataset;


    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset, int value) {

        mDataset = myDataset;
        val = value;

        // The colors of the left tile.
        palette.add("#C66963");
        palette.add("#0000FF");
        palette.add("#158431");
        palette.add("#76699C");
        palette.add("#808000");

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (val == 3 || val == 4 || val == 40 || val == 400 || val == 300 || (val == 999 && viewType == ITEM_TYPE_NON_PHD))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_2, parent, false);

        else if (val == 69)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_5, parent, false);

        else if (val == 2 || (val == 5 && viewType == ITEM_TYPE_HEADER) || (val == 50 && viewType == ITEM_TYPE_HEADER)
                || (val == 30 && viewType == ITEM_TYPE_HEADER) || val == 10 || (val == 509 && viewType == ITEM_TYPE_HEADER) || (val == 519 && viewType == ITEM_TYPE_HEADER))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_1, parent, false);

        else if ((val == 9 && viewType == ITEM_TYPE_HEADER)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_header, parent, false);
        } else if (val == 1 || val == 401 || val == 31 || (val == 9 && viewType == ITEM_TYPE_NORMAL) || val == 51 || val == 501 || (val == 999 && viewType == ITEM_TYPE_PHD)
                || (val == 1000 && viewType == ITEM_TYPE_NON_PIC_AWARD))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_4, parent, false);

        else if ((val == 30 && viewType == ITEM_TYPE_NORMAL) || (val == 1000 && viewType == ITEM_TYPE_PIC_AWARD || (val == 509 && viewType == ITEM_TYPE_NORMAL)
                || (val == 519 && viewType == ITEM_TYPE_NORMAL))) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_pic, parent, false);
        } else if ((val == 5 && viewType == ITEM_TYPE_NORMAL) || (val == 50 && viewType == ITEM_TYPE_NORMAL)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_big, parent, false);
        }


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public int getItemViewType(int position) {

        Log.d("Inside getItemViewType", String.valueOf(val));

        if (val == 999) {
            Log.d("ITEM_TYPE", mDataset.get(position).getmText4());
            if (mDataset.get(position).getmText5().equals("Ph.D.")) {

                return ITEM_TYPE_PHD;

            } else {
                return ITEM_TYPE_NON_PHD;
            }
        } else if (val == 1000) {
            Log.d("Picture Name : ", mDataset.get(position).getmText9());
            if (!mDataset.get(position).getmText9().equals("")) {

                return ITEM_TYPE_PIC_AWARD;
            } else {
                return ITEM_TYPE_NON_PIC_AWARD;
            }
        } else {
            Log.d("wrong val : ", mDataset.get(position).getmText9());
            if ((mDataset.get(position).getmText1()).equals("Previous Recipients") || (mDataset.get(position).getmText1()).equals("Previous Guests") ||
                    (val == 9 && (mDataset.get(position).getmText2()).equals(""))) {
                return ITEM_TYPE_HEADER;
            } else {
                return ITEM_TYPE_NORMAL;
            }

        }

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        final int itemType = getItemViewType(position);
        Log.d("ItemType : ", String.valueOf(itemType));

        if (val == 3 || val == 4 || val == 40 || val == 400 || val == 300) {
            String pre = "";

            if (val == 300)
                holder.arrow.setVisibility(View.INVISIBLE);

            if (val == 400) {
                pre = "Roll number : ";
                holder.arrow.setVisibility(View.INVISIBLE);
            } else if (val == 3 || val == 4 || val == 40) pre = "Total number of students : ";

            holder.t1.setText(mDataset.get(position).getmText1());
            holder.t2.setText(pre + mDataset.get(position).getmText2());

            //Choosing 1 out of the 5 colors from palette for the left tile in a card
            holder.tile.setBackgroundColor(Color.parseColor(palette.get(position % 5)));

        } else if (val == 2 || val == 10) {
            //Choosing 1 out of the 5 colors from palette for the left tile in a card
            holder.tile2.setBackgroundColor(Color.parseColor(palette.get(position % 5)));

            holder.t1.setText(mDataset.get(position).getmText1());
        } else if (val == 69) {
            if (holder.t1 != null)
                holder.t1.setText(mDataset.get(position).getmText1());
            if (holder.pic != null)
                holder.pic.setImageBitmap(mDataset.get(position).getmImg());
        } else if ((val == 5 && itemType == ITEM_TYPE_HEADER) || (val == 50 && itemType == ITEM_TYPE_HEADER)) {
            //Choosing 1 out of the 5 colors from palette for the left tile in a card
            holder.tile2.setBackgroundColor(Color.parseColor(palette.get(position % 5)));

            holder.t4.setText(mDataset.get(position).getmText1());
        } else if ((val == 30 && itemType == ITEM_TYPE_HEADER) || (val == 509 && itemType == ITEM_TYPE_HEADER) || (val == 519 && itemType == ITEM_TYPE_HEADER)) {
            //Choosing 1 out of the 5 colors from palette for the left tile in a card
            holder.tile2.setBackgroundColor(Color.parseColor(palette.get(position % 5)));

            holder.t5.setText(mDataset.get(position).getmText1());
        } else if ((val == 9 && itemType == ITEM_TYPE_HEADER)) {
//            Log.d("headContact : ", "Here");
            holder.t5.setText(mDataset.get(position).getmText1());
        } else if (val == 1 || val == 401 || val == 51 || val == 501 || (val == 999 && itemType == ITEM_TYPE_PHD) || (val == 9 && itemType == ITEM_TYPE_NORMAL)) {
            String p1 = "";
            String p2 = "";
            String p3 = "";
            String p4 = "";

            SpannableStringBuilder s2 = null;
            SpannableStringBuilder s3 = null;
            SpannableStringBuilder s4 = null;
            if (val == 401 || (val == 999 && itemType == ITEM_TYPE_PHD)) {
                p2 = "Roll number : ";
                p3 = "Thesis Supervisor : ";
                p4 = "Description : ";

                p1 = p1 + mDataset.get(position).getmText1();
                p2 = p2 + mDataset.get(position).getmText2();
                p3 = p3 + mDataset.get(position).getmText3();
                p4 = p4 + mDataset.get(position).getmText4();

                s2 = new SpannableStringBuilder(p2);
                s2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                s3 = new SpannableStringBuilder(p3);
                s3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                s4 = new SpannableStringBuilder(p4);
                s4.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.t1.setText(p1);
                holder.t2.setText(s2);
                holder.t3.setText(s3);
                holder.t4.setText(s4);

            } else if (val == 1) {

                p2 = "";
                p3 = "Date : ";
                p4 = "Time : ";

                p1 = p1 + mDataset.get(position).getmText1();
                p2 = p2 + mDataset.get(position).getmText2();
                p3 = p3 + mDataset.get(position).getmText3();
                p4 = p4 + mDataset.get(position).getmText4();


                s3 = new SpannableStringBuilder(p3);
                s3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                s4 = new SpannableStringBuilder(p4);
                s4.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.t1.setText(p1);
                holder.t2.setText(p2);
                holder.t3.setText(s3);
                holder.t4.setText(s4);

            } else if (val == 9) {
//                Log.d("nameContact : ", p1);
                p2 = "Phone : ";

                if (position < 12)
                    p3 = "Email : ";
                else
                    p3 = "Vehicle : ";
                p4 = "CALL NOW";

                p1 = p1 + mDataset.get(position).getmText1();
                p2 = p2 + mDataset.get(position).getmText2();
                p3 = p3 + mDataset.get(position).getmText3();
                p4 = p4 + mDataset.get(position).getmText4();

                s2 = new SpannableStringBuilder(p2);
                s2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                s3 = new SpannableStringBuilder(p3);
                if (position < 12)
                    s3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                else
                    s3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                s4 = new SpannableStringBuilder(p4);
                s4.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


//                Log.d("nameContact : ", p1);
                holder.t1.setText(p1);
                holder.t2.setText(s2);

                if (position == 0 || (position >= 12 && position <= 15))
                    holder.t3.setText("");
                else
                    holder.t3.setText(s3);
                holder.t4.setText(s4);
            } else {
                holder.t1.setText(mDataset.get(position).getmText1());
                holder.t2.setText(mDataset.get(position).getmText2());
                holder.t3.setText(mDataset.get(position).getmText3());
                holder.t4.setText(mDataset.get(position).getmText4());

            }


        } else if ((val == 5 && itemType == ITEM_TYPE_NORMAL) || (val == 50 && itemType == ITEM_TYPE_NORMAL)) {
            holder.t1.setText(mDataset.get(position).getmText1());
            holder.t2.setText(mDataset.get(position).getmText2());
            holder.t3.setText(mDataset.get(position).getmText5());
            holder.pic.setImageBitmap(mDataset.get(position).getmImg());
        } else if (val == 999) {
            if (itemType == ITEM_TYPE_NON_PHD) {
                //Choosing 1 out of the 5 colors from palette for the left tile in a card
                holder.tile.setBackgroundColor(Color.parseColor(palette.get(position % 5)));

                String pre = "Roll number : ";

                holder.t5.setText(mDataset.get(position).getmText1());
                holder.t6.setText(pre + mDataset.get(position).getmText2());
                holder.arrow.setVisibility(View.INVISIBLE);
            }
        } else if (val == 1000 && itemType == ITEM_TYPE_PIC_AWARD) {
//            Log.d("Here Val : ", String.valueOf(val) + " : " + itemType);

            String name, b, c, d;

            name = mDataset.get(position).getmText2();
            holder.t5.setText(name);

            b = mDataset.get(position).getmText1();
            if (b.equals(""))
                b = mDataset.get(position).getmText7();

            holder.t6.setText(b);


            //Second line can be branch or comment. Third line will be comment
            c = mDataset.get(position).getmText5();
            if (!c.equals("")) {
                holder.t7.setText(c);
                holder.t8.setText(mDataset.get(position).getmText6());
            } else {
                holder.t7.setText(mDataset.get(position).getmText6());
            }

            holder.pic.setImageBitmap(mDataset.get(position).getmImg());

        } else if (val == 1000 && itemType == ITEM_TYPE_NON_PIC_AWARD) {
            String name, b, c, d;

            name = mDataset.get(position).getmText2();
            holder.t1.setText(name);

            //Right side of the first line will be either roll number or year
            b = mDataset.get(position).getmText1();
            if (b.equals(""))
                b = mDataset.get(position).getmText7();

            holder.t2.setText(b);


            c = mDataset.get(position).getmText7();
            holder.t3.setText(c);
            holder.t4.setText(mDataset.get(position).getmText6());

        } else if (val == 31 || (val == 30 && itemType == ITEM_TYPE_NORMAL) || (val == 509 && itemType == ITEM_TYPE_NORMAL) || (val == 519 && itemType == ITEM_TYPE_NORMAL)) {

//            Log.d("Here Val : ", String.valueOf(val) + " : " + itemType);
            String name, b, c, d;

            name = mDataset.get(position).getmText2();
            holder.t1.setText(name);

            //Right side of the first line will be either roll number or year
            b = mDataset.get(position).getmText1();
            if (b.equals(""))
                b = mDataset.get(position).getmText7();

            holder.t2.setText(b);


            //Second line can be dept. Third line will be comment
            c = mDataset.get(position).getmText5();
            holder.t3.setText(c);
            holder.t4.setText(mDataset.get(position).getmText6());


            if (val == 30 || val == 509 || val == 519) {
                holder.pic.setImageBitmap(mDataset.get(position).getmImg());
            }


        }


    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public ArrayList<DataObject> getDataSet() {
        return mDataset;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;
        TextView t7;
        TextView t8;

        ImageView pic;
        ImageView arrow;

        FrameLayout tile;
        FrameLayout tile2;

        public DataObjectHolder(View itemView) {
            super(itemView);


            //Tile is for left tiles in 2 line rows. Tile2 is for 1 line rows.
            tile = (FrameLayout) itemView.findViewById(R.id.card_tile_2);
            tile2 = (FrameLayout) itemView.findViewById(R.id.card_tile_1);
            arrow = (ImageView) itemView.findViewById(R.id.imageArrow);

            if (val == 3 || val == 4 || val == 40 || val == 400 || val == 300) {
                t1 = (TextView) itemView.findViewById(R.id.textView);
                t2 = (TextView) itemView.findViewById(R.id.textView2);

            } else if (val == 69) {
                t1 = (TextView) itemView.findViewById(R.id.textView1);
                pic = (ImageView) itemView.findViewById(R.id.pic);
            } else if (val == 2 || val == 10) {
                t1 = (TextView) itemView.findViewById(R.id.textViewGradMenu);

            } else if (val == 1 || val == 401 || val == 31 || val == 51 || val == 501 || val == 9) {
                t1 = (TextView) itemView.findViewById(R.id.textViewp1);
                t2 = (TextView) itemView.findViewById(R.id.textViewp2);
                t3 = (TextView) itemView.findViewById(R.id.textViewp3);
                t4 = (TextView) itemView.findViewById(R.id.textViewp4);

                //For contacts heading
                t5 = (TextView) itemView.findViewById(R.id.textViewHeader);
                //Log.d("401","401");
            }

            //Searched in graduating students
            else if (val == 999) {
                t1 = (TextView) itemView.findViewById(R.id.textViewp1);
                t2 = (TextView) itemView.findViewById(R.id.textViewp2);
                t3 = (TextView) itemView.findViewById(R.id.textViewp3);
                t4 = (TextView) itemView.findViewById(R.id.textViewp4);

                t5 = (TextView) itemView.findViewById(R.id.textView);
                t6 = (TextView) itemView.findViewById(R.id.textView2);

            }

            //Searched in awards
            else if (val == 1000) {
                t1 = (TextView) itemView.findViewById(R.id.textViewp1);
                t2 = (TextView) itemView.findViewById(R.id.textViewp2);
                t3 = (TextView) itemView.findViewById(R.id.textViewp3);
                t4 = (TextView) itemView.findViewById(R.id.textViewp4);

                t5 = (TextView) itemView.findViewById(R.id.textViewpic1);
                t6 = (TextView) itemView.findViewById(R.id.textViewpic2);
                t7 = (TextView) itemView.findViewById(R.id.textViewpic3);
                t8 = (TextView) itemView.findViewById(R.id.textViewpic4);
                pic = (ImageView) itemView.findViewById(R.id.imageViewStudent);


            } else if (val == 30 || val == 509 || val == 519) {

                t1 = (TextView) itemView.findViewById(R.id.textViewpic1);
                t2 = (TextView) itemView.findViewById(R.id.textViewpic2);
                t3 = (TextView) itemView.findViewById(R.id.textViewpic3);
                t4 = (TextView) itemView.findViewById(R.id.textViewpic4);
                pic = (ImageView) itemView.findViewById(R.id.imageViewStudent);

                //Textview to show previous recipient
                t5 = (TextView) itemView.findViewById(R.id.textViewGradMenu);
            } else if (val == 5 || val == 50) {

                t1 = (TextView) itemView.findViewById(R.id.textViewbig1);
                t2 = (TextView) itemView.findViewById(R.id.textViewbig2);
                t3 = (TextView) itemView.findViewById(R.id.textViewbig3);
                pic = (ImageView) itemView.findViewById(R.id.imageViewBig);

                //Textview to show previous recipient
                t4 = (TextView) itemView.findViewById(R.id.textViewGradMenu);


            }


            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
