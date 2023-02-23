import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    //------------------------------------------------------------------------------------------------------------------
    public List<String> getMethodNames(Object object){
        Class<?> myClass = object.getClass();
        Method[] allMethodsArray = myClass.getDeclaredMethods();
        ArrayList<String> allMethodsArrayList = new ArrayList<>();
        for (Method method : allMethodsArray) {
            allMethodsArrayList.add(method.getName());
        }
        return allMethodsArrayList;
    }
    //------------------------------------------------------------------------------------------------------------------
    public Object getFieldContent(Object object, String fieldName) throws Exception {
        Class<?> myClass = object.getClass();
        Field[] allFields = myClass.getDeclaredFields();
        for (Field allField : allFields) {
            if (allField.getName().equals(fieldName)) {
                allField.setAccessible(true);
                return allField.get(object);
            }
        }
        throw new NoSuchFieldException();
    }
    //------------------------------------------------------------------------------------------------------------------
    public void setFieldContent(Object object, String fieldName, Object content) throws Exception{
        Field field = null;
        Class<?> myClass = object.getClass();
        Field[] allFields = myClass.getDeclaredFields();
        for (Field allField : allFields) {
            if (allField.getName().equals(fieldName)) {
                field = allField;
            }
        }
        if (field == null) {
            throw new NoSuchFieldException();
        }


        field.setAccessible(true);
        try {
            if (field.toGenericString().contains("static") && !field.toGenericString().contains("final")) {
                field.set(null, content);
            }
            if (field.toGenericString().contains("final") && field.toGenericString().contains("static")){
                setFinalStaticField(object.getClass(), fieldName, content, object);
            }
            else{
                field.set(object, content);
            }
        }
        catch (Exception e){

        }
    }

    private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value, Object object)
            throws ReflectiveOperationException {

        Field field = null;
        Field[] allFields = clazz.getDeclaredFields();
        for (int i = 0; i<allFields.length; ++i){
            if (allFields[i].getName().equals(fieldName)){
                field = allFields[i];
            }
        }
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }
    //------------------------------------------------------------------------------------------------------------------
    public Object call(Object object, String methodName, Object[] parameters) throws Exception{
        Class<?>[] classes = new Class[parameters.length];
        for (int i = 0; i<parameters.length; ++i){
            classes[i] = parameters[i].getClass();
        }
        Method[] allMethods = object.getClass().getDeclaredMethods();
        for (int i = 0; i<allMethods.length; ++i){
            if (allMethods[i].getName().equals(methodName)){
                allMethods[i].setAccessible(true);
                Method method = allMethods[i];
                Class<?>[] methodParameters = method.getParameterTypes();
                if (methodParameters.length == parameters.length){
                    return method.invoke(object, parameters);
                }
            }
        }

        throw new NoSuchMethodException();
    }
    //------------------------------------------------------------------------------------------------------------------
    public Object createANewObject(String fullClassName, Object[] initials) throws Exception{
        Class<?>[] arguments = new Class[initials.length];
        for (int i = 0; i< initials.length; ++i){
            arguments[i] = initials[i].getClass();
        }
        Class<?> myClass = Class.forName(fullClassName);
        Constructor<?> ctor = myClass.getConstructor(arguments);
        return ctor.newInstance(initials);
    }
    //------------------------------------------------------------------------------------------------------------------
    public String debrief(Object object){
        Class<?> myClass = object.getClass();
        String response = "";
        response += "Name: ";
        response += myClass.getSimpleName();
        response += "\n";
        response += "Package: ";
        response += myClass.getPackageName();
        response += "\n";
        response += numberOfConstructors(myClass);
        response += "\n";
        response += "===\n";
        response += "Fields:\n";
        response += fieldsInformation(myClass);
        response += numberOfFields(myClass);
        response += "===\n";
        response += "Methods:\n";
        response += methodsInformation(myClass);
        response += numberOfMethods(myClass);



        return  response;
    }

    public String numberOfConstructors (Class<?> myClass){
        Constructor<?>[] allConstructors = myClass.getDeclaredConstructors();
        return "No. of Constructors: " + allConstructors.length;
    }

    public String fieldsInformation (Class<?> myClass){
        Field[] allFields = myClass.getDeclaredFields();
        for (int i = 0; i<allFields.length; ++i){
            for (int j = i; j<allFields.length; ++j){
                if (allFields[i].getName().compareToIgnoreCase(allFields[j].getName()) > 0){
                    Field temp = allFields[i];
                    allFields[i] = allFields[j];
                    allFields[j] = temp;
                }
            }
        }
        String response = "";
        for (Field allField : allFields) {
            String information = allField.toGenericString();

            String[] informations = information.split(" ");
            String finality = "";
            for (String s : informations) {
                if (s.contains("$")){
                    String[] x = s.split("\\$");
                    finality += x[x.length - 1] + " ";
                }
                else if (s.contains(".")) {
                    String[] x = s.split("\\.");
                    finality += x[x.length - 1] + " ";
                }
                else {
                    finality += s + " ";
                }
            }
            response += finality.trim() + "\n";
        }

        return response;
    }

    public String numberOfFields (Class<?> myClass){
        Field[] allFields = myClass.getDeclaredFields();
        return "(" + allFields.length + " fields)\n";
    }

    public String methodsInformation (Class<?> myClass){
        Method[] allMethods = myClass.getDeclaredMethods();
        for (int i = 0; i<allMethods.length; ++i){
            for (int j = i; j<allMethods.length; ++j){
                if (allMethods[i].getName().compareToIgnoreCase(allMethods[j].getName()) > 0){
                    Method temp = allMethods[i];
                    allMethods[i] = allMethods[j];
                    allMethods[j] = temp;
                }
            }
        }
        String x = "";
        for (Method allMethod : allMethods) {

            if (allMethod.getReturnType().toString().contains("$")){
                String[] breaked = allMethod.getReturnType().toString().split("\\$");
                x += breaked[breaked.length - 1] + " ";
            }
            else if (allMethod.getReturnType().toString().contains(".")){
                String[] breaked = allMethod.getReturnType().toString().split("\\.");
                x += breaked[breaked.length - 1] + " ";
            }
            else{
                x += allMethod.getReturnType().toString() + " ";
            }
            x += allMethod.getName() + "(";
            Class<?>[] allParameters = allMethod.getParameterTypes();
            for (int i = 0; i < allParameters.length; ++i) {
                String name = allParameters[i].getSimpleName();
                if (i < allParameters.length - 1) {
                    x += name + ", ";
                } else {
                    x += name;
                }
            }
            x += ")\n";
        }

        return x;
    }

    public String numberOfMethods (Class<?> myClass){
        Method[] allMethods = myClass.getDeclaredMethods();
        return "(" + allMethods.length + " methods)";
    }
    //------------------------------------------------------------------------------------------------------------------
    int counter = 0;
    public Object clone(Object toClone) throws Exception {
        Constructor[] ctors = toClone.getClass().getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            ctor.setAccessible(true);
            Object c = ctor.newInstance();
            Field[] fields = c.getClass().getDeclaredFields();
            Field[] mainFields = toClone.getClass().getDeclaredFields();
            for (int i = 0; i<mainFields.length; ++i){
                for (int j = 0; j<fields.length; ++j){
                    if (fields[j].getName().equals(mainFields[i].getName())){
                        mainFields[i].setAccessible(true);
                        fields[j].setAccessible(true);
                        if (mainFields[i].getGenericType().getTypeName().equals("int[]")){
                            setFieldContent(c, mainFields[i].getName(), new int[100]);
                        }
                        else if (mainFields[i].getGenericType().getTypeName().equals("byte[]")){
                            setFieldContent(c, mainFields[i].getName(), new byte[100]);
                        }
                        else if (mainFields[i].getGenericType().getTypeName().equals("char[]")){
                            setFieldContent(c, mainFields[i].getName(), new char[100]);
                        }
                        else if (mainFields[i].getType().getSimpleName().equals("int")
                            || mainFields[i].getType().getSimpleName().equals("float")
                            || mainFields[i].getType().getSimpleName().equals("double")
                            || mainFields[i].getType().getSimpleName().equals("String")
                            || mainFields[i].getType().getSimpleName().equals("Type")
                            || mainFields[i].getType().getSimpleName().equals("Stationery")){
                            setFieldContent(c, mainFields[i].getName(), mainFields[i].get(toClone));
                        }
                        else if (mainFields[i].get(toClone) != null){
                            setFieldContent(c, mainFields[i].getName(), clone(mainFields[i].get(toClone)));
                        }
                    }
                }
            }
            if (!c.getClass().getSuperclass().toString().equals("class java.lang.Object")){
                Field[] nextLayerFields = c.getClass().getSuperclass().getDeclaredFields();
                Field[] nextLayerMainFields = toClone.getClass().getSuperclass().getDeclaredFields();
                for (int i = 0; i<nextLayerMainFields.length; ++i){
                    for (int j = 0; j<nextLayerFields.length; ++j){
                        nextLayerMainFields[i].setAccessible(true);
                        nextLayerFields[j].setAccessible(true);
                        if (nextLayerFields[j].getName().equals(nextLayerMainFields[i].getName())){
                            if (nextLayerMainFields[i].getType().getSimpleName().equals("int")
                                    || nextLayerMainFields[i].getType().getSimpleName().equals("float")
                                    || nextLayerMainFields[i].getType().getSimpleName().equals("double")
                                    || nextLayerMainFields[i].getType().getSimpleName().equals("String")
                                    || nextLayerMainFields[i].getType().getSimpleName().equals("Type")
                                    || nextLayerMainFields[i].getType().getSimpleName().equals("Stationery")){
                                setFieldContent2(c, nextLayerMainFields[i].getName(), nextLayerMainFields[i].get(toClone));
                            }
                            else if (nextLayerMainFields[i].get(toClone) != null) {
                                setFieldContent2(c, nextLayerMainFields[i].getName(), clone(nextLayerMainFields[i].get(toClone)));
                            }
                        }
                    }
                }
            }
            return c;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException x) {
            x.printStackTrace();
        }
        return null;
    }


    public void setFieldContent2(Object object, String fieldName, Object content) throws Exception{
        Field field = null;
        Class<?> myClass = object.getClass().getSuperclass();
        Field[] allFields = myClass.getDeclaredFields();
        for (Field allField : allFields) {
            if (allField.getName().equals(fieldName)) {
                field = allField;
            }
        }
        if (field == null) {
            throw new NoSuchFieldException();
        }


        field.setAccessible(true);
        try {
            field.set(object, content);
        }
        catch (Exception e){

        }
    }
    //------------------------------------------------------------------------------------------------------------------

}
