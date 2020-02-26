package edu.wpi.cs3733.c20.teamS.utilities;


import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

//FORMAT
// * Gets the current weather for Worcester, MA
//         *         0: summary: "Clear"
//         *         1: precipProbability: 0
//         *         2: visibility: 9.92
//         *         3: windGust: 7.72
//         *         4: precipIntensity: 0
//         *         5: icon: "clear-day"
//         *         6: cloudCover: 0.07
//         *         7: windBearing: 301
//         *         8: apparentTemperature: 57.39
//         *         9: pressure: 1020.82
//         *         10: dewPoint: 38.85
//         *         11: ozone: 347.94
//         *         12: nearestStormBearing: 210
//         *         13: nearestStormDistance: 504
//         *         14: temperature: 57.39
//         *         15: humidity: 0.5
//         *         16: time: 06-04-2019 18:42:56
//         *         17: windSpeed: 6.38
//         *         18: uvIndex: 0
//         */
public class WeatherBox {
    ForecastIO fio;
    FIOCurrently current;

    public WeatherBox(){
        fio = new ForecastIO("ea29114ff995d77b7be7e05cd23cee5b");
        fio.setUnits(ForecastIO.UNITS_US);
        fio.setLang(ForecastIO.LANG_ENGLISH);
        fio.getForecast("42.3601", "-71.0589");
        current = new FIOCurrently(fio);
    }

    public double getTemp(){
        return current.get().temperature();
    }

    public double getFeelTemp(){
        return current.get().apparentTemperature();
    }

    public String summary(){
        return current.get().summary();
    }


    public String icon(){
        return current.get().icon();
    }


}
