package com.example.anee.mytask.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.anee.mytask.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> {

    private Context mCtx;
    private List<TodolistPojo> taskList;
    private OnItemClicklistener mlistener;
    public  static  String taskid;

    public interface OnItemClicklistener {

        void onItemclick(TodolistPojo pojo);
    }

    public void setOnItemClickListener(OnItemClicklistener listener) {

        mlistener = listener;
    }


    public RecyclerAdapter(Context mCtx, List<TodolistPojo> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View itemView = inflater.inflate(R.layout.raw_item, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {

        final TodolistPojo task = taskList.get(position);

        holder.Name.setText(task.getName());
        holder.Location.setText(task.getLocation());
        holder.Time.setText(task.getTime());
        holder.Date.setText(task.getDate());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TodolistPojo userInfo = taskList.get(position);
                mlistener.onItemclick(userInfo);
               // Toast.makeText(mCtx, "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {

        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        TextView Name, Location, Time, Date;

        public TaskViewHolder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            Name = itemView.findViewById(R.id.name);
            Location = itemView.findViewById(R.id.location);
            Time = itemView.findViewById(R.id.time);
            Date = itemView.findViewById(R.id.date);


        }
    }
}