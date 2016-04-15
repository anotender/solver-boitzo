package exceptions;

public class WrongFileFormat extends Exception {
    public WrongFileFormat(){
        super("Wrong file format");
    }
}
