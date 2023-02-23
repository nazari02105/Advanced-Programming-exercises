import model.primitive.Circle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    String myName = "ali";
    User user = new User();

    private Main (){

    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public int firstMethod (){
        System.out.println("first method");
        return 1;
    }

    private int secondMethod (int[] x){
        System.out.println("second method");
        return 2;
    }

    public static void thirdMethod (float[] x){
        System.out.println("third method");
    }

    private static void forthMethod (float x){
        System.out.println("forth method");
    }

    public void fifthMethod (String x, Integer y){
        System.out.println(x);
        System.out.println(y);
    }


    public static void main(String[] args) throws Exception {
        Agent agent = new Agent();
        Main mainMain = new Main();

        Main myMain = (Main) agent.clone(mainMain);
        System.out.println(myMain.myName);

        mainMain.myName = "sss";

        System.out.println(mainMain.myName);
        System.out.println(myMain.myName);


    }
}
