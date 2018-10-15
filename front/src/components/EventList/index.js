import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import './styles.css';
import SearchList from '../SearchList';
import LinkIcon from '@material-ui/icons/Link';
import Button from '@material-ui/core/Button';
import Clipboard from 'react-clipboard.js';
import popupService from '../../services/popupService';

class EventList extends React.Component {
  
  copyClipboard = () => popupService.successPopup('EventID copied to clipboard');
  
  render() {
    return (
      <SearchList
        nameService={'getEvents'}
        displayItem={item => (
          <div className="list-row" key={item.id}>
            <a className="item">{item.name.text}</a>
            <Clipboard data-clipboard-text={item.id} className="link-button">
              <Button aria-label="Link" onClick={this.copyClipboard}>
                <LinkIcon />
              </Button>
            </Clipboard>
          </div>
        )}
      />
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(EventList));
