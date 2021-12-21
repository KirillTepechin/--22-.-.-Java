import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyException;
import java.util.*;

public class HangarCollection {
    final Map<String, Hangar<Vehicle, GunsInterface>> hangarStages;

    public List<String> Keys;

    private final int pictureWidth;

    private final int pictureHeight;

    private char separator = ':';

    public HangarCollection(int pictureWidth, int pictureHeight) {
        hangarStages = new HashMap<>();
        Keys = new LinkedList<>(hangarStages.keySet());
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void addHangar(String name) {
        if (!hangarStages.containsKey(name)) {
            hangarStages.put(name, new Hangar<>(pictureWidth, pictureHeight));
        }
    }

    public void updateKeys() {
        Keys = new LinkedList<>(hangarStages.keySet());
    }

    public void delHangar(String name) {
        hangarStages.remove(name);
    }

    public Hangar<Vehicle, GunsInterface> getValue(String ind) {
        if (hangarStages.containsKey(ind)) {
            return hangarStages.get(ind);
        }
        return null;
    }

    public Transport getParkingValue(String ind, int intInd) {
        Hangar p = hangarStages.get(ind);
        if (p != null) {
            return p.getValue(intInd);
        }
        return null;
    }

    public boolean saveData(String filename) throws IOException {

        Files.deleteIfExists(Paths.get(filename));

        try(FileWriter fileWriter = new FileWriter(filename, false)) {
            fileWriter.write("HangarCollection\n");
            for (Map.Entry<String, Hangar<Vehicle, GunsInterface>> level : hangarStages.entrySet()) {
                fileWriter.write("Hangar" + separator + level.getKey() + '\n');
                for (Transport transport : level.getValue()) {
                    if (transport.getClass().getSimpleName().equals("ArmoredVehicle")) {
                        fileWriter.write("\tArmoredVehicle" + separator);
                    } else if (transport.getClass().getSimpleName().equals("AntiAircraftGun")) {
                        fileWriter.write("\tAntiAircraftGun" + separator);
                    }
                    fileWriter.write(transport.toString() + '\n');
                }
            }
        }
        return true;
    }

    public boolean loadData(String filename) throws FileNotFoundException, HangarOverflowException, HangarAlreadyHaveException {

        if (!new File(filename).exists()) {
            throw new FileNotFoundException();
        }

        FileReader fileReader = new FileReader(filename);
        Scanner sc = new Scanner(fileReader);
        if (sc.nextLine().contains("HangarCollection")) {
            hangarStages.clear();
        } else {
            throw new IllegalArgumentException("Неверный формат файла");
        }

        Vehicle transport = null;
        String key = "";
        String line;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.contains("Hangar")) {
                key = line.split(String.valueOf(separator))[1];
                hangarStages.put(key, new Hangar<Vehicle, GunsInterface>(pictureWidth, pictureHeight));
            } else if (line.contains(String.valueOf(separator))) {
                if (line.contains("\tArmoredVehicle")) {
                    transport = new ArmoredVehicle(line.split(String.valueOf(separator))[1]);
                } else if (line.contains("\tAntiAircraftGun")) {
                    transport = new AntiAircraftGun(line.split(String.valueOf(separator))[1]);
                }
                if (hangarStages.get(key).add(hangarStages.get(key), transport) <= -1) {
                    throw new HangarOverflowException();
                }
            }
        }
        return true;
    }

    public boolean saveHangar(String filename, String key) throws KeyException, IOException {

        Files.deleteIfExists(Paths.get(filename));

        if (!hangarStages.containsKey(key)) {
            throw new KeyException();
        }
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            if (hangarStages.containsKey(key)) fileWriter.write("Hangar" + separator + key + '\n');
            for (Transport transport : hangarStages.get(key)) {
                if (transport.getClass().getSimpleName().equals("ArmoredVehicle")) {
                    fileWriter.write("\tArmoredVehicle" + separator);
                } else if (transport.getClass().getSimpleName().equals("AntiAircraftGun")) {
                    fileWriter.write("\tAntiAircraftGun" + separator);
                }
                fileWriter.write(transport.toString() + '\n');
            }
        }
        return true;
    }

    public boolean loadHangar(String filename) throws FileNotFoundException, HangarOverflowException, HangarAlreadyHaveException {
        if (!new File(filename).exists()) {
            throw new FileNotFoundException("Файл " + filename + " не найден");
        }
        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        String key;
        String line;
        line = scanner.nextLine();
        if (line.contains("Hangar:")) {
            key = line.split(String.valueOf(separator))[1];
            if (hangarStages.containsKey(key)) {
                hangarStages.get(key).clearHangar();
            } else {
                hangarStages.put(key, new Hangar<Vehicle, GunsInterface>(pictureWidth, pictureHeight));
            }
        } else {
            throw new IllegalArgumentException("Неверный формат файла");
        }
        Vehicle transport = null;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.contains(String.valueOf(separator))) {
                if (line.contains("\tArmoredVehicle")) {
                    transport = new ArmoredVehicle(line.split(String.valueOf(separator))[1]);
                } else if (line.contains("\tAntiAircraftGun")) {
                    transport = new AntiAircraftGun(line.split(String.valueOf(separator))[1]);
                }
                if (hangarStages.get(key).add(hangarStages.get(key), transport) <= -1) {
                    throw new HangarOverflowException();
                }
            }
        }
        return true;
    }
}
