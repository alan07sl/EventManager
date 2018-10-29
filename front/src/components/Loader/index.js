import React from 'react';
import Loader from 'react-loader-spinner';
import './style.css';

class LoaderComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="loader">
        {this.props.isLoading ? <Loader type="Puff" color="#0ac003" height="100" width="100" /> : null}
      </div>
    );
  }
}

export default LoaderComponent;
