
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Location implements Cloneable {


    private String roomTitle;

    private String shortDescription;
    private String longDescription;
    private boolean Entered;
    private ArrayList<Exit> Exits = new ArrayList<Exit>(0);

    private List<gameObject> Objects = new ArrayList<gameObject>(0);

    public Location(String roomName, String fileName) {
        String line = "";
        Entered = false;

        try {
            FileReader roomReader =
                    new FileReader(fileName);

            Scanner scanner = new Scanner(roomReader);


            while (scanner.hasNextLine()) {

                if(scanner.nextLine().equals(roomName)) {

                    this.setRoomTitle(roomName);

                    line = scanner.nextLine();
                    this.setShortDescription(line);

                    line = scanner.nextLine();
                    this.setLongDescription(line);

                    break;
                }
                else {
                    line = scanner.nextLine();
                    continue;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public void setEntered(boolean verdict) {
        this.Entered = verdict;
    }

    public void addObject(String objectName, ArrayList<gameObject> gameObjects) {
        String objectUpperFirst = objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
        List<gameObject> objectsToAdd = new ArrayList<gameObject>(0) ;

        for(gameObject obj: gameObjects) {
            if(objectUpperFirst.equals(obj.getObjectName())) {
                objectsToAdd.add(obj);
            }
        }

        this.Objects.addAll(objectsToAdd);
    }

    public void addObject(gameObject obj) {
        this.Objects.add(obj);
    }

    public void removeObject(String objectName) {
        String objectUpperFirst = objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
        List<gameObject> objectsToRemove = new ArrayList<gameObject>(0) ;

        for(gameObject obj: this.getObjects()) {
            if(objectUpperFirst.equals(obj.getObjectName())) {
                objectsToRemove.add(obj);
            }
        }

        this.Objects.removeAll(objectsToRemove);

    }

    public Location(CloneMe newLocation) {
        this.Exits = newLocation.getExits();
        this.roomTitle = newLocation.getRoomTitle();
        this.longDescription = newLocation.getLongDescription();
        this.shortDescription = newLocation.getShortDescription();
        this.Entered = newLocation.isEntered();
        this.Objects = newLocation.getObjects();

    }


    public void getExits(String fileName, ArrayList<Location> locations) {

        String line1 = " ";
        String line2 = " ";
        try {
            FileReader roomReader =
                    new FileReader(fileName);

            Scanner scanner = new Scanner(roomReader);

            while (scanner.hasNextLine()) {

                line1 = scanner.nextLine();
                if(line1.contains("*"))
                    line1 = scanner.nextLine();
                if(line1.equals(this.getRoomTitle())) {
                    line1 = scanner.nextLine();
                    while (!line1.contains("*")) {
                        String[] splitted = line1.split(" ");
                        String locationTo = splitted[0];
                        Exit newExit = new Exit(splitted[1], locationTo, locations);
                        this.addExit(newExit);
                        line1 = scanner.nextLine();
                        if(line1.contains("*")) {
                            scanner.close();
                            return;
                        }

                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    }
