package todolistmanager.huji.ac.il.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Guy Oren on 4/22/2015.
 */
public class DB extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db = this.getWritableDatabase();
        this.db.execSQL("CREATE TABLE IF NOT EXISTS tasks(`task` TEXT, `dueDate` REAL);");
//        this.db.execSQL("DROP TABLE IF EXISTS tasks;");
    }

    public List<taskLine> getAllTasks(){
        List<taskLine> tasks = new ArrayList<taskLine>();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            do {
                tasks.add(new taskLine(cursor.getString(cursor.getColumnIndex("task")), cursor.getLong(cursor.getColumnIndex("dueDate"))));
            } while (cursor.moveToNext());
        }
        return tasks;
    }

    public void saveTask(String task, long dueDate) {
        db.execSQL("INSERT INTO tasks (`task`, `dueDate`) VALUES ('" +task + "','" + dueDate + "');");
    }

    public void deleteTask(String task, long dueDate){
        db.execSQL("DELETE FROM tasks WHERE task ='"+task+"' AND dueDate ='"+dueDate+"';");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not relevant
    }
}
