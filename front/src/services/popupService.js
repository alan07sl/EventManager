import React from 'react';
import swal from 'sweetalert2';
import moment from 'moment';

const errorPopup = (title = 'Ops! Something failed') => swal({ title, type: 'error', timer: 2000 });

const successPopup = (title = 'OK') =>
  swal({
    title,
    type: 'success',
    timer: 2000
  });

const infoPopup = (title = 'INFO', text = '') =>
  swal({
    title,
    text,
    type: 'success'
  });

const getEventListName = (title = 'Create new List') =>
  swal({
    title,
    input: 'text',
    inputPlaceholder: 'List Name',
    showCancelButton: true,
    inputValidator: value => !value && 'Name of list is mandatory',
    inputAttributes: {
      maxLength: 22
    }
  });

const addEventList = title =>
  swal({
    title,
    input: 'text',
    inputPlaceholder: 'EventID',
    showCancelButton: true,
    inputValidator: value => !value && 'EventID is mandatory',
    inputAttributes: {
      maxLength: 40
    }
  });

const register = preConfirm =>
  swal({
    title: 'Register',
    html:
      '<input id="swal-input1" class="swal2-input" placeholder="Username" maxlength="20">' +
      '<input id="swal-input2" class="swal2-input" placeholder="Password" type="Password" maxlength="20">',
    focusConfirm: false,
    showCancelButton: true,
    expectRejections: true,
    confirmButtonText: 'Accept',
    preConfirm
  });

const createAlarm = preConfirm =>
  swal({
    title: 'Create Alarm',
    html:
      '<input id="swal-input1" class="swal2-input" placeholder="Alarm name" maxlength="20">' +
      '<input id="swal-input2" class="swal2-input" placeholder="Criteria" maxlength="20">',
    focusConfirm: false,
    showCancelButton: true,
    expectRejections: true,
    confirmButtonText: 'Accept',
    preConfirm
  });

const userDetail = userData =>
  swal({
    title: 'User Data',
    html: `</div><b>Username: </b>${userData.username}</div>
           <div><b>Alarms: </b>${userData.alarms}</div>
           <div><b>Events Lists: </b>${userData.eventsLists} </div>
           <div><b>Last Login: </b>${
             userData.lastLogin ? moment(userData.lastLogin).format('DD-MM-YYYY HH:mm') : '-'
           } </div>`,
    showCloseButton: true
  });

export default {
  errorPopup,
  infoPopup,
  getEventListName,
  addEventList,
  successPopup,
  register,
  userDetail,
  createAlarm
};
