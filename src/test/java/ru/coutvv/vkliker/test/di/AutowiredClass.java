package ru.coutvv.vkliker.test.di;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author coutvv
 */
public class AutowiredClass {

    @Autowired
    private VkApiClient vkApi;

    @Autowired
    private UserActor user;

    public void action() {
        System.out.println("ACT!" + vkApi.friends().get());
    }
}
