package todolistmanager.huji.ac.il.todolist;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TodoListManagerActivity extends ActionBarActivity {

    EditText itemToAdd;
    ListView items;
    List<String> listData;
    ArrayAdapter<String> adapter;
    Context cxt;
    int indexToDelete;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        cxt = this;
        itemToAdd = (EditText) findViewById(R.id.edtNewItem);
        items = (ListView) findViewById(R.id.lstTodoItems);
        listData = new ArrayList<String>();
        i = 0;

        //on item long click - deletion
        items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                indexToDelete = position;
                //build dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
                builder.setMessage(listData.get(position))
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //update data and list view
                                listData.remove(indexToDelete);
                                adapter = new ArrayAdapter<String>(cxt, R.layout.itemlayout, R.id.line, listData);
                                items.setAdapter(adapter);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            //no action
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
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

            //keep the colors ordered
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //for all items that appear on the list view
                for (i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++) {
                    TextView line = (TextView) (items.getChildAt(i-firstVisibleItem).findViewById(R.id.line));
                    //the color of the row depends on it's index
                    if (i % 2 == 0) {
                        line.setTextColor(Color.RED);
                    } else {
                        line.setTextColor(Color.BLUE);
                    }
                }
            }
        });
    }

    //add item to the list view
    public void addItem(View view) {
        //check if valid
        if (!itemToAdd.getText().toString().equals("")) {
            //insert to data
            listData.add(itemToAdd.getText().toString());
            //update listView
            adapter = new ArrayAdapter<String>(this, R.layout.itemlayout, R.id.line, listData);
            items.setAdapter(adapter);
            itemToAdd.setText("");
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
        if (id == R.id.add) {
            addItem(item.getActionView());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}