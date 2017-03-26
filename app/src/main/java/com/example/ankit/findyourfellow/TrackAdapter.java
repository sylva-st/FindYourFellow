package com.example.ankit.findyourfellow;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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

    //private FirebaseAuth mAuth;
    private List list = new ArrayList(); //name
    private List id = new ArrayList();
    private List userLocation = new ArrayList();

    public TrackAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add(String object, String object2, String object3)
    {
        list.add(object);
        id.add(object2);
        userLocation.add(object3);
        super.add(object);
        super.add(object2);
        super.add(object3);
    }

    static class RowHolder
    {
        TextView EMAIL;
        TextView DISTANCE;
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

    public Object getId(int position) { return this.id.get(position);}

    public Object getUserLocation(int position) { return this.userLocation.get(position);}

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

            row.setTag(holder);
        }
        else
        {
            holder = (RowHolder) row.getTag();
        }

        String FR = (String) getItem(position);
        holder.EMAIL.setText(FR);


        //mAuth = FirebaseAuth.getInstance();


        //String thisUser = mAuth.getCurrentUser().getUid().toString();

        //List userLocation = new ArrayList();

        //userLocation.set(0, 1111);
        //userLocation.set(1, 2222);

        //final Firebase userRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + thisUser + "/Information");

        /*
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userInfo = dataSnapshot.getValue().toString();
                String userKey = dataSnapshot.getKey().toString();

                if (userKey == "Latitude")
                {
                    //double latitude = Double.parseDouble(userInfo);
                    //userLocation.add(0, latitude);

                    //userLocation.set(0,1234.0);
                }

                if (userKey == "Longitude")
                {
                    //double longitude = Double.parseDouble(userInfo);
                    //userLocation.add(1, longitude);

                    //userLocation.set(1, 9876.0);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        String friendID = (String) getId(position);

        final ArrayList<Double> friendLocation = new ArrayList<>();

        final Firebase friendRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + friendID + "/Information");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String friendInfo = dataSnapshot.getValue().toString();
                String friendKey = dataSnapshot.getKey().toString();

                if (friendKey == "Latitude")
                {
                    double latitude = Double.parseDouble(friendInfo);
                    friendLocation.add(0, latitude);
                }

                if (friendKey == "Longitude")
                {
                    double longitude = Double.parseDouble(friendInfo);
                    friendLocation.add(1, longitude);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        //LatLng userLoc = new LatLng(userLocation.get(0), userLocation.get(1));
        //LatLng friendLoc = new LatLng(friendLocation.get(0), friendLocation.get(1));
       //String DT = (String) getDistance(position);

        float[] distance = {-1};

        Location.distanceBetween(userLocation.get(0), userLocation.get(1),friendLocation.get(0), friendLocation.get(1), distance);

        holder.DISTANCE.setText(String.valueOf(distance[0]));
*/
        //float[] distance = {-1};
        //Location.distanceBetween(0,0,0,0,distance);
        //String DT = (String) getId(position);

        //userLocation.add(1111);
        //userLocation.set(1, 2222);

        String DT = (String) getUserLocation(currentPosition);
        //String DT = (String.valueOf(0));
        //holder.DISTANCE.setText(String.valueOf(userLocation.get(0)));
        holder.DISTANCE.setText(DT);


        return row;

    }
}
