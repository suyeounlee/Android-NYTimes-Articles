package algonquin.cst2335.group_project.nytimes;
import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * API_Database for the app.
 *
 * @author Su Yeoun Lee
 */

@Database(entities = {API_Items.class}, version = 1)
public abstract class API_Database extends RoomDatabase {

    public abstract API_ItemsDAO apiDAO();

}
