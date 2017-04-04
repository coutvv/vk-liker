package ru.coutvv.vkliker.test;

import java.util.concurrent.*;

/**
 * @author coutvv
 */
public class TestMyLikerExecutor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(() -> {
//            System.out.println("this end!");
//        });
        ExecutorCompletionService completionService = new ExecutorCompletionService(executorService);
        completionService.submit(() -> {
            Thread.sleep(1000);
            System.out.println("shit!");
           return "string";
        });
//        while() {
//            System.out.println("fuck");
//        }
        System.out.println("before");
        Future f = completionService.take();

//        f.get();
        System.out.println("after");
    }
}
