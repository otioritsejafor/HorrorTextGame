import java.util.ArrayList;

public class Player {

    public ArrayList<String> getInventory() {
        return Inventory;
    }

    private ArrayList<String> Inventory = new ArrayList<String>();

    public Player() {
        this.Inventory.add("Spoon");
    }

    public void addToInventory(String item) {
        this.Inventory.add(item);
    }

    public void removeFromInventory(String item) {
        this.Inventory.remove(item);
    }
}
