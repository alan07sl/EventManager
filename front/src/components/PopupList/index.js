import Dialog from '@material-ui/core/Dialog';
import List from '@material-ui/core/List';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';
import React from 'react';
import './style.css';

import popupService from '../../services/popupService';

function Transition(props) {
  return <Slide direction="up" {...props} />;
}

class PopupList extends React.Component {
  state = {
    rows: [],
    open: false,
    isLoading: false
  };

  open = async () => {
    try {
      this.setState({ isLoading: true });
      const rows = await this.props.itemsService();
      this.setState({ open: true, rows, isLoading: false });
    } catch (e) {
      popupService.errorPopup();
      this.close();
    }
  };

  close = () => {
    this.setState({ open: false, rows: [], isLoading: false });
  };

  render() {
    return (
      <Dialog fullScreen open={this.state.open} onClose={this.close} TransitionComponent={Transition}>
        <AppBar className="appBar">
          <Toolbar>
            <IconButton color="inherit" onClick={this.close} aria-label="Close">
              <CloseIcon />
            </IconButton>
            <Typography color="inherit" className="title">
              {this.props.title}
            </Typography>
          </Toolbar>
        </AppBar>
        <List>{!this.state.isLoading ? this.state.rows.map(row => this.props.itemMapper(row)) : null}</List>
      </Dialog>
    );
  }
}

export default PopupList;
