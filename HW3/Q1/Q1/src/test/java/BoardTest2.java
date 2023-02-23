import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BoardTest2 {
    @Test
    public void allPerimeter() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);

        double perimeter = rd1.getPerimeter() + rd2.getPerimeter() + rd3.getPerimeter();
        Assertions.assertEquals(board.allPerimeter(), perimeter);
    }


    @Test
    public void allSurface() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);

        double surface = rd1.getSurface() + rd2.getSurface() + rd3.getSurface();
        Assertions.assertEquals(board.allSurface(), surface);
    }


    @Test
    public void allSide() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Circle c = new Circle(10);
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);
        board.addNewShape(c);

        double side = rd1.getSide() + rd2.getSide() + rd3.getSide();
        Assertions.assertEquals(board.allSide(), side);
    }


    @Test
    public void allSideException() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Circle c = new Circle(10);
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);
        board.addNewShape(c);

        double side = rd1.getSide() + rd2.getSide() + rd3.getSide();
        try {
            Assertions.assertEquals(board.allSideException(), side);
        } catch (SideNotDefinedException e) {
        }
    }


    @Test
    public void minimumSurface() {
        Circle c1 = new Circle(10);
        Circle c2 = new Circle(20);
        Circle c3 = new Circle(30);
        Board<Drawable> board = new Board<>();

        board.addNewShape(c2);
        board.addNewShape(c1);
        board.addNewShape(c3);

        Assertions.assertEquals(board.minimumSurface(), c1);
    }


    @Test
    public void sortedList() {
        Circle c1 = new Circle(10);
        Circle c2 = new Circle(20);
        Circle c3 = new Circle(30);
        Board<Drawable> board = new Board<>();

        board.addNewShape(c3);
        board.addNewShape(c1);
        board.addNewShape(c2);

        Assertions.assertEquals(board.sortedList(70).get(0), c2);
        Assertions.assertEquals(board.sortedList(70).get(1), c3);
    }
}
