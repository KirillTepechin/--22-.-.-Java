import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class Hangar<T extends Transport,G extends GunsInterface > implements Iterable<Vehicle> {
    private final List<T> places;

    private final int maxCount;

    private final int pictureWidth;

    private final int pictureHeight;

    private final int placeSizeWidth = 210;

    private final int placeSizeHeight = 80;

    public Hangar (int picWidth, int picHeight)
    {
        int width = picWidth / placeSizeWidth;
        int height = picHeight / placeSizeHeight;
        maxCount = width * height;
        places = new LinkedList<>();
        pictureWidth = picWidth;
        pictureHeight = picHeight;
    }

    public int add(Hangar<T,G> h,T armoredVehicle) throws HangarOverflowException, HangarAlreadyHaveException {
        if (h.maxCount == h.places.size())
        {
            throw new HangarOverflowException();
        }
        if (h.places.contains(armoredVehicle))
        {
            throw new HangarAlreadyHaveException();
        }
        else
        {
            h.places.add(armoredVehicle);
            return h.places.size() - 1;
        }
    }

    public T delete (Hangar<T,G> h, int index) throws HangarNotFoundException {
        if (index>=h.places.size() || index<0)
        {
            throw new HangarNotFoundException(index);
        }
        else
        {
            T tmp = h.places.get(index);
            h.places.remove(h.places.get(index));
            return tmp;
        }
    }
    private boolean greaterThan(Hangar<T,G> hangar,ArmoredVehicle armoredVehicle){
        float maxWeight=0;
        for(T tr : hangar.places){
            ArmoredVehicle aV = (ArmoredVehicle)tr;
            if(aV.weight > maxWeight){
                maxWeight = aV.weight;
            }
        }
        return maxWeight > armoredVehicle.weight;
    }
    private boolean lessThan(Hangar<T,G> hangar,ArmoredVehicle armoredVehicle){
        float maxWeight=0;
        for(T tr : hangar.places){
            ArmoredVehicle aV=(ArmoredVehicle)tr;
            if(aV.weight>maxWeight){
                maxWeight=aV.weight;
            }
        }
        return maxWeight < armoredVehicle.weight;
    }
    public void draw(Graphics g)
    {
        drawMarking(g);
        for (int i = 0; i < places.size(); i++)
        {
            if(places.get(i)!=null){
                places.get(i).setPosition(20 + i % 3 * placeSizeWidth + 5, i / 3 * (placeSizeHeight + 9) + 12, pictureWidth, pictureHeight);
                places.get(i).drawTransport(g);
            }
        }
    }
    private void drawMarking(Graphics g){
        int shift = 9;
        for (int i = 0; i < pictureWidth / placeSizeWidth; i++)
        {
            for (int j = 0; j < pictureHeight / placeSizeHeight + 1; ++j)
            {//линия разметки места
                g.drawLine(i * placeSizeWidth, j * (placeSizeHeight + shift),
                        i * placeSizeWidth + placeSizeWidth / 2 + 50, j * (placeSizeHeight + shift));
            }
            g.drawLine(i * placeSizeWidth, 0, i * placeSizeWidth,
                    (pictureHeight / placeSizeHeight) * (placeSizeHeight + shift));

        }
    }
    public T getValue(int ind){
        if(ind > -1 && ind < places.size())
        {
            return places.get(ind);
        }
        return null;
    }
    public void clearHangar(){
        places.clear();
    }


    public void sort() {
        places.sort((Comparator<? super T>) new ArmoredVehicleComparer());
    }


    @Override
    public Iterator<Vehicle> iterator() {
        Iterator<Vehicle> it = new Iterator<Vehicle>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < places.size() && places.get(currentIndex) != null;
            }

            @Override
            public Vehicle next() {
                return (Vehicle) places.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
