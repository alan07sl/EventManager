import React from 'react';
import { connect } from 'redux-zero/react';
import withAuth from '../withAuth';
import Loader from '../Loader';
import popupService from '../../services/popupService';
import apiService from '../../services/apiService';
import AnimatedNumber from 'react-animated-number';
import moment from 'moment';
import Button from '@material-ui/core/Button';
import './styles.css';

class Stats extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      numberEvents: 0,
      isLoading: false
    };
  }

  getStatsFrom = date => async () => {
    try {
      this.setState({ ...this.state, isLoading: true });
      const count = (await apiService.getEventsFrom(date.format('YYYY-MM-DD'))).count;
      this.setState({ ...this.state, numberEvents: count });
    } catch (e) {
      popupService.errorPopup();
    }
    this.setState({ ...this.state, isLoading: false });
  };

  render() {
    return (
      <div className="containerStats">
        <Loader isLoading={this.state.isLoading} />
        <div className="buttonStats">
          <Button variant="outlined" color="primary" onClick={this.getStatsFrom(moment())}>
            Today
          </Button>
          <Button
            variant="outlined"
            color="primary"
            onClick={this.getStatsFrom(moment().subtract('3', 'days'))}
          >
            3 days ago
          </Button>
          <Button variant="outlined" color="primary" onClick={this.getStatsFrom(moment().startOf('week'))}>
            Last week
          </Button>
          <Button variant="outlined" color="primary" onClick={this.getStatsFrom(moment().startOf('month'))}>
            Last month
          </Button>
          <Button variant="outlined" color="primary" onClick={this.getStatsFrom(moment().set('year', 1970))}>
            All time
          </Button>
        </div>
        <div className="numberStats">
          <label className="textStats">Events Count:</label>
          <AnimatedNumber
            component="text"
            value={this.state.numberEvents}
            style={{
              transition: '0.8s ease-out',
              fontSize: 48,
              transitionProperty: 'background-color, color, opacity'
            }}
            duration={300}
          />
        </div>
      </div>
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(Stats));
