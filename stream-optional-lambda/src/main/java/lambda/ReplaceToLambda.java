package lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReplaceToLambda {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = ReplaceToLambda.class.getClassLoader();
        URL url = classLoader.getResource("test-result.txt");
        if (url == null) {
            throw new IllegalStateException("file not found");
        }
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream("test-result.txt")))) {
            executorService.submit(() -> {
                    try {
                        while(reader.ready()) {
                            String line = reader.readLine();
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally{
                        latch.countDown();
                    }
            });
            latch.await();
        } finally{
            executorService.shutdown();
        }
    }
}
