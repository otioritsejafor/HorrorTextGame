import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;


/* Oti Oritsejafor */

public class Game {


    public static void main(String[] args) {
        getLocationInAfrica();
        printIntro();
        ArrayList<Location> locations = new ArrayList<Location>();
        ArrayList<gameObject> gameObjects = getObjectsFromFile("objects.txt");

        Scanner myInput = new Scanner(System.in);
        String file = "rooms.txt";
        Vector<String> Inventory = new Vector<String>();


        Player player = new Player();
        Location Start = new Location("Start", "rooms.txt");
        Location North_1 = new Location("North_1", "rooms.txt");
        Location North_2 = new Location("North_2", "rooms.txt");
        Location East_1 = new Location("East_1", "rooms.txt");
        Location East_2 = new Location("East_2", "rooms.txt");
        Location End = new Location("End", "rooms.txt");
        Location West_1 = new Location("West_1", "rooms.txt");
        Location Death_1 = new Location("Death_1", "rooms.txt");
        Location Death_2 = new Location("Death_2", "rooms.txt");

        locations.addAll(Arrays.asList(North_1, North_2, Start, East_1, East_2, End, West_1, Death_1, Death_2));
        Start.getExits("Exits.txt", locations);
        North_1.getExits("Exits.txt", locations);
        East_1.getExits("Exits.txt", locations);
        East_2.getExits("Exits.txt", locations);
        West_1.getExits("Exits.txt", locations);

        gameObject Godhammer = new gameObject("Godhammer", "This Godhammer has inscriptions in Hausa that translate into \n 'The flames of Zzaboolaza'");
        gameObject Torch = new gameObject("Torch", "A torch with a broken edge" );
        Location current = Start;
        East_2.addObject(Godhammer);
        Start.addObject(Torch);

        boolean alive = true;
        displayCurrentLocation(current);
        while(alive) {

            String line = myInput.nextLine();
            Location newCurrent = locationHandler(line, current, player);
            alive = deathHandler(newCurrent, player);
            actionHandler(line, newCurrent, player, gameObjects, locations);
            current = newCurrent;
            printLine();
        }


    }

    public static void displayCurrentLocation(Location current) {
        if(current.isEntered()) {
            System.out.println(current.getShortDescription());
        } else {
            System.out.println(current.getLongDescription());
        }
    }


    /* This will handle all the Deaths for the locations */
    public static boolean deathHandler(Location current, Player player) {
        if(current.getRoomTitle().equals("North_2")) {
            System.out.println("'I finally caught up to you', says a hazy voice. You raise your torch to identify the guard, \n'Your soul is just what I needed for the sacrifice' said the guard, who had no face.\n");
            gameOver();
            //return false;

        }

        else if(current.getRoomTitle().equals("Death_1")) {
            System.out.println("You have foolishly jumped off the cliff into the abyss, your bones are broken. \nIn the unfathomable darkness, you feel an insidious presence approach you.....\n");
            gameOver();
        }

        else if(current.getRoomTitle().equals("Death_2")) {
            System.out.println("You see a humanlike figure that appears to be transmundane. \nWith his massive souless eyes and ferocious teeth, the fabled Odalibeje swiftly seizes you.\n");
            gameOver();
        }

        else if(!(player.getInventory().contains("Torch")) && !(current.getRoomTitle().equals("Start"))) {
            System.out.println("You have wondered into the darkness without a torch, only to be consumed by creatures of the night.\n");
            gameOver();

        }

        return true;
    }

    public static Location locationHandler(String input, Location current, Player player) {
        String[] splitted = input.split(" ");
        String command = splitted[0].toLowerCase();
        String object = "";
        if(splitted.length > 1) object = splitted[1].toLowerCase();


        if(command.toLowerCase().equals("go")) {
            for(Exit a: current.getExits()) {
                CloneMe c = new CloneMe(current);
                if(object.equals(a.exitDirection())) {
                    c.setNewLocation(a.locationToGo());
                    Location currentClone = new Location(c);
                    deathHandler(currentClone, player);
                    displayCurrentLocation(currentClone);
                    current.setEntered(true);
                    a.setEntered(true);
                    return currentClone;

                }
            }
            System.out.println("The Ancestors forbid it!");
        }


        return current;
    }

