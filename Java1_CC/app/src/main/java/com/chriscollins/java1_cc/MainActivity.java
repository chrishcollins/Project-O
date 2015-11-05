/**
 * Created by chriscollins on 11/1/15.
 */

package com.chriscollins.java1_cc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity {

    private EditText mEnterBand;
    private TextView mTotalBands;
    private TextView mAvgBands;
    private EditText mBandNum;
    private Button mSingBand;
    private Button mSaveButton;
    private Button mShowButton;
    private Context mContext;
    private ArrayList<String> mBands;
    private final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mEnterBand = (EditText) findViewById(R.id.enterBand);
        mTotalBands = (TextView) findViewById(R.id.totalBands);
        mAvgBands = (TextView) findViewById(R.id.avgBands);
        mBandNum = (EditText) findViewById(R.id.bandNum);
        mSingBand = (Button) findViewById(R.id.singleButton);
        mSaveButton = (Button) findViewById(R.id.saveButton);
        mShowButton = (Button) findViewById(R.id.showButton);
        mBands = new ArrayList<>();

        mSingBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = -1;
                if(!mBandNum.getText().toString().equals("")) {
                    try {
                        id = Integer.parseInt(mBandNum.getText().toString());
                    } catch (Exception e) {
                       Log.e("Single Number", e.getLocalizedMessage());
                    }
                    if (mBands.size() > 0) {
                        final AlertDialog singleBand = new AlertDialog.Builder(mContext).create();
                        singleBand.setTitle("Selected Band");
                        singleBand.setMessage(mBands.get(id - 1));
                        singleBand.setIcon(R.drawable.guitar_s);
                        singleBand.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        singleBand.show();
                        final EditText numberInput = (EditText) findViewById(R.id.bandNum);

                        numberInput.setText("");

                    }
                } else {
                    Toast.makeText(getBaseContext(), " Enter a band number.", Toast.LENGTH_LONG).show();

                }

            }

        });



        mSaveButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = mEnterBand.getText().toString();

                if (getInput.equals("")) {
                    AlertDialog.Builder bandSaved = new AlertDialog.Builder(mContext);
                    bandSaved.setMessage("Come on, enter a band name!");
                    bandSaved.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bandSaved.setTitle("Alert");
                    bandSaved.setIcon(R.drawable.guitar_s);
                    bandSaved.create();
                    bandSaved.show();

                } else {
                        if (mBands.contains(getInput)) {

                            AlertDialog.Builder bandsSaved = new AlertDialog.Builder(mContext);
                            bandsSaved.setMessage("Can't enter duplicate bands.");
                            bandsSaved.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            bandsSaved.setTitle("Alert");
                            bandsSaved.setIcon(R.drawable.guitar_s);
                            bandsSaved.create();
                            bandsSaved.show();
                        } else {
                            mBands.add(getInput);
                            mTotalBands.setText("Total Number of Bands: " + mBands.size());
                            mAvgBands.setText("Average Length: " + Float.toString(getAverage()));
                            getAverage();
                            final EditText editTextPlainTextInput = (EditText) findViewById(R.id.enterBand);

                            editTextPlainTextInput.setText("");
                    }
                    Toast.makeText(getBaseContext(), getInput + " has been added to your bands list.", Toast.LENGTH_LONG).show();

                }
            }
        }));


        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert number to string
                String getInput = mBandNum.getText().toString();

                AlertDialog.Builder viewArrayList = new AlertDialog.Builder(mContext);
                int bandTtl = mBands.size();
                String cntBands = "";
                for (int i = 0; i < bandTtl; i++) {
                    cntBands = cntBands + "Band " + (i + 1) + " is " + mBands.get(i) + "\n";

                }
                viewArrayList.setMessage(cntBands);
                viewArrayList.setIcon(R.drawable.guitar_s);
                viewArrayList.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                viewArrayList.setTitle("Bands You Added");
                viewArrayList.create();
                viewArrayList.show();
            }
        });


    }

    private float getAverage() {
        float bandAverage = 0;
        String bandValue = "";
        Iterator<String> iterator = mBands.iterator();

        while (iterator.hasNext()) {
            bandValue += iterator.next();
        }
        bandAverage = bandValue.length() / mBands.size();

        return bandAverage;
    }


}


