package SmartModerationDesktopApp.Server;

import SmartModerationDesktopApp.Observer.ClientObservable;
import SmartModerationDesktopApp.Observer.ClientObserver;
import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class Client implements ClientObservable {

    private final LoginInformation loginInformation;
    private ClientObserver clientObserver;

    public Client(LoginInformation loginInformation) {
        this.loginInformation = loginInformation;
    }


    public void getModerationCards() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(loginInformation.getAndroidIpAddress())
                .port(loginInformation.getAndroidPort())
                .addPathSegment("moderationcards")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();

        System.out.println("Request URL: " + request.url());

        Thread thread = new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                getModerationCardsJsonString(response.body().string());
            } catch (IOException e) {

            }
        });
        thread.start();

    }

    @Override
    public void initObserver(ClientObserver observer) {
        this.clientObserver = observer;
    }

    @Override
    public void getModerationCardsJsonString(String moderationCardJson) {
        clientObserver.getModerationCardsJsonString(moderationCardJson);

    }

    public boolean deleteModerationCard(long cardId) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("http://" + loginInformation.getAndroidIpAddress() + ":" + loginInformation.getAndroidPort() + "/moderationcard/" + cardId)
                .method("DELETE", null)
                .build();

        System.out.println("Request URL: " + request.url());

        try {
            System.out.println("Moderation card deletion call");
            Response response = client.newCall(request).execute();
            System.out.println("Response: " + response.body().string());
            return response.code() < 500;
        } catch (ConnectException ex) {
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
