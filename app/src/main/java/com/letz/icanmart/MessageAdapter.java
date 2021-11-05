package com.letz.icanmart;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    List<ModelClass> list;
    String userName;

    boolean status;
    int send;
    int receive;


    public MessageAdapter(List<ModelClass> list, String userName) {
        this.list = list;
        this.userName = userName;
        status = false;
        send = 1;
        receive = 2;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == send){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_send, parent, false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(userName))
        {
            status = true;
            return send;
        }
        else {
            status = false;
            return receive;
        }
    };
}
