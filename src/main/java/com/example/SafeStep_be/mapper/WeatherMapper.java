package com.example.SafeStep_be.mapper;

import com.example.SafeStep_be.dto.WeatherForecastDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Named("mapHourlyForecast")
    default List<WeatherForecastDto.HourlyForecastDto> mapHourlyForecast(JSONObject hourly) {
        JSONArray times = hourly.getJSONArray("time");
        JSONArray temperatures = hourly.getJSONArray("temperature_2m");
        JSONArray precipitations = hourly.getJSONArray("precipitation");
        JSONArray weatherCodes = hourly.getJSONArray("weathercode");

        List<WeatherForecastDto.HourlyForecastDto> result = new ArrayList<>();

        for (int i = 0; i < times.length(); i++) {
            result.add(new WeatherForecastDto.HourlyForecastDto(
                    times.getString(i),
                    temperatures.getDouble(i),
                    precipitations.getDouble(i),
                    weatherCodes.getInt(i)
            ));
        }

        return result;
    }
}
