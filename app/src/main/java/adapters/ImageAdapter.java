package adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamedhanydev.repeatcancellation.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import elements.Contact;
import elements.GlideRoundTransform;
import elements.ImageElement;


/**
 * Created by MohamedDev on 5/12/2017.
 */

public class ImageAdapter  extends ArrayAdapter<ImageElement> {


    public static ArrayList<ImageElement> list;



    public static SparseBooleanArray mSelectedItemsIds;

    Context context;

    boolean[] checkBoxState;




    private static LayoutInflater inflater=null;
    public ImageAdapter(Context home,int pos, ArrayList<ImageElement>mylist ) {
        super(home,pos,mylist);
        // TODO Auto-generated constructor stub

        context=home;
        mSelectedItemsIds = new  SparseBooleanArray();
        checkBoxState = new boolean[mylist.size()];

        list =mylist;

        inflater =  LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Nullable
    @Override
    public ImageElement getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        ImageView iamge;
        TextView repeat;
        CheckBox box;
    }
    @Override
    public void remove(ImageElement object) {
        list.remove(object);
        notifyDataSetChanged();
    }

    // get List after update or delete
    public Object getMyList() {
        return list;
    }

    public void  toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    // Remove selection after unchecked

    public void  removeSelection() {
        mSelectedItemsIds = new  SparseBooleanArray();
        notifyDataSetChanged();
    }
    public void  selectall(boolean s) {
        mSelectedItemsIds = new  SparseBooleanArray();

        for(int i=0; i < list.size(); i++){
            checkBoxState[i] =s;
            toggleSelection(i);

        }
    }

    // Item checked on selection
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position,  value);
        else
            mSelectedItemsIds.delete(position);
    }

    // Get number of selected item
    public int  getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public  SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ImageAdapter.Holder holder=new ImageAdapter.Holder();
        view = inflater.inflate(R.layout.image_name_item, null);
        holder.iamge=(ImageView) view.findViewById(R.id.icon);


        holder.repeat=(TextView) view.findViewById(R.id.repeat);
        holder.box=(CheckBox) view.findViewById(R.id.box);

        holder.repeat.setText(list.get(position).getName());

        File imgFile = new  File(list.get(position).getUrl());

        if(imgFile.exists()){


            Glide.with(context)
                    .load(Uri.fromFile(imgFile))
                    .transform(new GlideRoundTransform(context, 16))
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.m3)
                    .into(holder.iamge);




        }
        holder.box.setChecked(checkBoxState[position]);


        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                    checkBoxState[position]=true;
                else
                    checkBoxState[position]=false;
                toggleSelection(position);

            }
        });



        return view;
    }


}
