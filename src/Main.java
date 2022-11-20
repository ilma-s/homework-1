import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("airbnb.txt");
        Scanner s = new Scanner(file);

        FileWriter fw = new FileWriter("error_lines.txt"); // a new file containing the erroneous files

        //a map doesn't need to be used because we don't need to work with every line in the file and every type of room
        ArrayList<room> LondonRooms = new ArrayList<>(); //an arraylist containing rooms with London in their name
        ArrayList<room> cheapestShared = new ArrayList<>(); // an arraylist containing all shared rooms
        ArrayList<room> mostExpensivePrivate = new ArrayList<>(); //an arraylist containing all private rooms

        s.nextLine();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] elements = line.split("\t");

            if (elements.length < 10) { //write the incomplete lines into a separate file
                fw.write(line);
                fw.write("\n");
            } else {
                if (Double.parseDouble(elements[9]) <= 7) {
                    String name = elements[1].trim();
                    Double lat = Double.parseDouble(elements[5].trim());
                    Double lon = Double.parseDouble(elements[6].trim());
                    String type = elements[7].trim();
                    Integer price = Integer.parseInt(elements[8].trim());

                    room r = new room(name, lat, lon, type, price); //create a new instance of a room class

                    if (type.equals("Private room") && r.distanceFromBigBen()) { //if the object is a private room within a 10km radius from Big Ben, add it to the array
                        mostExpensivePrivate.add(r);
                    }

                    if(type.equals("Shared room")) { //if the object is a shared room, add it to the array
                        cheapestShared.add(r);
                    }

                    if (name.contains("London")) { //add a room containing London in its name in the respective array
                            LondonRooms.add(r);
                    }

                }
            }
        }

        Collections.sort(cheapestShared); //sort the cheapest shared rooms in ascending order
        Collections.sort(mostExpensivePrivate); //sort the most expensive private rooms in ascending order
        Collections.reverse(mostExpensivePrivate); //reverse the order of the most expensive private rooms to get the most expensive one at the top
        Collections.sort(LondonRooms); //sort all the rooms containing London in their name by their price

        FileWriter fw1 = new FileWriter("report.txt");
        fw1.write("The cheapest shared room: " + cheapestShared.get(0) + "\n"); //write the cheapest shared room into the report
        fw1.write("The most expensive private room in 10km radius of Big Ben: " + mostExpensivePrivate.get(0) + "\n"); //write the most expensive private room in the 10km-radius of Big Ben into the report

        //writing the median of the array of rooms containing London in their name into the report
        if(LondonRooms.size() % 2 != 0) { //if the length of the array is odd, the median is the exact middle element
            fw1.write("The median of all rooms that have London in their name: " + LondonRooms.get(LondonRooms.size() / 2) + "\n");
        } else { //the median of an even-length array are the 2 middle elements
            fw1.write("The median of all rooms that have London in their name: " + LondonRooms.get(LondonRooms.size() / 2) + "and \n" + LondonRooms.get(LondonRooms.size() / 2 - 1)+ "\n");
        }
        fw1.close();

    }
}