package com.example.puzzlegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int emptyX=3;
    private int emptyY=3;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shuffle){
            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i/4][i%4].setClickable(true);
            }
            loadViews();
            generateNumbers();
            loadDataToViews();
            return true;
        } else if (id == R.id.out){
            System.exit(1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDataToViews() {
        emptyX=3;
        emptyY=3;
        for (int i = 0; i < group.getChildCount() - 1; i++) {
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundResource(android.R.drawable.btn_default);
            if (tiles[i] == 1){
                buttons[i/4][i%4].setText("A");
            }else if(tiles[i] == 2){
                buttons[i/4][i%4].setText("B");
            }else if(tiles[i] == 3){
                buttons[i/4][i%4].setText("C");
            }else if(tiles[i] == 4){
                buttons[i/4][i%4].setText("D");
            }else if(tiles[i] == 5){
                buttons[i/4][i%4].setText("E");
            }else if(tiles[i] == 6){
                buttons[i/4][i%4].setText("F");
            }else if(tiles[i] == 7){
                buttons[i/4][i%4].setText("G");
            }else if(tiles[i] == 8){
                buttons[i/4][i%4].setText("H");
            }else if(tiles[i] == 9){
                buttons[i/4][i%4].setText("I");
            }else if(tiles[i] == 10){
                buttons[i/4][i%4].setText("J");
            }else if(tiles[i] == 11){
                buttons[i/4][i%4].setText("K");
            }else if(tiles[i] == 12){
                buttons[i/4][i%4].setText("L");
            }else if(tiles[i] == 13){
                buttons[i/4][i%4].setText("M");
            }else if(tiles[i] == 14){
                buttons[i/4][i%4].setText("N");
            }else if(tiles[i] == 15){
                buttons[i/4][i%4].setText("O");
            }
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this,R.color.asbestos));

    }

    private void generateNumbers() {
        int n = 15;
        Random random = new Random();
        while (n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }

        if (!isSolvable())
            generateNumbers();
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j]>tiles[i])
                    countInversions++;
            }
        }
        return countInversions%2 == 0;
    }

    private void loadNumbers() {
        tiles = new int[16];
        for (int i = 0; i < group.getChildCount() - 1; i++) {
            tiles[i] = i+1;
            
        }
    }

    private void loadViews() {
        group = findViewById(R.id.group);
        buttons = new Button[4][4];
        for (int i = 0; i < group.getChildCount(); i++) {
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
        }
    }

    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';
        if ((Math.abs(emptyX-x)==1&&emptyY==y)||(Math.abs(emptyY-y)==1&&emptyX==x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.asbestos));
            emptyX=x;
            emptyY=y;
            checkWin();
        }
    }

    private void checkWin() {
        boolean isWin = false;
        if (emptyX==3&&emptyY==3){
            for (int i = 0; i < group.getChildCount() - 1; i++) {
                if ((buttons[0][0].getText() == "A")&&(buttons[0][1].getText() == "B")&&
                        (buttons[0][2].getText() == "C")&&(buttons[0][3].getText() == "D")&&
                        (buttons[1][0].getText() == "E")&&(buttons[1][1].getText() == "F")&&
                        (buttons[1][2].getText() == "G")&&(buttons[1][3].getText() == "H")&&
                        (buttons[2][0].getText() == "I")&&(buttons[2][1].getText() == "J")&&
                        (buttons[2][2].getText() == "K")&&(buttons[2][3].getText() == "L")&&
                        (buttons[3][0].getText() == "M")&&(buttons[3][1].getText() == "N")&&
                        (buttons[3][2].getText() == "O")){
                        isWin = true;
                }else {
                    isWin = false;
                    break;
                }
            }
        }
        if (isWin){
            Toast.makeText(this, "Congrats, You Win !!!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i/4][i%4].setClickable(false);
            }
        }
    }
}