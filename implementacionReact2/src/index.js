import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import { Route, BrowserRouter as Router } from 'react-router-dom';

// Our Components
import Login from './components/Login';
import MenuInicio from "./MenuInicio";
import ListadoEventos from "./ListadoEventos";
import MisListas from "./MisListas";

ReactDOM.render(
    <Router>
        <div>
            <Route exact path="/" component={App} />
            <Route exact path="/login" component={Login} />
            <Route path="/inicio" component={MenuInicio} />
            <Route path="/ListadoEventos" component={ListadoEventos} />
            <Route path="/MisListas" component={MisListas} />
        </div>
    </Router>
    , document.getElementById('root')
);
registerServiceWorker();
