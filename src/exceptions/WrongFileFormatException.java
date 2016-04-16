package exceptions;

public class WrongFileFormatException extends Exception {
    public WrongFileFormatException(){
        super("Wrong file format");
    }
}
