import React, { Component } from 'react';
import ReactDom from 'react-dom';
import { Route, NavLink, HashRouter } from 'react-router-dom';
import EventList from '../EventList';
import MyLists from '../MyLists';
import ApiService from '../../services/apiService';
import './style.css';

class Home extends Component {
  logout = () => ApiService.logout().finally(() => this.props.history.replace('/'));

  render() {
    return (
      <HashRouter>
        <div className="main-container" id="main-container">
          <div className="nav-bar">
            <h1 className="name-app">Event Manager</h1>
            <button className="btn-logout" onClick={this.logout}>
              Logout
            </button>
          </div>
          <ul className="header">
            <li>
              <NavLink to="/events-lists">Events</NavLink>
              <NavLink to="/my-lists">My Lists</NavLink>
            </li>
          </ul>
          <div className="content">
            <Route path="/events-lists" component={EventList} />
            <Route path="/my-lists" component={MyLists} />
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default Home;
