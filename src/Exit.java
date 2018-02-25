import java.util.ArrayList;

public class Exit implements Cloneable {

    private String direction;
    private Location locationTo;

    public void setEntered(boolean bool) {
        this.locationTo.setEntered(bool);
    }
    public Exit(String direction, String locationTo, ArrayList<Location> locations) {
        this.direction = direction;
        for(Location loc: locations) {
            if(loc.getRoomTitle().equals(locationTo)) {
                this.locationTo = loc;
            }
        }
    }

    public Location locationToGo() {
        return this.locationTo;
    }

    public String toString() {
        return locationTo.getRoomTitle();
    }

    public String exitDirection() {
        return this.direction.toLowerCase();

    }

}
