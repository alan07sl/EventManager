import React, { Component } from 'react';
import { Form, Field } from 'react-final-form';
import { FORM_ERROR } from 'final-form';
import './style.css';
import ApiService from '../../services/apiService';
import store from '../../store';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

class Login extends Component {

  onSubmit(values) {
    return ApiService.login(values.username, values.password)
      .then(() => {
        this.props.history.replace('/home');
        store.setState({ ...store.getState(), user: { username: values.username } })
      })
      .catch(() => ({ [FORM_ERROR]: 'Usuario o contraseña incorrecta' }));
  }

  getLabel(label,meta) {
    return meta.error && meta.touched ? meta.error : label;
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
                    <div>
                      <TextField error={meta.error && meta.touched} margin="normal" {...input} type="text" label={this.getLabel("Username",meta)} />
                    </div>
                  )}
                </Field>
                <Field name="password">
                  {({ input, meta }) => (
                    <div>
                      <TextField error={meta.error && meta.touched} margin="normal" {...input} type="password" label={this.getLabel("Password",meta)} />
                    </div>
                  )}
                </Field>
                {submitError && <div className="error">{submitError}</div>}
                <div className="buttons">
                  <Button margin="normal" className="form-submit" type="submit" disabled={submitting}>
                    Login
                  </Button>
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
