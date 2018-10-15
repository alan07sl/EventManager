import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import './styles.css';
import SearchList from '../SearchList';
import DeleteIcon from '@material-ui/icons/Delete';
import AddIcon from '@material-ui/icons/Add';
import Button from '@material-ui/core/Button';
import apiService from '../../services/apiService';

import swal from 'sweetalert2';

class MyLists extends React.Component {
  deleteList = item => () => apiService.deleteEventList(item.id).then(() => this.searchList.reset());

  addList = async () => {
    try {
      const { value: name } = await swal({
        title: 'Create new list',
        input: 'text',
        inputPlaceholder: 'List Name',
        showCancelButton: true,
        inputValidator: value => (!value || value.length > 22) && 'Name of list is mandatory (max length 22)'
      });
      if (name) {
        return apiService.createEventList(name).then(() => swal('Good job!', 'List Created !', 'success'));
      }
    } catch (e) {}
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
              <Button aria-label="Delete" onClick={this.deleteList(item)}>
                <DeleteIcon />
              </Button>
            </div>
          )}
        />
      </div>
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(MyLists));
