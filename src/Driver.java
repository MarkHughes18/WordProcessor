import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Driver class
public class Driver 
{
    // instance variables
    int threads;
    File in;
    File out;

    // constructor
    public Driver(File in, int threads)
    {
        // set instance variables
        this.in = in;
        this.threads = threads;

        // create executor service
        //can run multiple threads at once
        ExecutorService service = Executors.newFixedThreadPool(threads);
        //create a new thread memory object
        try
        {
            service.execute(new ThreadMemory(in, new File("finished.txt")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //shut down the service
            service.shutdown();
            //wait for the service to terminate
            while (!service.isTerminated())
            {
                //wait for the service to terminate
            }
        }

        System.out.println("Words beginning with 'a' have been written in " + out.getName());
    }

    // get methods
    public String getIn()
    {
        return this.in.getPath();
    }

    // get methods
    public String getOut()
    {
        return this.out.getPath();
    }

    // main method
    public static void main(String[] args)
    {
        // create a new file object
        File in = new File("words.txt");
        int threads = 4;

        // create a new driver object to instantiate the methods in the driver class
        //correctly
        Driver driver = new Driver(in, threads);
    }
}


