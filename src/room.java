public class room implements Comparable<room> {
    private final String name;
    private final Double lat;
    private final Double lon;
    private final Integer price;
    private final String type;

    public room(String name, Double lat, Double lon, String type, Integer price) {
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
                '}';
    }

    @Override
    public int compareTo(room r) {
        return this.price.compareTo(r.price);
    }


    public boolean distanceFromBigBen() {
        return Math.sqrt((this.lat-51.51)*(this.lat-51.51) + (this.lon+0.12)*(this.lon+0.12)) <= 10;
    }
}
