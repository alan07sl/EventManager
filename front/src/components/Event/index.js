import React, { Component } from 'react';
import './styles.css';
import calendarIcon from '../../assets/calendar.svg';
import Moment from 'react-moment';
import 'moment/locale/es';
import Clipboard from 'react-clipboard.js';
import popupService from '../../services/popupService';
import apiService from '../../services/apiService';
import noContentImage from '../../assets/no_content.jpg';
import ViewIcon from '@material-ui/icons/Visibility';
import LinkIcon from '@material-ui/icons/Link';
import Button from '@material-ui/core/Button';

class Event extends Component {
  constructor(props) {
    super(props);

    this.state = {
      event: props.event
    };
  }

  copyClipboard = () => popupService.successPopup('EventID copied to clipboard');

  getEventStats = eventId => async () => {
    const eventStats = await apiService.getEventStats(eventId);
    popupService.infoPopup('Event Stats', `Amount users: ${eventStats.amount}`);
  };

  render() {
    const event = this.state.event;

    if (event === undefined) {
      return <div />;
    }

    const logoUrl = event.logo ? event.logo.url : noContentImage;

    return (
      <div className="container">
        <img className="image" src={logoUrl} />
        <div className="detail">
          <div className="name">
            {event.name.text.length < 50 ? event.name.text : `${event.name.text.substring(0, 47)}...`}
          </div>
          <div className="date">
            <img className="calendar-icon" src={calendarIcon} />
            <Moment locale="es" format="LLLL" date={event.start.local} />
          </div>
        </div>
        <div className="buttons-events">
          <Clipboard data-clipboard-text={event.id} className="link-button">
            <Button onClick={this.copyClipboard}>
              <LinkIcon />
            </Button>
          </Clipboard>
          {apiService.isAdmin() ? (
            <Button onClick={this.getEventStats(event.id)}>
              <ViewIcon />
            </Button>
          ) : null}
        </div>
      </div>
    );
  }
}

export default Event;
