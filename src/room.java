import java.math.BigDecimal;
import java.math.BigInteger;

public class room implements Comparable<room> {
    private final String name;
    private final Double lat;
    private final Double lon;
    private final Integer price;
    private final String type;
    private final BigDecimal id;

    public room(BigDecimal id, String name, Double lat, Double lon, String type, Integer price) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "room{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int compareTo(room r) {
        return this.price.compareTo(r.price);
    }

    public static boolean hasLondonInName(String key) {
        if (key.contains("London")) return true;
        return false;
    }

    public static boolean distanceFromBigBen(Double lat, Double lon) {
        return Math.sqrt((lat-51.51)*(lat-51.51) + (lon+0.12)*(lon+0.12)) <= 10000;
    }
}
