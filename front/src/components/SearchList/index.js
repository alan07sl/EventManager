import React from 'react';
import searchService from '../../services/searchService';
import apiService from '../../services/apiService';
import PaginatedList from '../PaginatedList';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Loader from '../Loader';

class SearchList extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      hits: [],
      page: null,
      isLoading: false,
      isError: false,
      startIndex: this.props.startIndex || 0,
      searchOnInit: this.props.searchOnInit || true
    };
  }

  componentDidMount = () => {
    if(this.state.searchOnInit) this.reset();
  }

  onInitialSearch = e => {
    e.preventDefault();
    this.reset();
  };

  reset = () => {
    const value = this.state.search || '';
    this.search(value, this.state.startIndex);
  };

  onPaginatedSearch = () => {
    if (this.state.page < this.state.pageCount) {
      return this.search(this.state.search || '', this.state.page + 1);
    }
  };

  search = (query, page) => {
    this.setState({ isLoading: true });
    apiService[this.props.nameService]({ page, query })
      .then(result => this.onSetResult(result, page))
      .catch(this.onSetError);
  };

  onSetResult = (result, page) => {
    return page === (this.props.startIndex || 0)
      ? this.setState(searchService.applySetResult(result))
      : this.setState(searchService.applyUpdateResult(result));
  };

  onSetError = () => this.setState(searchService.applySetError);

  handleChange = e => this.setState({ search: e.target.value });

  render() {
    return (
      <div className="page">
        <Loader isLoading={this.state.isLoading}/>
        <div className="interactions">
          <form type="submit" onSubmit={this.onInitialSearch}>
            <Button type="submit">Search</Button>
            <TextField type="text" onChange={this.handleChange} />
          </form>
        </div>

        <PaginatedList
          list={this.state.hits}
          isError={this.state.isError}
          isLoading={this.state.isLoading}
          page={this.state.page}
          onPaginatedSearch={this.onPaginatedSearch}
          displayItem={this.props.displayItem}
        />
      </div>
    );
  }
}

export default SearchList;
