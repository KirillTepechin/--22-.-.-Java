import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HangarCollection {
    final Map<String, Hangar<Vehicle,GunsInterface>> hangarStages;

    public List<String> Keys;

    private final int pictureWidth;

    private final int pictureHeight;

    private char separator = ':';
    public HangarCollection(int pictureWidth, int pictureHeight)
    {
        hangarStages = new HashMap<>();
        Keys=new LinkedList<>(hangarStages.keySet());
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public void addHangar(String name)
    {
        if (!hangarStages.containsKey(name))
        {
            hangarStages.put(name, new Hangar<>(pictureWidth, pictureHeight));
        }
    }
    public void updateKeys(){
        Keys = new LinkedList<>(hangarStages.keySet());
    }
    public void DelParking(String name)
    {
        hangarStages.remove(name);
    }

    public Hangar<Vehicle, GunsInterface> getValue(String ind){
        if(hangarStages.containsKey(ind)){
            return hangarStages.get(ind);
        }
        return null;
    }

    public Transport getParkingValue(String ind, int intInd){
        Hangar p = hangarStages.get(ind);
        if(p != null){
            return p.getValue(intInd);
        }
        return null;
    }
    public boolean saveData(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            fileWriter.write("HangarCollection\n");
            for (Map.Entry<String, Hangar<Vehicle, GunsInterface>> level : hangarStages.entrySet()) {
                fileWriter.write("Hangar" + separator + level.getKey() + '\n');
                Transport transport;
                for (int i = 0; (transport = level.getValue().getValue(i)) != null; i++) {
                    if (transport.getClass().getSimpleName().equals("ArmoredVehicle")) {
                        fileWriter.write("\tArmoredVehicle" + separator);
                    } else if (transport.getClass().getSimpleName().equals("AntiAircraftGun")) {
                        fileWriter.write("\tAntiAircraftGun" + separator);
                    }
                    fileWriter.write(transport.toString() + '\n');
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean loadData(String filename) {

        if (!new File(filename).exists()) {
            return false;
        }

        try (FileReader fileReader = new FileReader(filename)) {
            Scanner sc = new Scanner(fileReader);
            if (sc.nextLine().contains("HangarCollection")) {
                hangarStages.clear();
            } else {
                return false;
            }

            Vehicle transport = null;
            String key = "";
            String line;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.contains("Hangar")) {
                    key = line.split(String.valueOf(separator))[1];
                    hangarStages.put(key, new Hangar<Vehicle, GunsInterface>(pictureWidth, pictureHeight));
                }
                else if (line.contains(String.valueOf(separator))) {
                    if (line.contains("\tArmoredVehicle")) {
                        transport = new ArmoredVehicle(line.split(String.valueOf(separator))[1]);
                    } else if (line.contains("\tAntiAircraftGun")) {
                        transport = new AntiAircraftGun(line.split(String.valueOf(separator))[1]);
                    }
                    if (hangarStages.get(key).add(hangarStages.get(key), transport) <= -1) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean saveHangar(String filename, String key) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!hangarStages.containsKey(key)) {
            return false;
        }
        try (FileWriter fileWriter = new FileWriter(filename, false)) {
            if (hangarStages.containsKey(key))
                fileWriter.write("Hangar" + separator + key + '\n');
            Transport transport;
            for (int i = 0; (transport = hangarStages.get(key).getValue(i)) != null; i++) {
                if (transport.getClass().getSimpleName().equals("ArmoredVehicle")) {
                    fileWriter.write("\tArmoredVehicle" + separator);
                } else if (transport.getClass().getSimpleName().equals("AntiAircraftGun")) {
                    fileWriter.write("\tAntiAircraftGun" + separator);
                }
                fileWriter.write(transport.toString() + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean loadHangar(String filename) {
        if (!new File(filename).exists()) {
            return false;
        }
        try (FileReader fileReader = new FileReader(filename)) {
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
                return false;
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
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
