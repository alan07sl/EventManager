import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';

const EventList = ({ user }) => (
    <h2>Welcome {user.username} !</h2>
);

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(EventList));