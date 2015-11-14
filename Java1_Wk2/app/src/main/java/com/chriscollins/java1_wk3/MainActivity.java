package com.chriscollins.java1_wk3;
/**
 * Created by chriscollins on 11/9/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.chriscollins.java1_wk2.R;

import java.util.HashMap;

public class MainActivity extends Activity {

    public static final String TAG = "CHC";
    private HashMap<Integer, PlayerData> map = new HashMap<>();
    private PAdapter playerAdapter = new PAdapter(MainActivity.this, map);
    private Spinner spinner;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        PlayerData Jordan = new PlayerData("Michael Jordan", "#23", "North Carolina Tarheels");
        PlayerData Perkins = new PlayerData("Sam Perkins", "#41", "North Carolina Tarheels");
        PlayerData Lynch = new PlayerData("George Lynch", "#34", "North Carolina Tarheels");
        PlayerData Montross = new PlayerData("Eric Montross", "#00", "North Carolina Tarheels");
        PlayerData Stackhouse = new PlayerData("Jerry Stackhouse", "#42", "North Carolina Tarheels");
        PlayerData Hansbrough = new PlayerData("Tyler Hansbrough", "#50", "North Carolina Tarheels");
        PlayerData Barnes = new PlayerData("Harrison Barnes", "#40", "North Carolina Tarheels");



          /*Add to hashmap*/
        map.put(0, Jordan);
        map.put(1, Perkins);
        map.put(2, Lynch);
        map.put(3, Montross);
        map.put(4, Stackhouse);
        map.put(5, Hansbrough);
        map.put(6, Barnes);

        int itemIndex=0;

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == 1) {


            Spinner spinner = (Spinner) findViewById(R.id.team_dropdown);
            PAdapter playerAdapter = new PAdapter(this, map);
            spinner.setAdapter(playerAdapter);
            playerDetail(0);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //Intent DetailIntent = new Intent(this, MainActivity.class);
                    //startActivity(DetailIntent);

                    playerDetail(position);
                    Log.d(TAG, "Spinner Item Selected");

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } else {
            playerDetail(0);
            ListView newList = (ListView)findViewById(R.id.listView);
            newList.setAdapter(playerAdapter);
            newList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    playerDetail(position);
                    //player.getText(position);
                    Log.d(TAG, "List Item Selected");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void playerDetail(int position) {
        TextView player = (TextView)findViewById(R.id.textViewName);
        TextView number = (TextView)findViewById(R.id.textViewNumber);
        TextView team = (TextView)findViewById(R.id.textViewTeam);

        player.setText(map.get(position).getPlayer());
        number.setText(map.get(position).getNumber());
        team.setText(map.get(position).getTeam());

    }
}

  /*  @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Spinner mSpinner = (Spinner) findViewById(R.id.team_dropdown);
			ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.artistArray)));
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrayList);
			mSpinner.setAdapter(dataAdapter);

		}
		/*else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			ListView newList = (ListView)findViewById(R.id.listView);
			ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.artistArray)));
			ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
			mArtistList.setAdapter(listAdapter);

		}
	}*/


