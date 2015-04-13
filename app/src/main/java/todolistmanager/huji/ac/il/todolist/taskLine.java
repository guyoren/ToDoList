package todolistmanager.huji.ac.il.todolist;
/**
 * Created by Guy Oren on 3/18/2015.
 */
public class taskLine {
    //task title
    public String title;
    //task due date
    public long dueDate;

    public taskLine(String newTask, long date){
        this.title = newTask;
        this.dueDate = date;
    }
}
