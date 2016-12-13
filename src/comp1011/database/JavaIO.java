/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp1011.database;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author tom
 */
public class JavaIO {
    
    Scanner read = new Scanner(System.in);
    BufferedWriter bufferWriter = null;
    
    //Method that is used to check for a given directory
    //Created if it does not exist
    public void checkDirectory() 
    {
        try {
            String dirName = "~/Desktop/JavaTest";
            File directory = new File(dirName);
            
            if(!directory.exists()) {
                try {
                    directory.mkdirs();
                } catch(SecurityException err) {
                    
                }
            }
            
        } catch(SecurityException err) {
            
        } catch(Exception error) {
            
        }
    }
    
    public void appendToFile() throws IOException {
        checkDirectory();
        
        String message;
        
        File file = new File("~/Desktop/JavaTest/Errors.txt");
        
        
        if(!file.exists()) {
            file.createNewFile();
        }
        
        message = read.nextLine();
        
        FileWriter fileWriter = new FileWriter("~/Desktop/JavaTest/Errors.txt", true);
        
        bufferWriter = new BufferedWriter(fileWriter);
        bufferWriter.newLine();
        bufferWriter.write(message);
        bufferWriter.close();
    }
    
    public static void main(String[] args)
    {
        JavaIO io = new JavaIO();
        try {
            io.appendToFile();
        } catch (IOException ex) {
            Logger.getLogger(JavaIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Method that is used to check for a given directory
    
}
