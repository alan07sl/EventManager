import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Route, BrowserRouter as Router } from 'react-router-dom';
import { Provider } from 'redux-zero/react';
import store from './store';

import Login from './components/Login';
import EventList from './components/EventList';

require('dotenv').config();

ReactDOM.render(
  <Provider store={store}>
    <Router>
      <div>
        <Route exact path="/" component={Login} />
        <Route exact path="/events" component={EventList} />
      </div>
    </Router>
  </Provider>,
  document.getElementById('root')
);
