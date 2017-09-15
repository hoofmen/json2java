package com.hoofmen.json2java.writer;

import com.hoofmen.json2java.model.Entity;
import com.hoofmen.json2java.utils.Constants;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Osman H. on 9/15/17.
 */
@Service("javaClassWriter")
public class JavaClassWriter {

    public void writeToFile(Entity entity) throws IOException {
         FileWriter fileWriter = new FileWriter(entity.getClassName() + Constants.JAVA_FILE_EXTENSION);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        //TODO: no package name yet
        printWriter.printf(Constants.PACKAGE_LINE, entity.getPackageName());

        //TODO: imports pending
        //loop -> printWiter.print... imports

        printWriter.printf(Constants.CLASS_LINE, entity.getClassName());

        // print the attribute section of the class
        for (Map.Entry<String, Entity> attribute : entity.getAttributesMap().entrySet()) {
            if (this.isCustomClass(attribute.getValue().getClassName())) {
                //creating a new entity to avoid issues with Collections of Custom Classes
                Entity customEntity = new Entity();
                customEntity.setClassName(attribute.getKey());
                customEntity.setAttributesMap(attribute.getValue().getAttributesMap());
                //recursive call to write the Custom Class
                this.writeToFile(customEntity);
            }
            printWriter.printf(Constants.ATTRIBUTE_LINE, attribute.getValue().getClassName(), getAttributeFormat(attribute.getKey()));

        }
        printWriter.print(Constants.NEW_LINE);

        // print the setter/getter of the class
        for (Map.Entry<String, Entity> attribute : entity.getAttributesMap().entrySet()) {
            printWriter.printf(Constants.SETTER_METHOD_LINE, attribute.getKey(), attribute.getValue().getClassName(), getAttributeFormat(attribute.getKey()));
            printWriter.printf(Constants.SET_ACTION_LINE, getAttributeFormat(attribute.getKey()), getAttributeFormat(attribute.getKey()));
            printWriter.print(Constants.TAB + Constants.CLOSE_CURLY_BRACKET);

            printWriter.printf(Constants.GETTER_METHOD_LINE, attribute.getValue().getClassName(), attribute.getKey());
            printWriter.printf(Constants.GET_ACTION_LINE, getAttributeFormat(attribute.getKey()));
            printWriter.print(Constants.TAB + Constants.CLOSE_CURLY_BRACKET);
        }
        printWriter.print(Constants.CLOSE_CURLY_BRACKET);
        printWriter.close();
    }

    //TODO Making too many calls to this method!
    private String getAttributeFormat(String attribute){
        return Character.toLowerCase(attribute.charAt(0)) + attribute.substring(1);
    }

    private boolean isCustomClass(String key){
        if (key.equals("String") || key.equals("Double") || key.equals("Integer") || key.equals("boolean")){
            return false;
        }
        return true;
    }
}
