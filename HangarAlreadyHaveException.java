public class HangarAlreadyHaveException extends Exception{
    public HangarAlreadyHaveException(){
        super("В ангаре уже есть такой транспорт");
    }
}
