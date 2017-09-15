package com.hoofmen.json2java.utils;

/**
 * Created by Osman H. on 9/15/17.
 */
public interface Constants {
    String JAVA_POJO_TEMPLATE = "java_pojo_template";
    String JAVA_SERVICE_TEMPLATE = "java_service_template";

    String JAVA_FILE_EXTENSION = ".java";

    String TAB = "\t";
    String NEW_LINE = "\n";

    String PACKAGE_LINE = "package %s;" + NEW_LINE + NEW_LINE;
    String IMPORT_LINE = "import %s;" + NEW_LINE + NEW_LINE;
    String CLASS_LINE = "public class %s {" + NEW_LINE;
    String ATTRIBUTE_LINE = TAB + "private %s %s;" + NEW_LINE;
    String SETTER_METHOD_LINE = TAB + "public void set%s(%s %s) {" + NEW_LINE;
    String SET_ACTION_LINE = TAB + TAB + "this.%s = %s;" + NEW_LINE;
    String GETTER_METHOD_LINE = TAB + "public %s get%s() {" + NEW_LINE;
    String GET_ACTION_LINE = TAB + TAB + "return %s;" + NEW_LINE;
    String CLOSE_CURLY_BRACKET = "}" + NEW_LINE;

}