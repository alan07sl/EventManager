import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import SearchList from '../SearchList';
import apiService from '../../services/apiService';
import popupService from '../../services/popupService';
import CompareIcon from '@material-ui/icons/CompareArrows';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';

class Lists extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      checkedValues: []
    };
  }
  checkList = listId => (event, checked) => {
    const checkedValues = this.state.checkedValues;
    if (checked) {
      if (checkedValues.length == 2) {
        checkedValues.pop();
      }
      checkedValues.push(listId);

      this.setState({ checkedValues });
    } else {
      this.setState({ checkedValues: checkedValues.filter(id => id !== listId) });
    }
  };

  compareLists = async () => {
    try {
      const response = await apiService.getEventListMatch(
        this.state.checkedValues[0],
        this.state.checkedValues[1]
      );
      console.log(response.length);
      popupService.infoPopup(`Events match count: ${response.length}`);
    } catch (e) {
      popupService.errorPopup();
    }
  };

  render() {
    return (
      <div>
        <SearchList
          ref={instance => {
            this.searchList = instance;
          }}
          nameService={'getLists'}
          startIndex={1}
          displayItem={item => (
            <div className="list-row" key={item.id}>
              <a className="item">{item.name}</a>
              <div>
                <Button
                  onClick={this.compareLists}
                  disabled={
                    !this.state.checkedValues.includes(item.id) || this.state.checkedValues.length !== 2
                  }
                >
                  <CompareIcon />
                </Button>

                <Checkbox
                  onChange={this.checkList(item.id)}
                  checked={this.state.checkedValues.includes(item.id)}
                />
              </div>
            </div>
          )}
        />
      </div>
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(Lists));
