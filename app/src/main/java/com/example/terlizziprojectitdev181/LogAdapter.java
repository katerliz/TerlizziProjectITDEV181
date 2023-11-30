package com.example.terlizziprojectitdev181;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends ArrayAdapter<MaintLogList>
{
    private Context context;
    private ArrayList<MaintLogList> mLog;

    String description;
    static boolean status;

    int pos;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//private MaintenanceData md;

    public LogAdapter(Context context, ArrayList<MaintLogList> list)
    {
      super(context,R.layout.log_list_item_layout,list);
      this.context=context;
      mLog=list;
    }

    private class ViewHolder{
        TextView description;

        CheckBox chkBox;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.log_list_item_layout,parent,false);



        TextView tvLog = convertView.findViewById(R.id.tvListText);
        CheckBox cbCheck = convertView.findViewById(R.id.cbCheck);

        tvLog.setText(mLog.get(position).getDescription().toString());

        cbCheck.setChecked(mLog.get(position).isDone());


       return convertView;
    }


}
