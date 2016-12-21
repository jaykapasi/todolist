/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.todo.com.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.todo.com.database.TodoTask;
import android.todo.com.listeneres.ItemTouchHelperAdapter;
import android.todo.com.listeneres.ItemTouchHelperViewHolder;
import android.todo.com.listeneres.OnItemActionListener;
import android.todo.com.todolist.R;
import android.todo.com.utils.Utilities;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private List<TodoTask> mTodoTaskList = new ArrayList<>();
    private Context mContext;
    private OnItemActionListener mOnItemActionListener;

    public RecyclerListAdapter(Context context, OnItemActionListener onItemActionListener) {
        mContext = context;
        mOnItemActionListener = onItemActionListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.titleTxt.setText(mTodoTaskList.get(position).getTitle());
        holder.descriptionTxt.setText(mTodoTaskList.get(position).getDescription());
        holder.dateTxt.setText(Utilities.getDate(Long.parseLong(mTodoTaskList.get(position).getCreated_date())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemActionListener.onItemClick(mTodoTaskList.get(position));
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mOnItemActionListener.onItemDismiss(mTodoTaskList.get(position));
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mTodoTaskList.size();
    }

    public void setTodoTaskList(List<TodoTask> todoTaskList) {
        mTodoTaskList = todoTaskList;
        notifyDataSetChanged();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView titleTxt, descriptionTxt, dateTxt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            titleTxt = (TextView) itemView.findViewById(R.id.item_title_txt);
            descriptionTxt = (TextView) itemView.findViewById(R.id.item_description_txt);
            dateTxt = (TextView) itemView.findViewById(R.id.item_date_txt);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
