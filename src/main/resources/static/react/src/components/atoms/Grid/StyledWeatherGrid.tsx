import React, {ReactElement} from "react";
import styled from "styled-components";
import {WeatherType} from "../../../types/weather.type";

const StyledGridView = styled.div`
	display: grid;
    grid-template-columns: repeat(2,1fr);
    gap: 1em;
    padding: 1em;
    justify-content: center;
`;

const StyledGridData = styled.span`
    color: #f9e16c;
	font-family: 'Itim', cursive;
`;

export const StyledWeatherGrid: React.FC<WeatherType> = (weatherData: WeatherType): ReactElement => {
	return (
		<StyledGridView>
			<span> Weather Icons: [<StyledGridData>{weatherData.weather_icons.join(", ")}</StyledGridData>] </span>
			<span> Weather Descriptions: [<StyledGridData>{weatherData.weather_descriptions.join(", ")}</StyledGridData>] </span>

			<span> Is currently Daytime: <StyledGridData>{weatherData.is_day}</StyledGridData> </span>
			<span> Visibility: <StyledGridData>{weatherData.visibility}</StyledGridData> </span>
			<span> Cloud Cover: <StyledGridData>{weatherData.cloudcover}</StyledGridData> </span>

			<span> Humidity: <StyledGridData>{weatherData.humidity}</StyledGridData> </span>
			<span> Observation Time: <StyledGridData>{weatherData.observation_time}</StyledGridData> </span>
			<span> Precipitation (Rainfall): <StyledGridData>{weatherData.precip}</StyledGridData> </span>
			<span> Pressure: <StyledGridData>{weatherData.pressure}</StyledGridData> </span>
			<span> Temperature: <StyledGridData>{weatherData.temperature}</StyledGridData> </span>

			<span> UV Index: <StyledGridData>{weatherData.uv_index}</StyledGridData> </span>
			<span> Wind Speed: <StyledGridData>{weatherData.wind_speed}</StyledGridData> </span>
			<span> Wind Degree: <StyledGridData>{weatherData.wind_degree}</StyledGridData> </span>
			<span> Wind Direction (N/S/E/W): <StyledGridData>{weatherData.wind_dir}</StyledGridData> </span>
		</StyledGridView>
	)
}