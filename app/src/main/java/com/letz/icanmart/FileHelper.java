package com.letz.icanmart;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {

    public static final String FILENAME = "listinfo.dat";

    public static void writeData(ArrayList<String> item, Context context) {
        try {// write data into a file
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> readData(Context context)
    {
//        Read data from the file
        ArrayList<String> itemList = null;
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // write read data to the array list
            itemList = (ArrayList<String>) ois.readObject();// add data to the arraylist, but this read data as obj. not string so.
            // need to convert it to string
            // we call it ' type casting'
        } catch (FileNotFoundException e) {
            itemList = new ArrayList<>(); // for the 1st opening.
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return itemList; // use this in the MainActivity
    }
}
