import React from 'react';
import searchService from '../../services/searchService';
import apiService from '../../services/apiService';
import PaginatedList from '../PaginatedList';

class SearchList extends React.Component {
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
    this.search(value, 0);
  };

  onPaginatedSearch = () => {
    if (this.state.page < this.state.pageCount) {
      return this.search(this.input.value, this.state.page + 1);
    }
  };

  search = (query, page) => {
    this.setState({ isLoading: true });
    apiService[this.props.nameService]({ page, query })
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
          displayItem ={this.props.displayItem}
        />
      </div>
    );
  }
}

export default SearchList;
