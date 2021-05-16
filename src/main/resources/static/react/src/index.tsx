import React from 'react';
import ReactDOM from 'react-dom';
import {App} from './components/App';

import "./theme/style.css";
import {toast} from "react-toastify";

ReactDOM.render(
	<App/>,
	document.getElementById('root')
);

toast.configure();
