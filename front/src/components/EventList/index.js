import React from 'react';
import { connect } from 'redux-zero/react';
import searchService from '../../services/searchService';
import apiService from '../../services/apiService';
import PaginatedList from '../PaginatedList';
import withAuth from '../withAuth';
import './styles.css';

class EventList extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      hits: [],
      page: null,
      isLoading: false,
      isError: false
    };
  }

  onInitialSearch = e => {
    e.preventDefault();
    const { value } = this.input;
    this.fetchStories(value, 0);
  };

  onPaginatedSearch = () => {
    if (this.state.page < this.state.pageCount) {
      return this.fetchStories(this.input.value, this.state.page + 1);
    }
  };

  fetchStories = (query, page) => {
    this.setState({ isLoading: true });
    apiService
      .getEvents({ page, query })
      .then(result => this.onSetResult(result, page))
      .catch(this.onSetError);
  };

  onSetResult = (result, page) =>
    page === 0
      ? this.setState(searchService.applySetResult(result))
      : this.setState(searchService.applyUpdateResult(result));

  onSetError = () => this.setState(searchService.applySetError);

  render() {
    return (
      <div className="page">
        <div className="interactions">
          <form type="submit" onSubmit={this.onInitialSearch}>
            <input type="text" ref={node => (this.input = node)} />
            <button type="submit">Search</button>
          </form>
        </div>

        <PaginatedList
          list={this.state.hits}
          isError={this.state.isError}
          isLoading={this.state.isLoading}
          page={this.state.page}
          onPaginatedSearch={this.onPaginatedSearch}
          displayItem ={item => (
            <div className="list-row" key={item.id}>
              <a className="item" >{item.name.text}</a>
            </div>
          )}
        />
      </div>
    );
  }
}

const mapToProps = ({ user }) => ({ user });

export default withAuth(connect(mapToProps)(EventList));
