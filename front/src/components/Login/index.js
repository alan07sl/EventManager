import React, { Component } from 'react';
import { Form, Field } from 'react-final-form';
import { FORM_ERROR } from 'final-form';
import './style.css';
import ApiService from '../../services/apiService';
import store from '../../store';

class Login extends Component {

  onSubmit(values) {
    return ApiService.login(values.username, values.password)
      .then(() => {
        this.props.history.replace('/home');
        store.setState({ ...store.getState(), user: { username: values.username } })
      })
      .catch(() => ({ [FORM_ERROR]: 'Usuario o contraseña incorrecta' }));
  }

  render() {
    return (
      <div className="center">
        <div className="card">
          <Form
            onSubmit={this.onSubmit.bind(this)}
            validate={values => {
              const errors = {};
              if (!values.username) {
                errors.username = 'Campo Requerido';
              }
              if (!values.password) {
                errors.password = 'Campo Requerido';
              }
              return errors;
            }}
            render={({ submitError, handleSubmit, submitting }) => (
              <form onSubmit={handleSubmit}>
                <Field name="username">
                  {({ input, meta }) => (
                    <div className="input-with-error">
                      {(meta.error || meta.submitError) &&
                        meta.touched && <span>{meta.error || meta.submitError}</span>}
                      <input className="form-item" {...input} type="text" placeholder="Username" />
                    </div>
                  )}
                </Field>
                <Field name="password">
                  {({ input, meta }) => (
                    <div className="input-with-error">
                      {meta.error && meta.touched && <span>{meta.error}</span>}
                      <input className="form-item" {...input} type="password" placeholder="Password" />
                    </div>
                  )}
                </Field>
                {submitError && <div className="error">{submitError}</div>}
                <div className="buttons">
                  <button className="form-submit" type="submit" disabled={submitting}>
                    Log In
                  </button>
                </div>
              </form>
            )}
          />
        </div>
      </div>
    );
  }
}

export default Login;
