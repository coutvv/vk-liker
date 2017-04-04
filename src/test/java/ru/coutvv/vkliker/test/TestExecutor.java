package ru.coutvv.vkliker.test;

import java.util.concurrent.*;

/**
 * @author coutvv
 */
public class TestExecutor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> fut = executorService.submit(() -> {
            Thread.sleep(1000);
            System.out.println("shit!");
            executorService.shutdown();
            return "string";
        });
        System.out.println("before");

//        f.get();
        System.out.println("some");
        fut.cancel(false);
        if(!fut.isCancelled())
            System.out.println(fut.get());
        System.out.println("after");
        //executorService.shutdown();
    }
}
