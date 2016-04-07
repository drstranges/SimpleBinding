
package com.testapp.weather.model;

import java.util.ArrayList;

public class Forecast {

    public City city;
    public String cod;
    public Double message;
    public Integer cnt;
    public java.util.List<com.testapp.weather.model.List> list = new ArrayList<com.testapp.weather.model.List>();

}
