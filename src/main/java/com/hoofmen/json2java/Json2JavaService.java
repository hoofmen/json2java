package com.hoofmen.json2java;

import com.hoofmen.json2java.model.Entity;
import com.hoofmen.json2java.parser.EntityParser;
import com.hoofmen.json2java.writer.JavaClassWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Osman H. on 9/15/17.
 */
@Service ("json2javaService")
public class Json2JavaService {

    @Autowired
    private EntityParser entityParser;

    @Autowired
    private JavaClassWriter javaClassWriter;

    public void readConfiguration(String path) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(path));
        String jsonString = new String(jsonData);
        Entity entity = entityParser.parseEntity(jsonString);
        entity.setPackageName("");

        // Send the details of the entity to be written into a Java file.
        javaClassWriter.writeToFile(entity);
    }

    public void printUsage(){
        System.out.println("");
        System.out.println(" Welcome to JSON-2-JAVA converter :()");
        System.out.println(" -------------------------------------");
        System.out.println(" Usage:");
        System.out.println("\tjava -jar json2java.jar <json file>");
        System.out.println("");
    }
}
