package ru.coutvv.vkliker.test.di;

import com.vk.api.sdk.client.actors.UserActor;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

import javax.annotation.Resource;

/**
 * @author coutvv
 */


public class TestSpringBuild {

    @Resource
    AutowiredClass cl;

    @Test
    public void test() {

        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

        UserActor user = context.getBean(UserActor.class);
        System.out.println(user.getId());
        cl.action();

    }

}


