import React, {Fragment, useEffect, useState} from "react";
import axios from "axios"
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Autocomplete from '@material-ui/lab/Autocomplete';
import {Button, TextField} from "@material-ui/core";
import styled from "styled-components";
import {WeatherType} from "../types/weather.type";
import {City} from "../types/city.type";

const StyledDropDownSubmitContainer = styled.div`
	display: flex;
	gap: 1em;
	justify-content: center;
	height: 30%;
	align-items:center;
`;

const StyledWeatherResultView = styled.div`
    margin: 0 auto;
    width: 60%;
    background-color: #009684;
    align-items: center;
    color: white;
    padding: 1em;
    font-family: calibri;
    box-shadow: 0 10px 20px rgb(0 0 0 / 19%), 0 6px 6px rgb(0 0 0 / 23%);
`;

const StyledGridView = styled.div`
	display: grid;
    grid-template-columns: repeat(2,1fr);
    gap: 1em;
    padding: 1em;
    justify-content: center;
`;

const StyledResultViewTitle = styled.span`
	font-size: 2.5em;
    color: #f9e16c;
    font-weight: bold;
    font-family: 'Caveat',cursive;
    text-shadow: 1px 0 0 #000, 0 -1px 0 #000, 0 1px 0 #000, -1px 0 0 #000;
`;

const StyledPageTitle = styled(StyledResultViewTitle)`
	display: flex;
    justify-content: center;
    padding-top: 1em;
`;

const StyledWeatherData = styled.span`
    color: #f9e16c;
	font-family: 'Itim', cursive;
`;

let WEATHER_DESCRIPTION_NOT_FOUND = "No Temperature Description Found";

export const App = () => {

	const [isLoading, setLoading] = useState<boolean>(false);

	const [countries, setCountries] = useState<Country[]>([])
	const [cities, setCities] = useState<City[]>([])
	const [weatherData, setWeatherData] = useState<WeatherType>()

	const [city, setCity] = useState<string>()
	const [country, setCountry] = useState<string>()

	useEffect(() => {
		axios.get("/api/countries")
			.then(res => {
				setCountries(res.data);
			}).catch(() => {
				toast.error('⚠ Something went wrong when finding country/city data', {
					position: "top-right",
					autoClose: 3000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
					progress: undefined,
				});
			})
	}, [])

	const getWeatherByCity = (city: string) => {
		axios.get("/api/weather", {
			params: {
				"city": city
			}
		}).then(res => {
			setWeatherData(res.data.current);
		}).catch(() => {
			toast.error('⚠ Something went wrong when checking the weather', {
				position: "top-right",
				autoClose: 5000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
			});
		})
	}

	const setCountryAndGetCities = (country: string) => {
		setCountry(country);
		setLoading(true)
		axios.get("/api/cities", {
			params: {
				"country": country
			}
		}).then(res => {
			setCities(res.data);
			setLoading(false);
		}).catch(() => {
			setLoading(false);
			toast.error('⚠ Something went wrong when getting city information for selected country', {
				position: "top-right",
				autoClose: 5000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
			});
		})
	}

	const isPropertySet = (property: string) => {
		return property != undefined && property != "";
	}

	return (
		<Fragment>
			<StyledPageTitle> Weather as a Service </StyledPageTitle>
			<StyledDropDownSubmitContainer>
				<Autocomplete
					id="countries-dropdown"
					options={countries.map(country => country.name)}
					getOptionLabel={(option) => option}
					onInputChange={(event, selectedCountry: string) => setCountryAndGetCities(selectedCountry)}
					style={{width: 300}}
					renderInput={(params) => <TextField {...params} label="Country Select" variant="outlined"/>}
				/>
				{isPropertySet(country) ? (
					<Autocomplete
						id="cities-dropdown"
						options={cities.map(cityObj => cityObj.city)}
						getOptionLabel={(option) => option}
						onInputChange={(event, selectedCity: string) => setCity(selectedCity)}
						style={{width: 300}}
						loading={isLoading}
						renderInput={(params) => <TextField {...params} label="City Select" variant="outlined"/>}
					/>
				) : undefined
				}

				{isPropertySet(country) && isPropertySet(city) ? (
					<Button variant={"contained"}
							color={"primary"}
							onClick={() => getWeatherByCity(city)}> Check weather </Button>
				) : undefined
				}
			</StyledDropDownSubmitContainer>
			{
				weatherData ? (
					<StyledWeatherResultView>
						{ weatherData.weather_descriptions.length > 0 ?
							<StyledResultViewTitle> {weatherData.weather_descriptions[0]} </StyledResultViewTitle>
							: WEATHER_DESCRIPTION_NOT_FOUND
						}
						<StyledGridView>
							<span> Weather Icons: [<StyledWeatherData>{weatherData.weather_icons.join(", ")}</StyledWeatherData>] </span>
							<span> Weather Descriptions: [<StyledWeatherData>{weatherData.weather_descriptions.join(", ")}</StyledWeatherData>] </span>

							<span> Is currently Daytime: <StyledWeatherData>{weatherData.is_day}</StyledWeatherData> </span>
							<span> Visibility: <StyledWeatherData>{weatherData.visibility}</StyledWeatherData> </span>
							<span> Cloud Cover: <StyledWeatherData>{weatherData.cloudcover}</StyledWeatherData> </span>
							<span> Feels Like: <StyledWeatherData>{weatherData.feelslike}</StyledWeatherData> </span>

							<span> Humidity: <StyledWeatherData>{weatherData.humidity}</StyledWeatherData> </span>
							<span> Observation Time: <StyledWeatherData>{weatherData.observation_time}</StyledWeatherData> </span>
							<span> Precipitation (Rainfall): <StyledWeatherData>{weatherData.precip}</StyledWeatherData> </span>
							<span> Pressure: <StyledWeatherData>{weatherData.pressure}</StyledWeatherData> </span>
							<span> Temperature: <StyledWeatherData>{weatherData.temperature}</StyledWeatherData> </span>

							<span> UV Index: <StyledWeatherData>{weatherData.uv_index}</StyledWeatherData> </span>
							<span> Wind Speed: <StyledWeatherData>{weatherData.wind_speed}</StyledWeatherData> </span>
							<span> Wind Degree: <StyledWeatherData>{weatherData.wind_degree}</StyledWeatherData> </span>
							<span> Wind Direction (N/S/E/W): <StyledWeatherData>{weatherData.wind_dir}</StyledWeatherData> </span>
						</StyledGridView>
					</StyledWeatherResultView>
				) : null
			}
		</Fragment>
	)
}