package com.sparkmt.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BeanSerializer<T> {
    private final ObjectOutputStream objectOutputStream;

    public BeanSerializer(String filename) throws IOException
    {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
    }

    public void writeObject(T object) throws IOException {
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
