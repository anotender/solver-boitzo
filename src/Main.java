import exceptions.WrongFileFormat;
import solver.SimpleSolver;
import solver.Solver;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                Solver solver = new SimpleSolver(args[0]);
                solver.solve();
            } catch (FileNotFoundException | WrongFileFormat e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong number of arguments");
        }
    }
}
