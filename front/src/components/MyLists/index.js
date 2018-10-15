import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import './styles.css';
import SearchList from '../SearchList';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import AddIcon from '@material-ui/icons/Add';
import Button from '@material-ui/core/Button';
import apiService from '../../services/apiService';
import popupService from '../../services/popupService';

class MyLists extends React.Component {
  deleteList = item => async () => {
    try {
      await apiService.deleteEventList(item.id);
      popupService.successPopup('List Deleted !');
      this.searchList.reset();
    } catch (e) {
      popupService.errorPopup();
    }
  };

  addEvent = item => async () => {
    try {
      const { value: id } = await popupService.addEventList(`Add event to list: "${item.name}"`);

      if (id) {
        await apiService.addEventList(item.id, id);
        popupService.successPopup(`Event ${id} added to list`);
      }
    } catch (e) {
      const errorResponse = await e.response.json();
      if (errorResponse.status === 404) {
        popupService.errorPopup('Event not found');
      } else {
        popupService.errorPopup();
      }
    }
  };

  updateList = item => async () => {
    try {
      const { value: name } = await popupService.getEventListName(`Update list: "${item.name}"`);

      if (name) {
        await apiService.updateEventList(item.id, name);
        popupService.successPopup('List Updated !');
        this.searchList.reset();
      }
    } catch (e) {
      popupService.errorPopup();
    }
  };

  addList = async () => {
    try {
      const { value: name } = await popupService.getEventListName();
      if (name) {
        await apiService.createEventList(name);
        popupService.successPopup('List Created !');
        this.searchList.reset();
      }
    } catch (e) {
      popupService.errorPopup();
    }
  };

  render() {
    return (
      <div>
        <Button variant="fab" color="primary" aria-label="Add" onClick={this.addList}>
          <AddIcon />
        </Button>
        <SearchList
          ref={instance => {
            this.searchList = instance;
          }}
          nameService={'getMyLists'}
          startIndex={1}
          displayItem={item => (
            <div className="list-row" key={item.id}>
              <a className="item">{item.name}</a>
              <div>
                <Button aria-label="Add" onClick={this.addEvent(item)}>
                  <AddIcon />
                </Button>
                <Button aria-label="Edit" onClick={this.updateList(item)}>
                  <EditIcon />
                </Button>
                <Button aria-label="Delete" onClick={this.deleteList(item)}>
                  <DeleteIcon />
                </Button>
              </div>
            </div>
          )}
        />
      </div>
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(MyLists));
