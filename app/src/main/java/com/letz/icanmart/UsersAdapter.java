package com.letz.icanmart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    List<String> userList;
    String userName;
    Context mContext;

    FirebaseDatabase database;
    DatabaseReference reference;

    public UsersAdapter(List<String> userList, String userName, Context mContext) {
        this.userList = userList;
        this.userName = userName;
        this.mContext = mContext;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        reference.child(userList.get(position)).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fdbName = snapshot.child("userName").getValue().toString();
                String fdbImage = snapshot.child("image").getValue().toString();
                // fdb text show
                holder.textView.setText(fdbName);

                // image 없을 경우 default icon 보여주기
                if (fdbImage.equals("null")){
                    holder.imageView.setImageResource(R.drawable.user_icon);

                } else {
                    Picasso.get().load(fdbImage).into(holder.imageView);
                }

                // onClick cardView
                holder.cardView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Intent igoChat = new Intent(UsersAdapter.this,Chat.class);
                        igoChat.putExtra("userName",userName);
                        igoChat.putExtra("fdbName",fdbName);
                        mContext.startActivity(igoChat);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private ImageView imageView;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(R.id.tvName_users_card);
            imageView = itemView.findViewById(R.id.ivCircle_users_card);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
