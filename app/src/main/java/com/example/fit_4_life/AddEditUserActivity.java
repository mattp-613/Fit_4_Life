package com.example.fit_4_life;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

import Database.UserDAO;


public class AddEditUserActivity extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private EditText textUsername;
    private EditText textPassword;
    private Button buttonSave;
    private Button buttonCancel;

    private User user;
    private boolean needRefresh;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        this.textUsername = (EditText)this.findViewById(R.id.editText_user_username);
        this.textPassword = (EditText)this.findViewById(R.id.editText_user_password);

        this.buttonSave = (Button)findViewById(R.id.button_save);
        this.buttonCancel = (Button)findViewById(R.id.button_cancel);

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonSaveClicked();
            }
        });

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonCancelClicked();
            }
        });

        Intent intent = this.getIntent();
        this.user = (User) intent.getSerializableExtra("user");
        if(user== null)  {
            this.mode = MODE_CREATE;
        } else  {
            this.mode = MODE_EDIT;
            this.textUsername.setText(user.getUsername());
            this.textPassword.setText(user.getPassword());
        }
    }

    // User Click on the Save button.
    public void buttonSaveClicked()  {
        UserDAO db = new UserDAO(this);

        String username = this.textUsername.getText().toString();
        String password = this.textPassword.getText().toString();


        if(username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter username & password", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode == MODE_CREATE ) {
            this.user= new User(username,password);
            db.addUser(user);
        } else  {
            this.user.setUsername(username);
            this.user.setPassword(password);
            db.updateUser(user);
        }

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }

    // User Click on the Cancel button.
    public void buttonCancelClicked()  {
        // Do nothing, back MainActivity.
        this.onBackPressed();
    }

    // When completed this Activity,
    // Send feedback to the Activity called it.
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }

}
