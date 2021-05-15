import React, {useState} from "react";
import axios from "axios"
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Autocomplete from '@material-ui/lab/Autocomplete';
import {TextField} from "@material-ui/core";

export const App = () => {

	const [city, setCity] = useState<string>()

	const getWeatherByCity = (city: string) => {
		console.log(window.location.origin + "/api/weather");
		axios.get(window.location.origin + "/api/weather", {
			params: {
				"city": city
			}
		}).then(res => {
			console.log(res);
		}).catch(err => {
			toast.error('⚠ Something went wrong when checking the weather', {
				position: "top-right",
				autoClose: 3000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
				progress: undefined,
			});
		})
	}

	return (
		<div>
			<Autocomplete
				id="cities-dropdown"
				options={["Dublin", "Paris", "Vancouver", "Tokyo"]}
				getOptionLabel={(option) => option}
				onInputChange={(event, selectedCity: string) => setCity(selectedCity)}
				style={{width: 300}}
				renderInput={(params) => <TextField {...params} label="Combo box" variant="outlined"/>}
			/>
			<button onClick={() => getWeatherByCity(city)}> Get weather </button>
		</div>
	)
}