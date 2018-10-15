const applyUpdateResult = ({ result, pageNumber, pageCount}) => prevState => ({
  hits: [...prevState.hits, ...result],
  page: pageNumber,
  pageCount,
  isError: false,
  isLoading: false
});

const applySetResult = ({ result, pageNumber, pageCount}) => () => ({
  hits: result,
  page: pageNumber,
  pageCount,
  isError: false,
  isLoading: false
});

const applySetError = () => ({
  isError: true,
  isLoading: false
});

export default { applySetResult, applyUpdateResult, applySetError };
