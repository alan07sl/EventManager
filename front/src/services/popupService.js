import swal from 'sweetalert2';

const errorPopup = (title = 'Ops! Something failed') => swal({ title, type: 'error', timer: 2000 });

const successPopup = (title = 'OK') =>
  swal({
    title,
    type: 'success',
    timer: 2000
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

export default { errorPopup, getEventListName, addEventList, successPopup, register };
