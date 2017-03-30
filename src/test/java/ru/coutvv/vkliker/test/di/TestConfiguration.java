package ru.coutvv.vkliker.test.di;

/**
 * @author coutvv
 */

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfiguration {
    private static final String filename = "app.properties";

    private String token;
    private int userId;

    private UserActor actor;
    private VkApiClient vk;

    /**
     * data для нотификатора в Телеграм
     **/
    private String teleToken;
    private String chatId;

    public TestConfiguration() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
        Properties props = new Properties();
        props.load(in);

        token = props.getProperty("token");
        userId = Integer.parseInt(props.getProperty("userId"));

        //telegram info
        teleToken = props.getProperty("teleToken");
        chatId = props.getProperty("chatId");
        actor = new UserActor(userId, token);
        TransportClient tc = HttpTransportClient.getInstance();
        vk = new VkApiClient(tc, new Gson());
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public UserActor getActor() {
        return actor;
    }

    @Bean
    public VkApiClient getVk() {
        return vk;
    }
}