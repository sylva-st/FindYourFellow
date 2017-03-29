package com.example.ankit.findyourfellow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 2017-03-26.
 */

public class TrackAdapter extends ArrayAdapter{

    private FirebaseAuth mAuth;
    private List email = new ArrayList<>(); //name
    private List id = new ArrayList<>();
    private List friendLocations = new ArrayList<>();
    private List userLocations = new ArrayList<>();
    private Context c;

    public TrackAdapter(Context context, int resource)
    {
        super(context, resource);
        this.c = context;
    }

    public void add(String object, String object2, String object3, String object4, String object5, String object6)
    {
        email.add(object);
        id.add(object2);
        friendLocations.add(object3);
        friendLocations.add(object4);
        userLocations.add(object5);
        userLocations.add(object6);
        super.add(object);
        super.add(object2);
        //super.add(object3);
    }

    static class RowHolder
    {
        TextView EMAIL;
        TextView DISTANCE;
    }

    @Override
    public int getCount()
    {
        return this.email.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.email.get(position);
    }

    public Object getId(int position) { return this.id.get(position);}

    public Object getFriendLatitude(int position) { return this.friendLocations.get(position*2);}

    public Object getFriendLongitude(int position) { return this.friendLocations.get((position*2)+1);}

    public Object getUserLatitude(int position) { return this.userLocations.get(position*2);}

    public Object getUserLongitude(int position) { return this.userLocations.get((position*2)+1);}

    public boolean isAlreadyInList(String testId) { return this.id.contains(testId);}

    public void replaceList(String testId, String newFLat, String newFLong, String newULat, String newULong)
    {
        int itemIndex = this.id.indexOf(testId);

        this.friendLocations.set((itemIndex*2), newFLat);

        this.friendLocations.set(((itemIndex*2)+1), newFLong);

        this.userLocations.set((itemIndex*2), newULat);

        this.userLocations.set(((itemIndex*2)+1), newULong);
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
            row = inflater.inflate(R.layout.track_item, parent, false);

            holder = new RowHolder();

            holder.EMAIL = (TextView) row.findViewById(R.id.track_item_text);
            holder.DISTANCE = (TextView) row.findViewById(R.id.track_item_distance);


            holder.EMAIL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, "item at position " + currentPosition, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(c, FriendInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("FRIENDLATITUDE", (String) getFriendLatitude(currentPosition));
                    intent.putExtra("FRIENDLONGITUDE", (String) getFriendLongitude(currentPosition));
                    intent.putExtra("USERLATITUDE", (String) getUserLatitude(currentPosition));
                    intent.putExtra("USERLONGITUDE", (String) getUserLongitude(currentPosition));
                    c.startActivity(intent);
                }
            });


            row.setTag(holder);
        }
        else
        {
            holder = (RowHolder) row.getTag();
        }


        String EM = (String) getItem(position);
        holder.EMAIL.setText(EM);



        Location friendLocation = new Location("");

        final Location userLocation = new Location("");

        String fLatitude = (String) getFriendLatitude(currentPosition);

        String fLongitude = (String) getFriendLongitude(currentPosition);

        String uLatitude = (String) getUserLatitude(currentPosition);

        String uLongitude = (String) getUserLongitude(currentPosition);

        double fLat = Double.parseDouble(fLatitude);

        double fLong = Double.parseDouble(fLongitude);

        double uLat = Double.parseDouble(uLatitude);

        double uLong = Double.parseDouble(uLongitude);

        //double d = Double.parseDouble((String) getFriendLatitude(currentPosition));

        friendLocation.setLatitude(fLat);

        friendLocation.setLongitude(fLong);

        userLocation.setLatitude(uLat);

        userLocation.setLongitude(uLong);

        float distance = friendLocation.distanceTo(userLocation);

        int intDistance = (int) distance;

        holder.DISTANCE.setText(String.valueOf(intDistance) + "m");

        return row;

    }
}
