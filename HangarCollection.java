import java.util.*;

public class HangarCollection {
    final Map<String, Hangar<Vehicle,GunsInterface>> hangarStages;

    public List<String> Keys;

    private final int pictureWidth;

    private final int pictureHeight;

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

}
