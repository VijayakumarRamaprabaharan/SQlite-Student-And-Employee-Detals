package com.example.vj.sqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SecondTable extends AppCompatActivity implements View.OnClickListener {

    EditText editId,editName,editSalary;
    Button btAdd,btModify,btView,btViewAll,btDelete,btInfo;
    SQLiteDatabase dbEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_table);

        editId=(EditText)findViewById(R.id.editid);
        editName=(EditText)findViewById(R.id.editName1);
        editSalary=(EditText)findViewById(R.id.editSalary);
        btAdd=(Button)findViewById(R.id.btnAdd1);
        btDelete=(Button)findViewById(R.id.btnDelete1);
        btModify=(Button)findViewById(R.id.btnModify1);
        btView=(Button)findViewById(R.id.btnView1);
        btViewAll=(Button)findViewById(R.id.btnViewAll1);
        btInfo=(Button)findViewById(R.id.btnShowInfo1);

        btAdd.setOnClickListener(this);
        btInfo.setOnClickListener(this);
        btView.setOnClickListener(this);
        btViewAll.setOnClickListener(this);
        btModify.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        dbEmp=openOrCreateDatabase("EmployeeDb", Context.MODE_PRIVATE, null);
        dbEmp.execSQL("CREATE TABIL IF NOT EXISTS employee (id VARCHAR PRIMARY KEY ASC, name VARCHAR, salary VARCHAR);");

    }

    @Override
    public void onClick(View v) {
        if (v == btAdd) {
            if (editId.getText().toString().trim().length() == 0 ||
                    editName.getText().toString().trim().length() == 0 ||
                    editSalary.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please Enter all values");
                return;
            }
            dbEmp.execSQL("INSERT INTO employee) value('" + editId.getText() + "','" + editName.getText() + "','" + editSalary.getText() + "');");
            showMessage("Success", "Values Added");
            clearText();
        }
        if (v == btDelete) {
            if (editId.getText().toString().trim().length() == 0) {
                showMessage("Error", "Enter Id value");
            }
            Cursor c = dbEmp.rawQuery("SELECT * FROM employee where id='" + editId.getText() + "'", null);
            if (c.moveToFirst()) {
                dbEmp.execSQL("DELETE FROM employee where id='" + editId.getText() + "'", null);
                showMessage("Success", "Data Deleted");

            } else {
                showMessage("Error", "Invalid Id");
            }
            clearText();

        }
        if (v == btModify) {
            if (editId.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c = dbEmp.rawQuery("SELECT * FROM student WHERE rollno='" + editId.getText() + "'", null);
            if (c.moveToFirst()) {
                dbEmp.execSQL("UPDATE student SET name='" + editName.getText() + "',marks='" + editSalary.getText() +
                        "' WHERE rollno='" + editId.getText() + "'");
                showMessage("Success", "Record Modified");
            } else {
                showMessage("Error", "Invalid Rollno");
            }
            clearText();
        }
        if (v == btView) {
            if (editId.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c = dbEmp.rawQuery("SELECT * FROM student WHERE rollno='" + editId.getText() + "'", null);
            if (c.moveToFirst()) {
                editName.setText(c.getString(1));
                editSalary.setText(c.getString(2));
            } else {
                showMessage("Error", "Invalid RollNo");
                clearText();
            }
        }
        if (v == btViewAll) {
            Cursor c = dbEmp.rawQuery("SELECT * FROM student", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Id: " + c.getString(0) + "\n");
                buffer.append("Name: " + c.getString(1) + "\n");
                buffer.append("Salary: " + c.getString(2) + "\n\n");
            }
            showMessage("Employee Details", buffer.toString());
        }
        if (v == btInfo) {
            showMessage("Employee Management Application", "Developed By vj");
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        editId.setText("");
        editName.setText("");
        editSalary.setText("");
        editId.requestFocus();
    }
}


