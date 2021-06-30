package com.sparkmt.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BeanDeserializer{
    private final ObjectInputStream objectInputStream;

    public BeanDeserializer(String filename) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(filename);
        objectInputStream = new ObjectInputStream(fileInputStream);
    }

    public Object getObject() throws IOException, ClassNotFoundException{
        return objectInputStream.readObject();
    }
}
