import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import './styles.css';
import SearchList from '../SearchList';
import DeleteIcon from '@material-ui/icons/Delete';
import Button from '@material-ui/core/Button';
import apiService from '../../services/apiService';

class MyLists extends React.Component {
  deleteList = item => () => apiService.deleteEventList(item.id).then(() => this.searchList.reset());

  render() {
    return (
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
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(MyLists));