    public static void actionHandler(String input, Location current, Player player, ArrayList<gameObject> gameObjects, ArrayList<Location> locations) {
        ArrayList<String> Actions = new ArrayList<String>();
        Actions.addAll(Arrays.asList("pick", "drop", "get", "open", "inventory", "look", "go"));
        String[] splitted = input.split(" ");
        String command = splitted[0].toLowerCase();
        String object = "";
        if(splitted.length > 1) object = splitted[1].toLowerCase();

        if(Actions.contains(command)) {
            if (command.toLowerCase().equals("pick") || command.toLowerCase().equals("get")) {
                if(splitted.length == 1) {
                    System.out.println("Get what?");
                    return;
                }
                String objectUpperFirst = object.substring(0, 1).toUpperCase() + object.substring(1);
                for(gameObject obj: current.getObjects()) {
                    if(obj.getObjectName().equals(objectUpperFirst)) {
                        System.out.println("Taken");
                        player.addToInventory(objectUpperFirst);
                        current.removeObject(objectUpperFirst);
                        return;
                    }
                    else {
                        System.out.println("That's not here!");
                        return;
                    }
                }
            }

             else if(command.toLowerCase().equals("drop")) {
                String objectUpperFirst = object.substring(0, 1).toUpperCase() + object.substring(1);
                if(player.getInventory().contains(objectUpperFirst)) {
                    System.out.println("Dropped");
                    player.removeFromInventory(objectUpperFirst);
                    current.addObject(objectUpperFirst, gameObjects);
                    return;
                }
                else {
                    System.out.println("You do not have " + object + " in your inventory");
                    return;
                }
            }

            else if(command.toLowerCase().equals("look") ) {
                if(splitted.length==1) {
                    System.out.println(current.getLongDescription());
                    for(gameObject obj: current.getObjects()) {
                        System.out.println("There is a " + obj.getObjectName() + " here");
                    }
                }
                else if(splitted.length == 2) {
                    for(gameObject obj: current.getObjects()) {
                        if(obj.getObjectName().toLowerCase().equals(object)) {
                            System.out.println(obj.getDescription());
                            return ;
                        }
                    }
                }
                else {
                    System.out.println("There's no such thing around");
                }
            }
            else if(command.toLowerCase().contains("open") ) {
                if(current.getRoomTitle().equals("East_2")) {
                    if(player.getInventory().contains("Godhammer")) {
                        Location End = getLocationFromString("End", locations);
                        displayCurrentLocation(End);
                        printEnding();
                        System.exit(0);
                    } else {
                        System.out.println("You have nothing you can use to open this gate.");
                        return;
                    }
                }

            }

            else if(command.toLowerCase().contains("inventory")) {
                System.out.println("You have a : ");
                for(String obj: player.getInventory()) {
                    System.out.println("A "+ obj);
                }
            }
        }

        else {
            System.out.println("The ancestors have decided they do not understand that. Try something else");
            return ;
        }
    return ;
    }


    public static Location getLocationFromString(String locationName, ArrayList<Location> locations ) {
        for(Location loc: locations) {
            if(locationName.equals(loc.getRoomTitle())) {
                return loc;
            }
        }

        System.out.println("Location not found");
        return null;
    }

    public static void printIntro() {
        System.out.println("The Sinister Secrets Of Wazobia, chapter one: Prologue");
        System.out.println("-----------------------------------------------------------");

        System.out.println("You've successfully climbed out of the window of your hut" +
                " without amma seeing you. \nIt's late at night, the time amma has warned you is ruled by evil entities of all kinds.\n" +
                "There have been unnatural and paranormal activities going on \nin the village lately. Something's not right in the village of Wazobia! Time to find out. " +
                " There is a road ahead to the north and a lit torch in front of you. ");

    }

