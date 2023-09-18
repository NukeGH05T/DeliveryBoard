import com.google.gson.Gson;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthenticationTest {
    @Test
    public void testAuth() {
        String user = "bxnn";
        try {
            URL url = new URL("https://raw.githubusercontent.com/NukeGH05T/d35u68/main/db.txt");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            if (httpURLConnection.getResponseCode() == 200) {
                InputStream is = httpURLConnection.getInputStream();
                StringBuffer sb = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();

                String rawJson = "";
                while (line != null) {
                    //System.out.println(line);
                    rawJson += line.trim();
                    line = br.readLine();
                }

                //System.out.println(rawJson);

                //Parse rawJson
                for (String user_ : rawJson.split(",")) {
                    if (user_.equalsIgnoreCase(user)) System.out.println("Verified!");
                }

                System.out.println("Not Verified");
            } else {
                return;
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
