package todolistmanager.huji.ac.il.todolist;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListManagerActivity extends ActionBarActivity {

    ListView items;
    List<taskLine> listData;
    MyAdapter adapter;
    int indexToDelete;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        items = (ListView) findViewById(R.id.lstTodoItems);
        listData = new ArrayList<taskLine>();

        //on item long click
        items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                //build dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                builder.setMessage(listData.get(position).title);

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    //no action
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //update data and list view
                        listData.remove(position);
                        adapter = new MyAdapter(getApplicationContext(), R.layout.itemlayout, listData);
                        items.setAdapter(adapter);
                        dialog.cancel();
                    }
                });
                //check if title starts with "Call " - if it does add another button
                if (listData.get(position).title.startsWith("Call ")) {
                    builder.setNeutralButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //call
                            Intent dial = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:"+listData.get(position).title.substring(5)));
                                    startActivity(dial);
                            dialog.cancel();
                        }
                    });
                }

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

        //keep the colors updated
        items.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            //red is over due and blue is future task
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //for all items that appear on the list view
                Date now = new Date();
                for (i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++) {
                    //assign values
                    TextView dueDate = (TextView) (items.getChildAt(i-firstVisibleItem).findViewById(R.id.txtTodoDueDate));
                    TextView title = (TextView) (items.getChildAt(i-firstVisibleItem).findViewById(R.id.txtTodoTitle));

                    //analyze date
                    String[] info = dueDate.getText().toString().split("/");
                    Date d = new Date(Integer.parseInt(info[2])-1900, Integer.parseInt(info[1])-1,
                            Integer.parseInt(info[0]));

                    //color accordingly
                    if (now.after(d)) {
                        dueDate.setTextColor(Color.RED);
                        title.setTextColor(Color.RED);
                    } else {
                        dueDate.setTextColor(Color.BLUE);
                        title.setTextColor(Color.BLUE);
                    }
                }
            }
        });
    }

    //open new task dialog
    public void openForm() {
        Intent intent = new Intent(this, AddNewTodoItemActivity.class);
        startActivityForResult(intent, 0);
    }

    //When dialog ends
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        switch (resCode) {
            case RESULT_OK:
                addItem(data.getStringExtra("title"), data.getLongExtra("dueDate",0));
        }
    }

    //add item to the list view
    public void addItem(String task, long date) {
        //check if valid
        if (!task.equals("")) {
            //insert to data
            listData.add(new taskLine(task, date));
            //update listView
            adapter = new MyAdapter(getApplicationContext(), R.layout.itemlayout, listData);
            items.setAdapter(adapter);
        }
    }

    //pop up menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    //when item selected (Add)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuItemAdd) {
            openForm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}