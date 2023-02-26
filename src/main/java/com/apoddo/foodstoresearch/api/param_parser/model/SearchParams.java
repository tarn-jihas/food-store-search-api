package com.apoddo.foodstoresearch.api.param_parser.model;

public class SearchParams {

    private int distance;
    private int batchSize;
    private double latitude;
    private double longitude;
    private String partialQuery;

    SearchParams() {}

    public int getDistance() {
        return distance;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPartialQuery() {
        return partialQuery;
    }

    public static class Builder {

        private int distance;
        private int batchSize;
        private double latitude;
        private double longitude;
        private String partialQuery;

        public Builder() {}

        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder batchSize(int batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder partialQuery(String partialQuery) {
            this.partialQuery = partialQuery;
            return this;
        }

        public SearchParams build() {
            SearchParams searchParams = new SearchParams();
            searchParams.distance = distance;
            searchParams.batchSize = batchSize;
            searchParams.latitude = latitude;
            searchParams.longitude = longitude;
            searchParams.partialQuery = partialQuery;
            return searchParams;
        }
    }
}
