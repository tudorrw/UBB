package map.project.stockApp.utils.memento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private static Caretaker instance;
    private List<StockMemento> mementoList = new ArrayList<>();

    private Caretaker() {
    }

    public static Caretaker getInstance() {
        if (instance == null) {
            synchronized (Caretaker.class) {
                if (instance == null) {
                    instance = new Caretaker();
                }
            }
        }
        return instance;
    }

    public void addMemento(StockMemento memento) {
        int position = this.getMementoByIdPosition(memento.getId());
        if (position != -1) {
            mementoList.add(position, memento);
        } else {
            mementoList.add(memento);
        }
    }

    public StockMemento getMemento(int index) {
        return mementoList.get(index);
    }

    public StockMemento getMementoById(int id) {
        for (StockMemento memento : mementoList) {

            if (memento.getId() == id) {
                return memento;
            }
        }

        return null;
    }

    public int getMementoByIdPosition(int id) {
        for (int index = 0; index < mementoList.size(); index++) {
            if (this.mementoList.get(index).getId() == id) {
                return index;
            }
        }

        return -1;
    }

    public void removeMementoById(int id) {
        int position = this.getMementoByIdPosition(id);
        this.mementoList.remove(position);
    }

    public List<StockMemento> getMementos() {
        return this.mementoList;
    }
}