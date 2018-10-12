import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Route, BrowserRouter as Router } from 'react-router-dom';

import Login from './components/Login';

ReactDOM.render(
    <Router>
        <div>
            <Route exact path="/" component={Login} />
        </div>
    </Router>
    , document.getElementById('root')
);
