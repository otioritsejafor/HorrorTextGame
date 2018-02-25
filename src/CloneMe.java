import java.util.ArrayList;
import java.util.List;

public class CloneMe implements Cloneable
{
    // Constructor
    public CloneMe(Location original)
    {
        roomTitle = original.getRoomTitle();
        shortDescription = original.getShortDescription();
        longDescription = original.getLongDescription();
        Entered = original.isEntered();
        Exits = original.getExits();
        Objects = original.getObjects();
    }


    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public boolean isEntered() {
        return Entered;
    }

    public ArrayList<Exit> getExits() {
        return this.Exits;
    }

    public void addExit(Exit newExit) {
        this.Exits.add(newExit);
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }


    public List<gameObject> getObjects() {
        return Objects;
    }


    public void setNewLocation(Location newLocation) {
        this.Exits = newLocation.getExits();
        this.roomTitle = newLocation.getRoomTitle();
        this.longDescription = newLocation.getLongDescription();
        this.shortDescription = newLocation.getShortDescription();
        this.Entered = newLocation.isEntered();
        this.Objects = newLocation.getObjects();
    }
    ///// Private data /////
    private String roomTitle;

    private String shortDescription;
    private String longDescription;
    private boolean Entered;
    private ArrayList<Exit> Exits = new ArrayList<Exit>(0);

    private List<gameObject> Objects = new ArrayList<gameObject>(0);
}


