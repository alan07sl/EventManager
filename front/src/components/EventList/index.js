import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import './styles.css';
import SearchList from '../SearchList';

class EventList extends React.Component {
  render() {
    return (
      <SearchList
        nameService={'getEvents'}
        displayItem={item => (
          <div className="list-row" key={item.id}>
            <a className="item">{item.name.text}</a>
          </div>
        )}
      />
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(EventList));
