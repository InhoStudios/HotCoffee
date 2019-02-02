package io.inhostudios.hotcoffee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ByteReader {

    // Path of a file
    static String path = System.getProperty("user.dir") + "\\src\\main\\resources\\audio.wav";
    static File file;

    static {
        file = new File(path);
    }

    // Method which write the bytes into a file
    static void writeByte(byte[] bytes)
    {
        System.out.println(path);
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    static String readByte(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }

}