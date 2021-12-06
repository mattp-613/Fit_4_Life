package com.example.fit_4_life;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Database.UserDAO;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;

    private final List<User> userList = new ArrayList<User>();
    private ArrayAdapter<User> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = (ListView) findViewById(R.id.listView);

        UserDAO db = new UserDAO(this);
        db.createDefaultUsersIfNeed();

        List<User> list=  db.getAllUsers();
        this.userList.addAll(list);


        // Define a new Adapter
        // 1 - Context
        // 2 - Layout for the row
        // 3 - ID of the TextView to which the data is written
        // 4 - the List of data

        this.listViewAdapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.userList);

        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);

        // Register the ListView for Context menu
        registerForContextMenu(this.listView);
    }

    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)    {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, "View User");
        menu.add(0, MENU_ITEM_CREATE , 1, "Create User");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit User");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete User");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final User selectedUser = (User) this.listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Toast.makeText(getApplicationContext(),selectedUser.getUsername(),Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this, AddEditUserActivity.class);

            // Start AddEditUserActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, AddEditUserActivity.class);
            intent.putExtra("User", (Serializable) selectedUser);

            // Start AddEditUserActivity, (with feedback).
            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selectedUser.getUsername()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteUser(selectedUser);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    // Delete a record
    private void deleteUser(User user)  {
        UserDAO db = new UserDAO(this);
        db.deleteUser(user);
        this.userList.remove(user);
        // Refresh ListView.
        this.listViewAdapter.notifyDataSetChanged();
    }

    // When AddEditUserActivity completed, it sends feedback.
    // (If you start it using startActivityForResult ())
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                this.userList.clear();
                UserDAO db = new UserDAO(this);
                List<User> list = db.getAllUsers();
                this.userList.addAll(list);


                // Notify the data change (To refresh the ListView).
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

}