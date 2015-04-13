package todolistmanager.huji.ac.il.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAdapter extends ArrayAdapter<taskLine> {

    public MyAdapter(Context context, int textViewResourceId, List<taskLine> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.itemlayout, parent, false);
        //get objects
        TextView title = (TextView)view.findViewById(R.id.txtTodoTitle);
        TextView dueDate = (TextView)view.findViewById(R.id.txtTodoDueDate);
        //title + due date
        title.setText(" "+getItem(position).title+" ");
        //when the date is not valid
        if (getItem(position).dueDate==0) {
            //in case getExtra didn't receive information
            dueDate.setText("No due date");
        } else {
            //inset information to list view
            Date d = new Date(getItem(position).dueDate);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dueDate.setText(dateFormat.format(d));
        }
        return view;
    }

}
