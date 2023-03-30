package algonquin.cst2335.group_project.weather;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Data Access Object for WeatherMessage table.
 * @author Shing Kwan Chow
 */
@Dao
public interface WeatherDAO {

    /**
     * Insert a new WeatherMessage into the table.
     * @param m the WeatherMessage to insert
     * @return the row ID of the inserted WeatherMessage
     */
    @Insert
    public long insertMessage(WeatherMessage m);

    /**
     * Get all WeatherMessages from the table.
     * @return a list of WeatherMessages
     */
    @Query("SELECT * FROM WeatherMessage")
    public List<WeatherMessage> getAllMessages();

    /**
     * Delete a WeatherMessage from the table.
     * @param m the WeatherMessage to delete
     */
    @Delete
    public void deleteMessage(WeatherMessage m);
}
