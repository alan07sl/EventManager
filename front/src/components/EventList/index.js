import React from "react";
import { connect } from "redux-zero/react";
import withAuth from "../withAuth";
import "./styles.css";
import SearchList from "../SearchList";
import Event from "../Event";

class EventList extends React.Component {
  render() {
    return (
      <SearchList
        nameService={"getEvents"}
        displayItem={item => <Event key={item.id} event={item} />}
      />
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(EventList));
