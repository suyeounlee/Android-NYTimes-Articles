package algonquin.cst2335.group_project.weather;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface WeatherDAO {
    @Insert
    public long insertMessage(WeatherMessage m);

    @Query("SELECT * FROM WeatherMessage")
    public List<WeatherMessage> getAllMessages();

    @Delete
    public void deleteMessage(WeatherMessage m);
}
