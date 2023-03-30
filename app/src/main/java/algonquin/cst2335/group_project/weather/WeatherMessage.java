package algonquin.cst2335.group_project.weather;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a single weather information item, including the city name,
 * weather description, temperature, time retrieved, and humidity.
 * @author Shing Kwan Chow
 */
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

    /**
     * Default constructor for WeatherMessage.
     */
    public WeatherMessage() {
    }

    /**
     * Constructor for WeatherMessage.
     *
     * @param city the name of the city
     * @param c the weather description
     * @param temperature the temperature value
     * @param t the time when the weather data was retrieved
     * @param humidity the humidity value
     */
    WeatherMessage(String city, String c, Double temperature, String t, Double humidity)
    {
        this.name = city;
        this.weather_descriptions = c;
        this.temperature = temperature;
        this.timeRetrieved = t;
        this.humidity = humidity;
    }

    /**
     * Setter for the id
     * @param id the primary key
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the name
     * @return name of the city
     */
    public String getCity() {
        return name;
    }

    /**
     * Setter for the name
     * @param city the name of the city
     */
    public void setCity(String city) {
        this.name = city;
    }

    /**
     * Getter for the weather descriptions
     * @return weather_descriptions the weather descriptions
     */
    public String getCondition() {
        return weather_descriptions;
    }

    /**
     * Setter for the weather descriptions
     * @param condition the weather descriptions
     */
    public void setCondition(String condition) {
        this.weather_descriptions = condition;
    }

    /**
     * Getter for temperature
     * @return temperature the temperature of the city
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * Setter for temperature
     * @param temperature the temperature of the city
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * Getter for timeRetrieved
     * @return timeRetrieved the time at which the city's weather info is retrieved
     */
    public String getTimeRetrieved() {
        return timeRetrieved;
    }

    /**
     * Setter for timeRetrieved
     * @param timeRetrieved the time at which the city's weather info is retrieved
     */
    public void setTimeRetrieved(String timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }

    /**
     * Getter for name
     * @return name the city's name
     */
    public String getName() {return name;}

    /**
     * Getter for weather_descriptions
     * @return weather_descriptions the weather descriptions
     */
    public String getWeather_descriptions() {return weather_descriptions;}

    /**
     * Getter for humidity
     * @return humidity
     */
    public Double getHumidity() {return humidity;}
}
