package com.example.ankit.findyourfellow;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RequestAdapter extends ArrayAdapter {

    private FirebaseAuth mAuth;
    private List list = new ArrayList();

    public RequestAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add(String object)
    {
        list.add(object);
        super.add(object);
    }

    static class RowHolder
    {
        TextView EMAIL;
        Button ACCEPT;
        Button DECLINE;
    }

    @Override
    public int getCount()
    {
        return this.list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;
        row = convertView;
        RowHolder holder;

        final int currentPosition = position;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);

            holder = new RowHolder();

            holder.EMAIL = (TextView) row.findViewById(R.id.list_item_text);
            holder.ACCEPT = (Button) row.findViewById(R.id.list_item_accept);
            holder.DECLINE = (Button) row.findViewById(R.id.list_item_decline);

            holder.ACCEPT.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    mAuth = FirebaseAuth.getInstance();
                    String thisUser = mAuth.getCurrentUser().getUid().toString();

                    Firebase findRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + thisUser);

                    String currentEmail = (String) getItem(currentPosition);

                    Query requestQuery = findRef.child("FriendRequest").orderByChild("Email").equalTo(currentEmail);

                    requestQuery.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot requestSnapshot: dataSnapshot.getChildren())
                            {
                                Toast.makeText(getContext(), "Button Accept clicked", Toast.LENGTH_LONG).show();
                                //requestSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError)
                        {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();

                        }
                    });

                    Toast.makeText(getContext(), "Button Accept clicked", Toast.LENGTH_LONG).show();
                }
            });

            holder.DECLINE.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getContext(), "Button Decline clicked", Toast.LENGTH_LONG).show();

                }
            });

            row.setTag(holder);
        }
        else
        {
            holder = (RowHolder) row.getTag();
        }

        String FR = (String) getItem(position);
        holder.EMAIL.setText(FR);

        return row;
    }
}
