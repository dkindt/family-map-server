package server.database.model;

import java.util.Arrays;

public class Locations {

    Location[] data;

    public Locations(Location[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    private static class Location {

        private float latitude;
        private float longitude;
        private String city;
        private String country;

        public Location() {

        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return String.format(
                "\nLocation(\n\tlatitude='%s', \n\tlongitude='%s', \n\tcity='%s', \n\tcountry='%s'\n)",
                latitude, longitude, city, country
            );
        }
    }
}
