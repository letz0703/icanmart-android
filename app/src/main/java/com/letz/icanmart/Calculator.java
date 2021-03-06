package com.letz.icanmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnEqual, btnAC, btnMulti, btnDivide, btnPlus, btnMinus, btnDel;

    private TextView textViewResult, textViewHistory;
    private String number = null; // 이건 뭐지? object 다. textViewResult가 empty인지 아닌지 알 수 있다
    double firstNum = 0;
    double lastNum = 0;

    String status = null;
    boolean operator = false;

    DecimalFormat myFormatter = new DecimalFormat("######.######");

    String history, currentResult;
    boolean dot = true;
    boolean btnACcontrol = true;
    boolean btnEqualsControl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMulti = findViewById(R.id.btnMulti);
        btnDivide = findViewById(R.id.btnDivide);

        btnAC = findViewById(R.id.btnAC);
        btnDel = findViewById(R.id.btnDel);
        btnDot = findViewById(R.id.btnDot);
        btnEqual = findViewById(R.id.btnEqual);

        textViewResult = findViewById(R.id.textViewResult);
        textViewHistory = findViewById(R.id.textViewHistory);

        // activate buttons
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                numberClick("0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("9");
            }
        });

        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = null;
                status = null;
                textViewResult.setText("0");
                textViewHistory.setText("");
                firstNum = 0;
                lastNum = 0;
                dot = true;
                btnACcontrol = true;
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// L2
                if (btnACcontrol) {
                    textViewResult.setText("0");
                } else {
                    number = number.substring(0, number.length() - 1);

                    if (number.length() == 0) {
                        btnDel.setClickable(false);
                    } else if (number.contains(".")) {
                        dot = false;//pass false to "dot" variable
                    } else {
                        dot = true;
                    }
                    textViewResult.setText(number);
                }
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator) {
                    if (status == "multiplication") {
                        multiply();
                    } else if (status == "sum") {
                        plus();
                    } else if (status == "subtraction") {
                        minus();
                    } else {
                        divide();
                    }
                }
                status = "division";
                operator = false;
                number = null;
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "x");

                if (operator) {
                    if (status == "division") {
                        divide();
                    } else if (status == "plus") {
                        plus();
                    } else if (status == "subtraction") {
                        minus();
                    } else {
                        multiply();
                    }
                }
                operator = false;
                status = "multiplication";
                number = null;
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dot) {
                    if (number == null) {
                        String number = "0.";
                    } else {
                        number = number + ".";
                    }
                }
                textViewResult.setText(number);
                dot = false;
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "+");

                if (operator) {
                    if (status == "multiplication") {
                        multiply();
                    } else if (status == "division") {
                        divide();
                    } else if (status == "subtraction") {
                        minus();
                    } else {
                        plus();
                    }
                }
                status = "sum";
                operator = false;
                number = null;
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "-");

                if (operator) {
                    if (status == "multiplication") {
                        multiply();
                    } else if (status == "division") {
                        divide();
                    } else if (status == "sum") {
                        plus();
                    } else {
                        minus();
                    }
                }
                status = "subtraction";
                operator = false;
                number = null;
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator) {
                    if (status == "multiplication") {
                        multiply();
                    } else if (status == "division") {
                        divide();
                    } else if (status == "sum") {
                        plus();
                    } else if (status == "subtraction") {
                        minus();
                    } else {
                        firstNum = Double.parseDouble(textViewResult.getText().toString());
                    }
                }
                operator = false;
                btnEqualsControl = true;
            }
        });
    }

    public void numberClick(String view) {
        if (number == null) {
            number = view;

        } else if (btnEqualsControl) {
            firstNum = 0;
            lastNum = 0;
            number = view;
        } else {
            number = number + view;
        }

        textViewResult.setText(number);
        operator = true;
        btnACcontrol = false;
        btnDel.setClickable(true);
        btnEqualsControl = false;
    }

    public void plus() {
        lastNum = Double.parseDouble(textViewResult.getText().toString());
        firstNum = firstNum + lastNum;

        // print first number's value to the screen.
//        textViewResult.setText("" + firstNum);
        textViewResult.setText(myFormatter.format(firstNum));
        dot = true;
    }

    public void minus() {
        if (firstNum == 0) {
            firstNum = Double.parseDouble(textViewResult.getText().toString());
        } else {
            lastNum = Double.parseDouble(textViewResult.getText().toString());
            firstNum = firstNum - lastNum;
        }
//        textViewResult.setText(""+ firstNum);
        textViewResult.setText(myFormatter.format(firstNum));
        dot = true;
    }

    public void multiply() {
        if (firstNum == 0) {
            firstNum = 1;
            lastNum = Double.parseDouble(textViewResult.getText().toString());
            firstNum = firstNum * lastNum;
        } else {
            lastNum = Double.parseDouble(textViewResult.getText().toString());
            firstNum = firstNum * lastNum;
        }
//        textViewResult.setText(""+ firstNum);
        textViewResult.setText(myFormatter.format(firstNum));
        dot = true;
    }

    public void divide() {
        history = textViewHistory.getText().toString();
        currentResult = textViewResult.getText().toString();
        textViewHistory.setText(history + currentResult + "/");

        if (firstNum == 0) {
            lastNum = Double.parseDouble(textViewResult.getText().toString());
            firstNum = lastNum / 1;
        } else {
            lastNum = Double.parseDouble(textViewResult.getText().toString());
            firstNum = firstNum / lastNum;
        }
//        textViewResult.setText(""+firstNum);
        textViewResult.setText(myFormatter.format(firstNum));
        dot = true;
    }
}