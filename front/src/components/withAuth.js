import React, { Component } from 'react';
import apiService from '../services/apiService';

export default function withAuth(AuthComponent) {
  return class AuthWrapped extends Component {
    componentWillMount() {
      if (!apiService.loggedIn()) {
        this.props.history.replace('/');
      }
    }

    render() {
      return <AuthComponent history={this.props.history} />;
    }
  };
}
