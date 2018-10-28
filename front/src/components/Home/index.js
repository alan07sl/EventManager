import React, { Component } from 'react';
import { Route, NavLink, HashRouter } from 'react-router-dom';
import EventList from '../EventList';
import MyLists from '../MyLists';
import UserList from '../UserList';
import AlarmList from '../AlarmList';
import Lists from '../Lists';
import Stats from '../Stats';
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
              <NavLink to="/alarms">Alarms</NavLink>
              { ApiService.isAdmin() ? <NavLink to="/users">Users</NavLink>:null }
              { ApiService.isAdmin() ? <NavLink to="/all-lists">Lists</NavLink>:null }
              { ApiService.isAdmin() ? <NavLink to="/stats">Stats</NavLink>:null }
            </li>
          </ul>
          <div className="content">
            <Route path="/events-lists" component={EventList} />
            <Route path="/my-lists" component={MyLists} />
            <Route path="/alarms" component={AlarmList} />
            <Route path="/users" component={UserList} />
            <Route path="/all-lists" component={Lists} />
            <Route path="/stats" component={Stats} />
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default Home;
