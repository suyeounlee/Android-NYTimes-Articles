package algonquin.cst2335.group_project.weather;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherMessage.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDAO cmDAO();
}
