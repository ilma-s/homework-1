import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("airbnb.txt");
        Scanner s = new Scanner(file);

        FileWriter fw = new FileWriter("error_lines.txt");
        fw.write("name, latitude, longitude, type, price)\n");

        ArrayList<room> rooms = new ArrayList<>(); //array list to store all the rooms
        Map<BigDecimal, ArrayList<room>> cheapestSharedRoom = new HashMap<>();
        Map<BigDecimal, ArrayList<room>> mostExpensivePrivateRoom = new HashMap<>();
        Map<BigDecimal, ArrayList<room>> roomsWithLondonInName = new HashMap<>();

        s.nextLine(); // preskace header

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] elements = line.split("\t");
            System.out.println(line); //zasto je ovdje uvijek 10

            if (elements.length < 10) {
                fw.write(line);
                s.nextLine(); //skip the element which doesn't contain all the parameters and store it in a separate file
                fw.write("\n");
            } else {
                //System.out.println(elements[9]);
                //here, some elements such as the element with the id of 9975003 gets skipped for some reason
                if (Double.parseDouble(elements[9]) >= 7) {
                    BigDecimal id = new BigDecimal(elements[0].trim());
                    String name = elements[1].trim();
                    Double lat = Double.parseDouble(elements[5].trim());
                    Double lon = Double.parseDouble(elements[6].trim());
                    String type = elements[7].trim();
                    Integer price = Integer.parseInt(elements[8].trim());

                    rooms.add(new room(id, name, lat, lon, type, price)); //add the room to the array list of all rooms

                    if (type.equals("Shared room")) {
                        if (!cheapestSharedRoom.containsKey(id)) {
                            cheapestSharedRoom.put(id, new ArrayList<>()); //add an element to the map: new blank key-value pair
                        }

                        room r = new room(id, name, lat, lon, type, price);
                        cheapestSharedRoom.get(id).add(r); //dodaje u array listu
                    }

                    //System.out.println(id);

                    if (type.equals("Private room") && room.distanceFromBigBen(lat, lon)) {
                        if (!mostExpensivePrivateRoom.containsKey(id)) {
                            mostExpensivePrivateRoom.put(id, new ArrayList<>());
                        }

                        room r = new room(id, name, lat, lon, type, price);
                        mostExpensivePrivateRoom.get(id).add(r);
                    }

                    if (room.hasLondonInName(name)) {
                        if (!roomsWithLondonInName.containsKey(id)) {
                            roomsWithLondonInName.put(id, new ArrayList<>());
                        }

                        room r = new room(id, name, lat, lon, type, price);
                        roomsWithLondonInName.get(id).add(r);
                    }
                } else {
                    s.nextLine(); //skip the line with less than 7 nights
                }
            }
        }
        FileWriter filew = new FileWriter("report.txt");


        for (Map.Entry<BigDecimal, ArrayList<room>> entry : cheapestSharedRoom.entrySet()) {
            Collections.sort(entry.getValue());
        }

//        for (Map.Entry<BigDecimal, ArrayList<room>> entry : cheapestSharedRoom.entrySet()) {
//            System.out.println(entry); //doesn't work - nothing is sorted by price
//        }

        for (Map.Entry<BigDecimal, ArrayList<room>> entry : mostExpensivePrivateRoom.entrySet()) {
            Collections.reverse(entry.getValue());
        }

//        for (Map.Entry<BigDecimal, ArrayList<room>> entry : mostExpensivePrivateRoom.entrySet()) {
//            System.out.println(entry); //doesn't work - nothing is sorted by price
//        }

//        filew.write("The cheapest shared room is: " + cheapestSharedRoom.entrySet().iterator().next() + "\n");
//        filew.write("The most expensive private room is: " + mostExpensivePrivateRoom.entrySet().iterator().next() + "\n");
//
//        for (Map.Entry<BigDecimal, ArrayList<room>> entry : roomsWithLondonInName.entrySet()) {
//            Collections.sort(entry.getValue());
//        }
//        for (Map.Entry<BigDecimal, ArrayList<room>> entry : roomsWithLondonInName.entrySet()) {
//            System.out.println(entry); //doesn't work - nothing is sorted by price
//        }

        //sorting by price doesnt work
    }
}