import React, { Component } from "react";
import "./styles.css";
import calendarIcon from "../../assets/calendar.svg";
import Moment from "react-moment";
import "moment/locale/es";
import Clipboard from "react-clipboard.js";
import popupService from "../../services/popupService";
import noContentImage from "../../assets/no_content.jpg";

class Event extends Component {
  constructor(props) {
    super(props);

    this.state = {
      event: props.event
    };
  }

  copyClipboard = () =>
    popupService.successPopup("EventID copied to clipboard");

  render() {
    const event = this.state.event;

    if (event === undefined) {
      return <div />;
    }

    const logoUrl = event.logo ? event.logo.url : noContentImage;

    return (
      <div className="container" onClick={this.copyClipboard}>
        <Clipboard data-clipboard-text={event.id} className="link-button">
          <img className="image" src={logoUrl} />
          <div className="detail">
            <div className="name">{event.name.text}</div>
            <div className="date">
              <img className="calendar-icon" src={calendarIcon} />
              <Moment locale="es" format="LLLL" date={event.start.local} />
            </div>
          </div>
        </Clipboard>
      </div>
    );
  }
}

export default Event;
