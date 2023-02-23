import java.util.ArrayList;

public class Board <T extends Drawable>{
    ArrayList<T> shapes = new ArrayList<>();

    public void addNewShape(T shape){
        shapes.add(shape);
    }


    public double allPerimeter(){
        double sum = 0;
        for (T shape : shapes) {
            sum += shape.getPerimeter();
        }
        return sum;
    }


    public double allSurface(){
        double sum = 0;
        for (T shape : shapes){
            sum += shape.getSurface();
        }
        return sum;
    }


    public double allSide(){
        double sum = 0;
        for (T shape : shapes){
            try {
                sum += shape.getSide();
            } catch (SideNotDefinedException ignored) {

            }
        }
        return sum;
    }


    public double allSideException() throws SideNotDefinedException{
        double sum = 0;
        for (T shape : shapes){
            try {
                sum += shape.getSide();
            } catch (SideNotDefinedException e) {
                throw e;
            }
        }
        return sum;
    }


    public T minimumSurface(){
        T t = null;
        double min = Double.MAX_VALUE;
        for (T shape : shapes) {
            if (shape.getSurface() < min) {
                t = shape;
                min = t.getSurface();
            }
        }
        return t;
    }


    public ArrayList<T> sortedList(double x){
        ArrayList<T> sortedTS = new ArrayList<>();
        for (T shape : shapes) {
            if (shape.getPerimeter() > x)
                sortedTS.add(shape);
        }
        for (int i = 0; i<sortedTS.size(); ++i){
            for (int j = 0; j<sortedTS.size(); ++j){
                if (sortedTS.get(i).getSurface() < sortedTS.get(j).getSurface()){
                    T temp = sortedTS.get(i);
                    sortedTS.set(i, sortedTS.get(j));
                    sortedTS.set(j, temp);
                }
            }
        }
        return sortedTS;
    }


}
