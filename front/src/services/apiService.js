import decode from 'jwt-decode';

const setToken = idToken => {
  // Saves user token to localStorage
  localStorage.setItem('id_token', idToken);
};

const getToken = () => localStorage.getItem('id_token');

const login = (username, password) =>
  requestUrl(`${process.env.REACT_APP_API}/users/login`, {
    method: 'POST',
    headers: {},
    body: JSON.stringify({
      username,
      password
    })
  }).then(res => {
    setToken(res.headers.get('authorization'));
    return res;
  });

const isTokenExpired = token => {
  try {
    const decoded = decode(token);
    return decoded.exp < Date.now() / 1000;
  } catch (err) {
    return false;
  }
};

const loggedIn = () => {
  // Checks if there is a saved token and it's still valid
  const token = getToken();
  return !!token && !isTokenExpired(token); // handwaiving here
};

const logout = () => {
  // Clear user token and profile data from localStorage
  localStorage.removeItem('id_token');
};

const getProfile = () => decode(getToken());

const _checkStatus = response => {
  // raises an error in case response status is not a success
  if (response.status >= 200 && response.status < 300) {
    return response;
  } else {
    const error = new Error(response.statusText);
    error.response = response;
    throw error;
  }
};

const requestUrl = (url, options) => {
  // performs api calls sending the required authentication headers
  const headers = {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  };

  if (loggedIn()) {
    headers['Authorization'] = 'Bearer ' + getToken();
  }

  return fetch(url, {
    headers,
    ...options
  }).then(_checkStatus);
};

export default { login, logout, getProfile, loggedIn };
