package pdem.stackoverflow;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


    /**
     * Make a future launch an exception in the accept.
     */
    public class CompletableFutureTest2
    {
        public static void main(String[] args)
            throws InterruptedException, ExecutionException
        {
            CompletableFuture<String> future = readPage();

            CompletableFuture<Void> future2 = future.thenAccept(page->{
                System.out.println(page);
                throw new IllegalArgumentException("unexpected exception");
            });

            future2.exceptionally(e->{
              e.printStackTrace(System.err);
              return null;
            });

        }

        private static CompletableFuture<String> readPage()
        {

          CompletableFuture<String> future = new CompletableFuture<>();
          new Thread(()->{
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            // FUTURE: normal process
            future.complete("page");

          }).start();
            return future;
        }


    }