    public static ArrayList<gameObject> getObjectsFromFile(String fileName) {
        ArrayList<gameObject> result = new ArrayList<gameObject>(0);
        String line1 = "";
        String line2 = "";

        try {
            FileReader roomReader =
                    new FileReader(fileName);

            Scanner scanner = new Scanner(roomReader);

            while (scanner.hasNextLine()) {
                line1 = scanner.nextLine();
                if(line1.contains("*")) {
                    line1 = scanner.nextLine();
                }
                if(line1.contains("end")) break;
                line2 = scanner.nextLine();

                gameObject newObj = new gameObject(line1, line2);
                result.add(newObj);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void printEnding() {

        System.out.println("Akeredolu peers straight into your soul, 'Why have you come here, o nephew?\nHave you come to foil my plans? You will join them in their death, as did your father 13 years ago!' As he raises his staff " +
                "to strike you, the flames of the ancestors, \nthe 'Zzaboolaza' fills your soul! You are granted an awesome power, making you soar into the air.\nYou look down with anger and vigor " +
                "at the evil Akeredolu, 'I will do what I must, Uncle.' \n TO BE CONTINUED IN CHAPTER 2");


    }

    public static void music() {
        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;
        ContinuousAudioDataStream loop = null;
        try {
            BGM = new AudioStream(new FileInputStream("GetOut.mp3"));
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);
        } catch(IOException error) {}

        MGP.start(loop);
    }

    public static void printLine() {
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
    }

    public static void getLocationInAfrica() {
        System.out.println("MAP OF AFRICA\n");
        System.out.println("    ___________\n" +
                "           / |       | |\n" +
                "        ,' ,'         \\/',_    __\n" +
                "     ,'__/             |    ',|  \"'-,,,,,,,\n" +
                "   ,/  _|',            |                |   \\\n" +
                "   |  |   |',           \\               |    \\\n" +
                "   |__|   |  ',          ',             |     \\\n" +
                "  /       |     ',        ,_\"\"\"\"\"---'-_,'______\\\n" +
                " /        |        ',,_-'\"    |        |        ',\n" +
                "|_________|         |         /        |        / ',,'\"\"\"\"|\n" +
                "|__  |        ,____/         |        _|       /    |___  /\n" +
                "'\\___|      ,'_,'|_,-,_______|         |       /      , '/\n" +
                "  \\,' _', _/  ,, ,',|        |          \\       |   '\" ,'<---- Wazobia is somewhere here.\n" +
                "   \\ / |_ ,  |  \\||||       ,' |      ,'|    _\"\"    |,'\n" +
                "    ' ,'  ', |  ||||| __ ,'   _|_ ,'    |    |\"\"---/\n" +
                "       ' ,\"\"\"','\"\"\"\"\"\" |     /           \\\"\"\"|    /\n" +
                "                      |_____|_      __''\"    \\   |\n" +
                "                     |  |  /  \"\"\"\"\"\"   |      \\ /\n" +
                "                      \\ / |            |       /\n" +
                "                       \\--'            |      /\n" +
                "                       |   \\__        _|__    |\n" +
                "                       |      |__     |   ',,,|\n" +
                "                       |         |____|   /   |\n" +
                "                       /         _|    ,,'_   |\n" +
                "                      |__________|___,'  ,,' /\n" +
                "                       \\      ---'    \\,/  ,'\n" +
                "                        \\     |    ,,,' \\_/\n" +
                "                         |    |_,''      |/\n" +
                "                         |    |       []_|\n" +
                "                          \\___'        /\n" +
                "                           \\       __,'\n" +
                "                            \\_____/        \n" +
                "\n" +
                "\n");
    }

    public static void gameOver() {
        System.out.println(" ▄▀▀▀▀▄    ▄▀▀█▄   ▄▀▀▄ ▄▀▄  ▄▀▀█▄▄▄▄      ▄▀▀▀▀▄   ▄▀▀▄ ▄▀▀▄  ▄▀▀█▄▄▄▄  ▄▀▀▄▀▀▀▄ \n" +
                "█         ▐ ▄▀ ▀▄ █  █ ▀  █ ▐  ▄▀   ▐     █      █ █   █    █ ▐  ▄▀   ▐ █   █   █ \n" +
                "█    ▀▄▄    █▄▄▄█ ▐  █    █   █▄▄▄▄▄      █      █ ▐  █    █    █▄▄▄▄▄  ▐  █▀▀█▀  \n" +
                "█     █ █  ▄▀   █   █    █    █    ▌      ▀▄    ▄▀    █   ▄▀    █    ▌   ▄▀    █  \n" +
                "▐▀▄▄▄▄▀ ▐ █   ▄▀  ▄▀   ▄▀    ▄▀▄▄▄▄         ▀▀▀▀       ▀▄▀     ▄▀▄▄▄▄   █     █   \n" +
                "▐         ▐   ▐   █    █     █    ▐                            █    ▐   ▐     ▐   \n" +
                "                  ▐    ▐     ▐                                 ▐                ");
        System.exit(0);
    }

}