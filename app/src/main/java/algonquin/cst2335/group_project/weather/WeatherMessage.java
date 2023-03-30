package algonquin.cst2335.group_project.weather;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherMessage {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="city")
    protected String name;

    @ColumnInfo(name="weather_descriptions")
    protected String weather_descriptions;

    @ColumnInfo(name="temperature")
    protected Double temperature;

    @ColumnInfo(name="timeRetrieved")
    protected String timeRetrieved;

    @ColumnInfo(name="humidity")
    protected Double humidity;

    public WeatherMessage() {
    }

    WeatherMessage(String city, String c, Double temperature, String t, Double humidity)
    {
        this.name = city;
        this.weather_descriptions = c;
        this.temperature = temperature;
        this.timeRetrieved = t;
        this.humidity = humidity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return name;
    }

    public void setCity(String city) {
        this.name = city;
    }

    public String getCondition() {
        return weather_descriptions;
    }

    public void setCondition(String condition) {
        this.weather_descriptions = condition;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getTimeRetrieved() {
        return timeRetrieved;
    }

    public void setTimeRetrieved(String timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }

    public String getName() {return name;}

    public String getWeather_descriptions() {return weather_descriptions;}

    public Double getHumidity() {return humidity;}
}
