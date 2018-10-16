import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import SearchList from '../SearchList';
import ViewIcon from '@material-ui/icons/Visibility';
import Button from '@material-ui/core/Button';
import popupService from '../../services/popupService';
import apiService from '../../services/apiService';

class UserList extends React.Component {
  viewUserDetail = userId => async () => {
    try {
      const userData = await apiService.getUserDetail(userId);
      popupService.userDetail(userData);
    } catch (e) {
      popupService.errorPopup();
    }
  };

  render() {
    return (
      <SearchList
        nameService={'getUsers'}
        startIndex={1}
        displayItem={item => (
          <div className="list-row" key={item.id}>
            <a className="item">{item.username}</a>
            <Button onClick={this.viewUserDetail(item.id)}>
              <ViewIcon />
            </Button>
          </div>
        )}
      />
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(UserList));
