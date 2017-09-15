package com.hoofmen.json2java.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.hoofmen.json2java.model.Entity;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Osman H. on 9/15/17.
 */
@Service("entityParser")
public class EntityParser {

    public Entity parseEntity(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(jsonString);
        Iterator<Map.Entry<String,JsonNode>> attributesIterator = rootNode.fields();
        // We only care about the first node, from which we get the 'entity name' and 'entity body'
        Map.Entry<String,JsonNode> attribute = attributesIterator.next();
        return parseEntity(attribute);
    }

    /**
     * Parse the entity read from the Json
     * @param attribute
     * @return
     */
    private Entity parseEntity(Map.Entry<String,JsonNode> attribute) {
        // Parse the rest of the entity body to get the attributes in a Map
        Map<String, Entity> entityAttributesMap = this.getEntityAttributes(attribute);
        String entityName = this.toCamelCase(attribute.getKey());

        Entity entity = new Entity();
        entity.setClassName(entityName);
        entity.setAttributesMap(entityAttributesMap);

        return entity;
    }

    /**
     * Gets a Map of attributes JsonNode
     * @param attribute
     * @return
     */
    private Map<String, Entity> getEntityAttributes(Map.Entry<String,JsonNode> attribute){
        JsonNode entityBodyNode = attribute.getValue();
        Map<String, Entity> attributesMap = new HashMap<>();
        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = entityBodyNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String,JsonNode> field = fieldsIterator.next();
            String attributeKey = this.toCamelCase(field.getKey());
            Entity entity = this.getNodeType(field);
            attributesMap.put(attributeKey,entity);
        }

        return attributesMap;
    }

    /**
     * Given an MapEntry of a JsonNode, return an Entity with the corresponding Java type
     * @param field
     * @return
     */
    private Entity getNodeType(Map.Entry<String,JsonNode> field) {
        Entity entity = new Entity();
        String fieldKey = field.getKey();
        if (field.getValue().getNodeType() == JsonNodeType.STRING) {
            entity.setClassName("String");
        }else if (field.getValue().getNodeType() == JsonNodeType.NUMBER){
            entity.setClassName(this.getNumberType(field.getValue()));
        }else if (field.getValue().getNodeType() == JsonNodeType.ARRAY){
            entity = this.createEntityOfTypeList(field);
        }else if (field.getValue().getNodeType() == JsonNodeType.OBJECT){
            //recursive call to parse custom Object
            entity = this.parseEntity(field);
        }else if (field.getValue().getNodeType() == JsonNodeType.BOOLEAN){
            entity.setClassName("boolean");

            //TODO: what happens in these cases?
        }else if (field.getValue().getNodeType() == JsonNodeType.BINARY){

        }else if (field.getValue().getNodeType() == JsonNodeType.MISSING){

        }else if (field.getValue().getNodeType() == JsonNodeType.NULL){

        }else if (field.getValue().getNodeType() == JsonNodeType.POJO){

        }
        return entity;
    }

    /**
     * Gets the Java type of number the Json field is
     * e.g:
     *  1.2 => Double
     *  1   => Integer
     * @param node
     * @return
     */
    private String getNumberType(JsonNode node){
        try{
            Integer.valueOf(node.asText());
            return "Integer";
        }catch(NumberFormatException ex){}
        try{
            Double.valueOf(node.asText());
            return "Double";
        }catch(NumberFormatException ex){}
        //TODO: Will we have another type of number?
        return "NUMBER";
    }

    /**
     * Process the array type of field found in the Json and returns a new Entity with a Java class name of 'List<Java Type>'
     * @param field
     * @return
     */
    private Entity createEntityOfTypeList(Map.Entry<String,JsonNode> field){
        JsonNode firstElement = field.getValue().get(0);
        Map.Entry<String, JsonNode> entry = new AbstractMap.SimpleEntry<String, JsonNode>(field.getKey(),firstElement);
        Entity entity = this.getNodeType(entry);
        entity.setClassName("List<" + entity.getClassName() + ">");


        return entity;
    }

    /**
     * Removes non alphanumeric characters from a String and the returns the camel case version of the string.
     * e.g:
     * latest-posts => LatestPost
     * @param string
     * @return
     */
    private String toCamelCase(String string){
        string = WordUtils.capitalizeFully(string.replaceAll("[^a-zA-Z\\d\\s:]"," "));
        String finalstring = string.replaceAll(" ","");;
        return finalstring;
    }
}