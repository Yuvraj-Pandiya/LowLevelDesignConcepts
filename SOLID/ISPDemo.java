import java.util.ArrayList;
import java.util.List;

public class ISPDemo {

    // Fat interface forcing all shapes to implement both 2D and 3D methods
    interface FatShape {
        double calculateArea();
        double calculateVolume(); 
    }

    static class FatRectangle implements FatShape {
        private double width, height;
        
        public FatRectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double calculateArea() { return width * height; }

        // Rectangle forced to implement volume
        public double calculateVolume() {
            throw new UnsupportedOperationException("Rectangles do not have volume!");
        }
    }

    static class FatCube implements FatShape {
        private double side;

        public FatCube(double side) {
            this.side = side;
        }

        public double calculateArea() { return 6 * side * side; }
        public double calculateVolume() { return side * side * side; }
    }

    static class FatClient {
        public static void printVolumes(List<FatShape> shapes) {
            System.out.println("--- Processing Fat Shapes (ISP Violation) ---");
            for (FatShape shape : shapes) {
                try {
                    System.out.println("Volume: " + shape.calculateVolume());
                } catch (UnsupportedOperationException e) {
                    System.out.println("Caught Expected Crash: " + e.getMessage());
                }
            }
        }
    }

    // ISP Solution: Segregating into Shape2D and Shape3D

    interface Shape2D {
        double calculateArea();
    }

    interface Shape3D {
        double calculateArea();
        double calculateVolume();
    }

    static class Rectangle implements Shape2D {
        private double width, height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double calculateArea() { return width * height; }
    }

    static class Square implements Shape2D {
        private double side;

        public Square(double side) {
            this.side = side;
        }

        public double calculateArea() { return side * side; }
    }

    static class Cube implements Shape3D {
        private double side;

        public Cube(double side) {
            this.side = side;
        }

        public double calculateArea() { return 6 * side * side; }
        public double calculateVolume() { return side * side * side; }
    }

    static class GoodClient {
        public static void printAreas(List<Shape2D> shapes2D) {
            System.out.println("\n--- Processing 2D Shape Areas ---");
            for (Shape2D shape : shapes2D) {
                System.out.println("2D Area: " + shape.calculateArea());
            }
        }

        public static void print3DMetrics(List<Shape3D> shapes3D) {
            System.out.println("\n--- Processing 3D Shape Volumes ---");
            for (Shape3D shape : shapes3D) {
                System.out.println("3D Surface Area: " + shape.calculateArea());
                System.out.println("3D Volume: " + shape.calculateVolume());
            }
        }
    }

    public static void main(String[] args) {
        
        // 1. Fat interface demo
        List<FatShape> fatShapes = new ArrayList<>();
        fatShapes.add(new FatCube(3));
        fatShapes.add(new FatRectangle(4, 5));
        FatClient.printVolumes(fatShapes);

        // 2. Segregated interfaces demo
        List<Shape2D> flatShapes = new ArrayList<>();
        flatShapes.add(new Rectangle(4, 5));
        flatShapes.add(new Square(4));
        GoodClient.printAreas(flatShapes);

        List<Shape3D> solidShapes = new ArrayList<>();
        solidShapes.add(new Cube(3));
        GoodClient.print3DMetrics(solidShapes);
        
        // solidShapes.add(new Rectangle(4,5)); // Compile error as expected
    }
}
