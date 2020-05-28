package src.exceptions;

public class BadValue extends Exception{
    public BadValue(){
        super("Bad Value Exception: 'the variable is not in correct range!'");
    }
    public BadValue(String message){
        super(message);
    }
}
