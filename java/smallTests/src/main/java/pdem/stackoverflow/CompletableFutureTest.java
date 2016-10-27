package pdem.stackoverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureTest
{
    public static void main(String[] args) 
        throws InterruptedException, ExecutionException
    {
        CompletableFuture<String> contentsCF = readPage();
        CompletableFuture<List<String>> linksCF = 
            contentsCF.thenApply(CompletableFutureTest::getLinks);

        CompletableFuture<Void> completionStage = linksCF.thenAccept(list -> 
        {
            String a = null;
            System.out.println(a.toString());
        });        

        // This will NOT cause an exception to be thrown, because
        // the part that was passed to "thenAccept" will NOT be
        // evaluated (it will be executed, but the exception will
        // not show up)
        List<String> result = linksCF.get();
        System.out.println("Got "+result);


        // This will cause the exception to be thrown and
        // wrapped into an ExecutionException. The cause
        // of this ExecutionException can be obtained:
        try
        {
            completionStage.get();
        }
        catch (ExecutionException e)
        {
            System.out.println("Cought "+e);
            Throwable cause = e.getCause();
            System.out.println("cause: "+cause);
        }

        // Alternatively, the exception may be handled by
        // the future directly:
        completionStage.exceptionally(e -> 
        { 
            System.out.println("Future exceptionally finished: "+e);
            return null; 
        });

        try
        {
            completionStage.get();
        }
        catch (Throwable t)
        {
            System.out.println("Already handled by the future "+t);
        }

    }

    private static List<String> getLinks(String s)
    {
        System.out.println("Getting links...");
        List<String> links = new ArrayList<String>();
        for (int i=0; i<10; i++)
        {
            links.add("link"+i);
        }
        dummySleep(1000);
        return links;
    }

    private static CompletableFuture<String> readPage()
    {
        return CompletableFuture.supplyAsync(new Supplier<String>() 
        {
            @Override
            public String get() 
            {
                System.out.println("Getting page...");
                dummySleep(1000);
                return "page";
            }
        });
    }

    private static void dummySleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}