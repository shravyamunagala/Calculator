package com.lwu.myapplication;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView txvScreen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvScreen = findViewById(R.id.txvScreen);
        txvScreen.setText(display);

    }
    DecimalFormat df = new DecimalFormat("#.#############");


    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }

    private void updateScreen(){
        txvScreen.setText(display);
    }

    public void onClickNumber(View v){
        Log.d("CalcX", "onClickNumber: entered");
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText().toString();
        updateScreen();
         Log.d("CalcX", "onClickNumber: exited"+ display);
    }

    private boolean isOperator(char op){
        Log.d("CalcX", "isOperator: entered");
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;

        }
    }
    public void onClickOperator(View v){
        Log.d("CalcX", "onClickOperator: entred");
        if(display == "") return;
        Button b = (Button)v;
        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }
        if(currentOperator != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText().toString();
        currentOperator = b.getText().toString();
        updateScreen();
        Log.d("CalcX", "onClickOperator: exited");
    }

    public void onClickClear(View v){

        clear();
        updateScreen();

    }
    private double operate(String a, String b, String op){
        Log.d("CalcX", "operate: entered operator"+op);

                switch (op) {

                    case "+":
                        return Double.valueOf(a) + Double.valueOf(b);
                    case "-":
                        return Double.valueOf(a) - Double.valueOf(b);
                    case "x":
                        return Double.valueOf(a) * Double.valueOf(b);
                    case "÷":
                        try {
                            return Double.valueOf(a) / Double.valueOf(b);
                        } catch (Exception e) {
                            Log.d("Calc", e.getMessage());
                        }
                    default:
                        return -1;
                }

    }
    private boolean getResult(){
        Log.e("CalcX", "getResult: entered"  );
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        Double d = operate(operation[0], operation[1], currentOperator);
        result = String.valueOf(df.format(d));
        return true;

    }
    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) {
            return;
        }
        txvScreen.setText(display + "\n" + String.valueOf(result));

    }

    public void onClickDot(View v) {

        Log.d("CalcX", "onClickDot: entered");

        display = display + ".";
        if (display.contains("-") || display.contains("+") || display.contains("x") || display.contains("÷")) {
            Log.d("CalcX", "onClickDot: contain method entered");
            String[] ar = display.split("[-+÷x]+");
            if (ar[1] == "" || ar[1].matches("^[0-9]*\\.?[0-9]*$")) {
                txvScreen.setText(display);

            } else {
                clear();
            }
            Log.d("CalcX", "onClickDot: contain method exited");
        } else if (display.matches("^[0-9]*\\.?[0-9]*$")) {
            Log.d("CalcX", "onClickDot222: contain method entered");
            txvScreen.setText(display);
        } else {
            clear();
        }

        Log.d("CalcX", "onClickDot: exited"+display);

    }
}
