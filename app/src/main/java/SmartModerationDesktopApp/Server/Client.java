package SmartModerationDesktopApp.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.net.ConnectException;
import java.util.stream.Collectors;

public class Client {

    private final LoginInformation loginInformation;

    public Client(LoginInformation loginInformation) {
        this.loginInformation = loginInformation;
    }

    public String getModerationCards() throws IOException {

        //String emulatorAddress = "http://127.0.0.1:8001/";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("http://" + loginInformation.getAndroidIpAddress() + ":" + loginInformation.getAndroidPort() + "/moderationcards")
                .method("GET", null)
                .build();
        //        .url(emulatorAddress + "moderationcards")

        System.out.println("Request URL: " + request.url());

        try {
            System.out.println("Moderation card request");
            Response response = client.newCall(request).execute();
            System.out.println("Response: " + response);
            return response.body().string();
        } catch (ConnectException ex) {
            BufferedReader br = new BufferedReader(new FileReader("./src/testFiles/ModerationCardsTestSet.json"));
            return br.lines().collect(Collectors.joining());
        }
    }
}
