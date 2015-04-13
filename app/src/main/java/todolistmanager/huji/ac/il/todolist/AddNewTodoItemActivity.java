package todolistmanager.huji.ac.il.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Date;

/**
 * Created by Guy Oren on 3/18/2015.
 */
public class AddNewTodoItemActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_todo_item_activity);
    }

    //save the task
    public void ok(View view){
        //find objects
        EditText task = (EditText)findViewById(R.id.edtNewItem);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        //check that there is a title
        if (!task.getText().toString().equals("")) {
            //transmit data
            Intent result = new Intent();
            result.putExtra("title", task.getText().toString());
            Date d = new Date(datePicker.getYear()-1900,datePicker.getMonth(),datePicker.getDayOfMonth());
            result.putExtra("dueDate", d.getTime());
            setResult(RESULT_OK, result);
            finish();
        }
    }
    //do nothing
    public void cancel(View view){
        finish();
    }
}
