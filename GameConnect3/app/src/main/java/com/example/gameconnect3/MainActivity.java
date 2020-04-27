package com.example.gameconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // let 0:empty, 1:red, 2:yellow
    public int[] status = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[][] winPlan = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public boolean isPlaying = false;

    public int playerNow = 0;

    public void dropIn(View view) {

        if (isPlaying) {
            ImageView imageview = (ImageView) view;
            int tappedViewIndex = Integer.parseInt(imageview.getTag().toString());
            int clickedPlaceState = status[tappedViewIndex];

            if (clickedPlaceState == 0) {
                imageview.setTranslationY(-1500);
                if (playerNow == 1) {
                    imageview.setImageResource(R.drawable.red);
                    status[tappedViewIndex] = 1;
                    playerNow = 0;
                } else {
                    imageview.setImageResource(R.drawable.yellow);
                    status[tappedViewIndex] = 2;
                    playerNow = 1;
                }
                imageview.animate().translationYBy(1500).setDuration(300);

                int winColor = checkWin(status, winPlan);
                if (winColor != 0) {
                    TextView tv = (TextView) findViewById(R.id.textView);
                    Button b = (Button) findViewById(R.id.button);
                    if (winColor == 1) {
                        tv.setText(R.string.text_win_game_red);
                    } else {
                        tv.setText(R.string.text_win_game_yellow);
                    }
                    b.setText(R.string.game_restart);
                    isPlaying = false;
                }
            }
        }
    }

    private int checkWin(int[] status, int[][] winPlan) {
        int winColor = 0;
        for (int[] plan : winPlan) {
            int num1 = status[plan[0]];
            if(num1 == 0) continue;
            int num2 = status[plan[1]];
            if (num1 != num2) continue;
            int num3 = status[plan[2]];
            if (num1 == num3) {
                winColor = num1;
                break;
            }
        }
        return winColor;
    }

    public void startClick(View view) {
        Button botton = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.textView);
        String bottonText = botton.getText().toString();
        if (bottonText == getString(R.string.game_reset)){
            resetGame(status);
        }

        if (!isPlaying) {
            botton.setText(R.string.game_reset);
            textView.setText(R.string.text_game_start);
            isPlaying = true;
        } else {
            // restart or reset the game
            textView.setText(R.string.text_game_reseted);
            botton.setText(R.string.game_start);
            isPlaying = false;
        }
    }

    private void resetGame(int[] status) {
        int[] idList = {R.id.iv1,R.id.iv2,R.id.iv3,R.id.iv4,R.id.iv5,R.id.iv6,R.id.iv7,R.id.iv8};
        for (int i = 0; i < 9; i++) {
            status[i] = 0;
            ImageView iv = (ImageView) findViewById(idList[i]);
            iv.setImageResource(0);
        }
        isPlaying = false;
        TextView tv = (TextView) findViewById(R.id.textView);
        Button b = (Button) findViewById(R.id.button);
        tv.setText(R.string.text_game_reseted);
        b.setText(R.string.game_start);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
