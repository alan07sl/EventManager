import React, { Component } from 'react';
import { Route, NavLink, HashRouter, Redirect } from 'react-router-dom';
import EventList from '../EventList';
import ApiService from '../../services/apiService';
import './style.css';

class Home extends Component {
  
  logout = () =>
    ApiService.logout().finally(() => this.props.history.replace('/'));
  
  render() {
    return (
      <HashRouter>
        <div className="main-container">
          <div className="nav-bar">
            <h1 className="name-app">Event Manager</h1>
            <button className="btn-logout" onClick={this.logout}>Logout</button>
          </div>
          <ul className="header">
            <li>
              <NavLink to="/events-lists">Events</NavLink>
            </li>
          </ul>
          <div className="content">
            <Route path="/events-lists" component={EventList} />
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default Home;
