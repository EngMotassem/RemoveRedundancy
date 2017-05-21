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


/**
 * Created by MohamedDev on 5/8/2017.
 */

public class HistoryAdapter  extends ArrayAdapter<String> {
    Context context;

    ArrayList<String>datearr;
    ArrayList<String>desc;
    public static SparseBooleanArray mSelectedItemsIds;
    boolean[] checkBoxState;


    private static LayoutInflater inflater = null;

    public HistoryAdapter(Context home, int pos,ArrayList<String> datearr,ArrayList<String>desc) {
        super(home, pos,datearr);
        // TODO Auto-generated constructor stub

        context = home;
        this.datearr = datearr;
        this.desc =desc;
        checkBoxState = new boolean[datearr.size()];

        mSelectedItemsIds = new  SparseBooleanArray();


        inflater =  LayoutInflater.from(context);

    }

    @Override
    public void remove(String object) {
        datearr.remove(object);

        notifyDataSetChanged();
    }
    public Object getMyList() {
        return datearr;
    }

    public void  toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    // Remove selection after unchecked
    public void  removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    // Item checked on selection


    public void  selectall(boolean s) {
        mSelectedItemsIds = new  SparseBooleanArray();

        for(int i=0; i < datearr.size(); i++){
            checkBoxState[i] =s;
            toggleSelection(i);

        }

    }
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position,  value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    // Get number of selected item
    public int  getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public  SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datearr.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView date;
        TextView desct;
        CheckBox box;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Holder holder = new Holder();
        view = inflater.inflate(R.layout.history_item_list, null);

        holder.date = (TextView) view.findViewById(R.id.date);
        holder.box=(CheckBox) view.findViewById(R.id.box);

        holder.desct = (TextView) view.findViewById(R.id.desc);
        holder.date.setText(datearr.get(position));
        holder.desct.setText(desc.get(position));

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