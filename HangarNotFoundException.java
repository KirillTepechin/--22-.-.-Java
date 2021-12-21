public class HangarNotFoundException extends Exception {
    public HangarNotFoundException(int i) {
        super("Не найден бронетранспорт по месту " + i);
    }
}
