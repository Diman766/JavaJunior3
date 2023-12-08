package org.example.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        Student student = new Student("Станислав", 37, 4.2);

        System.out.println(student);


        saveTasksToFile("student.xml", student);
        saveTasksToFile("student.json", student);
        saveTasksToFile("student.bin", student);

        student = loadTasksFromFile("student.json");
        System.out.println(student);

    }

    public static void saveTasksToFile(String fileName, Student student) {
        try {
            if (fileName.endsWith(".json")) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), student);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(student);
                }
            } else if (fileName.endsWith(".xml")) {
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.writeValue(new File(fileName), student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Student loadTasksFromFile(String fileName) {

        Student student = null;
        File file = new File(fileName);

        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    student = objectMapper.readValue(file, Student.class);
                } else if (fileName.endsWith(".bin")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        student = (Student) ois.readObject();
                    }
                } else if (fileName.endsWith(".xml")) {
                    XmlMapper xmlMapper = new XmlMapper();
                    student = xmlMapper.readValue(file, Student.class);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return student;
    }
}