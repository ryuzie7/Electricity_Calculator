package com.example.individualassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvFinalcost, tvTextfinalcost, tvInput, tvseekBar;
    EditText etUnitUsed;
    Button btnInput;

    SeekBar seekBar;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFinalcost = findViewById(R.id.tvFinalcost);
        tvTextfinalcost = findViewById(R.id.tvTextfinalcost);
        tvInput = findViewById(R.id.tvInput);
        tvseekBar = findViewById(R.id.tvseekBar);

        seekBar = findViewById(R.id.seekBar);
        etUnitUsed = findViewById(R.id.etUnitused);

        btnInput = findViewById(R.id.btnInput);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                seekBar.setMax(5);
                int cprogress = seekBar.getProgress();

                tvseekBar.setText(cprogress+" %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });

        btnInput.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                double unit, rebate, finalcost;

                try {
                    unit = Double.parseDouble(etUnitUsed.getText().toString());
                    rebate = seekBar.getProgress();

                    double unit200 = 0.218;
                    double unit100 = 0.334;
                    double unit300 = 0.516;
                    double unitmore = 0.546;
                    double totalcharges;

                    if (unit <= 200) {
                        totalcharges = unit * unit200;
                    } else if (unit <= 300) {
                        totalcharges = 200 * unit200 + (unit - 200) * unit100;
                    } else if (unit <= 500) {
                        totalcharges = 200 * unit200 + 100 * unit100 + (unit - 300) * unit300;
                    } else {
                        totalcharges = 200 * unit200 + 100 * unit100 + 200 * unit300 + (unit - 500) * unitmore;
                    }

                    finalcost = totalcharges - (totalcharges * (rebate / 100.0));

                    finalcost = Math.round(finalcost * 100.0) / 100.0;

                    tvFinalcost.setText("RM" + Double.toString(finalcost));

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please enter Unit Used (kWh)", Toast.LENGTH_SHORT).show();
                }
            }
            });

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_share)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Share with your friends and family. - https://t.co/app");
            startActivity(Intent.createChooser(shareIntent,null));
            return true;
        }
        else if(item.getItemId()==R.id.item_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);

        }
        return false;
    }
}