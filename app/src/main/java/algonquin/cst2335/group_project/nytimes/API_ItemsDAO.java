package algonquin.cst2335.group_project.nytimes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object for the API_Items table.
 *
 * @author Su Yeoun Lee
 */
@Dao
public interface API_ItemsDAO {
    /**
     * Inserts a new API_Items object into the database.
     *
     * @param a The API_Items object to insert.
     * @return The ID of the newly inserted row.
     */
    @Insert
    long insertData(API_Items a);

    /**
     * Gets all API_Items objects from the database.
     *
     * @return A list of all API_Items objects in the database.
     */
    @Query("Select * from API_Items")
    List<API_Items> getAllData();

    /**
     * Deletes an existing API_Items object from the database.
     *
     * @param a The API_Items object to delete.
     */
    @Delete
    void deleteData(API_Items a);

}
