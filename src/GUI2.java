import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.io.FileReader;
import javax.swing.table.DefaultTableModel;

public class GUI2 extends JFrame
{
    //create buttons
    private JButton select, process;
    public GUI2()
    {
        //setting default operations for the gui
        this.setTitle("Word Memorizer");
        this.setSize(700, 700);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creating tables for the incoming file and the output the programme has on the file
        JTable in = new JTable();
        JTable out = new JTable();

        //JPanel for the top of the gui
        //with a set Border Title
        JPanel top = new JPanel();
        top.setBorder(new TitledBorder("Word Memorizer"));
        this.add(top, BorderLayout.NORTH);

        //JPanel for the select button
        JPanel left = new JPanel();
        left.setBorder(new TitledBorder("Select File"));
        this.add(left, BorderLayout.WEST);
        JScrollPane input = new JScrollPane(in);

        //adding the button and scrollpane to the panel
        select = new JButton("Select File");
        left.add(input);
        left.add(select);

        //JPanel for the processing button
        //with a set border title
        JPanel right = new JPanel();
        right.setBorder(new TitledBorder("Process File"));
        this.add(right, BorderLayout.EAST);
        JScrollPane output = new JScrollPane(out);

        //adding the process button to the panel
        process = new JButton("Process File");
        right.add(output);
        right.add(process);

        //action listener for the select button
        select.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //create a file chooser to select a file
                JFileChooser fileChooser = new JFileChooser();
                //this sets the default directory to the user's home directory
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                //this opens the file chooser dialog and waits for the user to select a file
                int result = fileChooser.showOpenDialog(GUI2.this);
                //this checks if the user has selected a file
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    //this retrieves the file the user has selected
                    File selectedFile = fileChooser.getSelectedFile();
                    //this checks if the file is a text file
                    if (selectedFile.getName().endsWith(".txt"))
                    {
                        System.out.println("Selected file: " + selectedFile.getName());

                        try
                        {
                            //this updates the table with the selected file
                            updateTable(selectedFile.getPath(), in);
                        }
                        catch (IOException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {
                        System.out.println("Invalid file type");
                    }
                }
            }
        });

        process.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //this checks if the table is empty
                if (in.getRowCount() == 0)
                {
                    System.out.println("No file selected");
                }
                else
                {
                    try
                    {
                        //this updates the table with the output file
                        updateTable("words.txt", out); 
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    //this method updates the table with the file
    private void updateTable(String file, JTable table) throws IOException
    {
        //this reads the file and updates the table
        Vector<Vector<String>> info = new Vector<>();
        //this reads the file from bytes to readable text
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String read;
        //this reads the file line by line
        while ((read = reader.readLine()) != null)
        {
            //this adds the line to the table
            Vector<String> line = new Vector<>();
            line.add(read);
            info.add(line);
        }
        //this closes the reader
        reader.close();
        //this updates the table with the file
        table.setModel(new DefaultTableModel(info, new Vector<>()));
    }

    public static void main(String[] args)
    {
        GUI2 gui = new GUI2();
        gui.setVisible(true);
    }
}