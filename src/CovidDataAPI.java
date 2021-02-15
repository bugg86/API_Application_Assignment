import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// You are only able to use the org.json library after you've installed it.
import org.json.*;

public class CovidDataAPI {
    public static void getCovidData()
    {
        String baseURL = "https://api.covidtracking.com";
        String countryCallAction = "/v1/us/";   //Call action for looking up data in a country
        String date = "20210212";
        String stateCallAction = "/v1/states/nc/";  //Call action for looking up data in a specific state
        String urlString = baseURL + stateCallAction + date + ".json";
        URL url;

        try {
            //Connect to API
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Check response code
            int status = connection.getResponseCode();
            if (status != 200) {
                System.out.printf("Error: Could not load JSON page: " + status);
            } else {
                //Parsing input stream into a text string.
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while((inputLine = in.readLine()) != null){
                    content.append(inputLine);
                }
                //Close connections
                in.close();
                connection.disconnect();
                //Print out JSON string.
                System.out.println("Output: " + content.toString() + "\n");

                //Parse into JSON object.
                JSONObject obj = new JSONObject(content.toString());
                //Date the data is pulled from.
                int dateInt = obj.getInt("date");
                System.out.println("Date data is pulled from: " + dateInt);
                //State the data is from.
                String state = obj.getString("state");
                System.out.println("State: " + state);
                //Number of deaths this day.
                int deathIncrease = obj.getInt("deathIncrease");
                System.out.println("Deaths from this day: " + deathIncrease);
                //Number of positive tests this day.
                int positiveIncrease = obj.getInt("positiveIncrease");
                System.out.println("Positive deaths from this day: " + positiveIncrease);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }
    }
}
