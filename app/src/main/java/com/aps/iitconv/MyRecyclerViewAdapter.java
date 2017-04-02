package com.aps.iitconv;

/**
 * Created by imhobo on 31/3/17.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder>
{

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    private static int val;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;

        ImageView pic;

        public DataObjectHolder(View itemView)
        {
            super(itemView);

            if(val==1 || val == 400)
            {
                t1 = (TextView) itemView.findViewById(R.id.textView);
                t2 = (TextView) itemView.findViewById(R.id.textView2);
            }
            else if(val==4 || val==40 || val==3)
            {
                t1 = (TextView) itemView.findViewById(R.id.textViewGradMenu);

            }
            else if(val == 401 || val == 31)
            {
                t1 = (TextView) itemView.findViewById(R.id.textViewp1);
                t2 = (TextView) itemView.findViewById(R.id.textViewp2);
                t3 = (TextView) itemView.findViewById(R.id.textViewp3);
                t4 = (TextView) itemView.findViewById(R.id.textViewp4);
                //Log.d("401","401");
            }
            else if(val == 30)
            {

                t1 = (TextView) itemView.findViewById(R.id.textViewpic1);
                t2 = (TextView) itemView.findViewById(R.id.textViewpic2);
            }

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener)
    {
        this.myClickListener = myClickListener;
    }


    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset, int value)
    {

        mDataset = myDataset;
        val = value;

    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = null;
        if(val==1 || val == 400)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_2, parent, false);

        else if(val==4 || val==40 || val==3)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_1, parent, false);

        else if(val==401 || val == 31)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_4, parent, false);

        else if(val==30)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_pic, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position)
    {
        if(val==1 || val == 400)
        {
            holder.t1.setText(mDataset.get(position).getmText1());
            holder.t2.setText(mDataset.get(position).getmText2());
        }
        else if(val==4 || val==40 || val==3)
        {
            holder.t1.setText(mDataset.get(position).getmText1());
        }
        else if(val==401)
        {
            holder.t1.setText(mDataset.get(position).getmText1());
            holder.t2.setText(mDataset.get(position).getmText2());
            holder.t3.setText(mDataset.get(position).getmText3());
            holder.t4.setText(mDataset.get(position).getmText4());
        }

        else if(val==30)
        {

            holder.t1.setText(mDataset.get(position).getmText3());
            holder.t2.setText(mDataset.get(position).getmText7());
            Log.d("val=30 ", mDataset.get(position).getmText3() + ":" + mDataset.get(position).getmText7());

            //Add code for imageview to show picture

        }
        else if(val==31)
        {
            String name,b,c,d;

            name = mDataset.get(position).getmText3();
            holder.t1.setText(name);

            //Right side of the first line will be either roll number or year
            b = mDataset.get(position).getmText2();
            if(b.equals(""))
                b = mDataset.get(position).getmText8();

            holder.t2.setText(b);


            //Second line can be branch or comment. Third line will be comment
            c = mDataset.get(position).getmText6();
            if(!c.equals(""))
            {
                holder.t3.setText(c);
                holder.t4.setText(mDataset.get(position).getmText7());
            }
            else
            {
                holder.t3.setText(mDataset.get(position).getmText7());
            }



        }


    }

    public void addItem(DataObject dataObj, int index)
    {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index)
    {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener
    {
        public void onItemClick(int position, View v);
    }
}
