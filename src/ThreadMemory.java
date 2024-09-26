import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;

// ThreadMemory class
public class ThreadMemory extends Thread
{
    // instance variables
    // create a new atomic integer
    //atomicInteger allows the value to be updated in a thread safe way
    private static final AtomicInteger lineNum = new AtomicInteger(0);
    private File out;
    private File in;
    //we use byte as the file is read in as bytes rather than words
    private static byte[] file;
    //create a lock object so that only one thread can write to the file at a time
    //also meaning we don't need to add a synchronized block to the methods
    private static final Object lock = new Object();    

    // constructor
    //checks to see if the entered file exists  
    //if it does, set the file to the file entered
    //if it doesn't, throw an exception
    //set the out file to the file entered
    public ThreadMemory(File in, File out) throws IOException
    {
        if (in.exists())
        {
            this.in = in;
        }
        else
        {
            throw new IOException("File does not exist");
        }

        this.out = out;
    }
    
    // static method
    //this reads and returns the file content as bytes
    //it takes a file object as a parameter 
    //and throws an IOException if the file is not found during the file reading process
    private static byte[] readFile(File file) throws IOException
    {
        //create a new byte array with the length of the file
        byte[] data = new byte[(int) file.length()];
        //create a new file input stream object
        //this reads the data from the file
        FileInputStream fs = new FileInputStream(file);
        //create a new byte array input stream object
        //this reads the data from the byte array
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        //create a new byte array with a length of 1024
        byte[] buff = new byte[1024];
        int bytes = 0;

        //this reads the data from the file and writes it to the byte array
        while ((bytes = fs.read(buff)) != -1)
        {
            data = new byte[bytes];
            //this copies the data from the byte array to the byte array input stream
            System.arraycopy(buff, 0, data, 0, bytes);
        }

        //close the file input stream
        fs.close();
        //return the data
        return data;
    }

    //this method reads the file and constructs words from the file
    private String readWord()
    {
        //create a new string builder object to build to words
        StringBuilder word = new StringBuilder();
        int place;

        //this loop continues until the end of the file
        while ((place = lineNum.getAndIncrement()) < file.length)
        {
            char c = (char) file[place];
            if (c == '\n')
            {
                break;
            }
            //if c is not a newline character, add it to the word
            word.append(c);
        }

        //return the word as a string
        return word.toString();
    }

    private boolean isA(String word)
    {
        //this checks if the word starts with an 'a' or 'A'
        return word.toLowerCase().startsWith("a") && word.toUpperCase().startsWith("A");
    }


    @Override
    public void run()
    {
        try 
        {
            //loop continues until the end of the file
            while(lineNum.get() < file.length)
            {
            //create a new string object and set it to the word
            String word = readWord();
            //if the word starts with an 'a' or 'A', write it to the file
            if (isA(word))
            {
                //create a synchronized block so that only one thread can write to the file at a time
                synchronized (lock)
                {
                    //create a new file output stream object
                    FileOutputStream fs = new FileOutputStream(out, true);
                    //write the word to the file
                    fs.write(word.getBytes());
                    fs.write('\n');
                    fs.close();
                }
            }
        }
    }
        catch (IOException e)
        {
            e.printStackTrace();
    }
}
}
