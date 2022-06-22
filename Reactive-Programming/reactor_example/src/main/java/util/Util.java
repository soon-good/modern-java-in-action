package util;

import java.util.function.Consumer;

public class Util {
    public static Consumer<Object> onNext(){
        return obj -> System.out.println("Received >>> " + obj);
    }

    public static Consumer<Throwable> onError(){
        return error -> System.out.println("Error >>> " + error.getMessage());
    }

    public static Runnable onComplete(){
        return () -> System.out.println("Completed");
    }

    public static void sleepSeconds(int n){
        try {
            Thread.sleep(n * 1000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
