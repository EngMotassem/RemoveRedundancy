package adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mohamedhanydev.repeatcancellation.R;

import java.util.ArrayList;

import elements.Contact;

/**
 * Created by MohamedDev on 5/7/2017.
 */

public class ListAdapter extends ArrayAdapter<Contact> {
    Context context;

  public static ArrayList<Contact>list;


    public static SparseBooleanArray mSelectedItemsIds;

     boolean[] checkBoxState;




    private static LayoutInflater inflater=null;
    public ListAdapter(Context home,int pos, ArrayList<Contact>mylist ) {
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
    public Contact getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView number;
        TextView repeat;
        CheckBox box;
    }
    @Override
    public void remove(Contact object) {
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
    public void  selectall(boolean s) {
        mSelectedItemsIds = new  SparseBooleanArray();

        for(int i=0; i < list.size(); i++){
            checkBoxState[i] =s;
toggleSelection(i);

        }

    }
    // Remove selection after unchecked

    public void  removeSelection() {
        mSelectedItemsIds = new  SparseBooleanArray();
        notifyDataSetChanged();
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

        final    Holder holder=new Holder();
        view = inflater.inflate(R.layout.contact_items_listview, null);

        holder.number=(TextView) view.findViewById(R.id.number);

        holder.repeat=(TextView) view.findViewById(R.id.repeat);
        holder.box=(CheckBox) view.findViewById(R.id.box);

        holder.number.setText(list.get(position).getPhone());
      holder.repeat.setText(list.get(position).getName());
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