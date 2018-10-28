import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import SearchList from '../SearchList';
import ViewIcon from '@material-ui/icons/Visibility';
import Button from '@material-ui/core/Button';
import DeleteIcon from '@material-ui/icons/Delete';
import AddIcon from '@material-ui/icons/Add';
import popupService from '../../services/popupService';
import apiService from '../../services/apiService';

class AlarmList extends React.Component {

  deleteAlarm = alarmId => async () => {
    try {
      await apiService.deleteAlarm(alarmId);
      popupService.successPopup('Alarm Deleted !');
      this.searchList.reset();
    } catch (e) {
      popupService.errorPopup();
    }
  };

  addAlarm = async () => {
    try {
      popupService
        .createAlarm(async () => {
          const userData = {
            alarmName: document.getElementById('swal-input1').value,
            criteria: document.getElementById('swal-input2').value
          };

          if (!userData.alarmName || !userData.criteria) {
            throw new Error('Name and criteria are required');
          }

          try {
            await apiService.createAlarm(userData.alarmName, userData.criteria);
          } catch (e) {
            const errorResponse = await e.response.json();
            throw new Error(errorResponse.description);
          }

          return userData;
        })
        .then(result => {
          if (!result.dismiss) {
            popupService.successPopup('Alarm Created !');
            this.searchList.reset();
          }
        });
    } catch (e) {
      popupService.errorPopup();
    }
  };

  render() {
    return (
      <div>
        <Button variant="fab" color="primary" aria-label="Add" onClick={this.addAlarm}>
          <AddIcon />
        </Button>
        <SearchList
          ref={instance => {
            this.searchList = instance;
          }}
          nameService={'getAlarms'}
          startIndex={1}
          displayItem={item => (
            <div className="list-row" key={item.id}>
              <a className="item">{item.name}</a>
              <div>
                <Button onClick={this.deleteAlarm(item.id)}>
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

export default withAuth(connect(mapToProps)(AlarmList));
