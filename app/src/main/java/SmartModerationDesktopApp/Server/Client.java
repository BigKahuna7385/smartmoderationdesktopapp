package SmartModerationDesktopApp.Server;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client {

    public String getModerationCards() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/moderationcards")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
