"use strict";

function toggleForm() {
    resetForm();
    toggleModal('create-edit-modal');
}

function submitForm(event) {
    event.preventDefault();

    for (const errorText of document.getElementsByClassName('error-text')) {
        errorText.classList.add('hidden');
    }

    const form = document.getElementById('create-edit-form');
    const formData = new FormData(form);

    let formValid = true;

    if (formData.get('username') === null || formData.get('username').trim() === '') {
        const usernameError = document.getElementById('username-error');
        usernameError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('fullname') === null || formData.get('fullname').trim() === '') {
        const fullnameError = document.getElementById('fullname-error');
        fullnameError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('password') === null || formData.get('password').trim() === '') {
        const passwordError = document.getElementById('password-error');
        passwordError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('confirmPassword') === null || formData.get('confirmPassword').trim() === '') {
        const confirmPasswordError = document.getElementById('confirmPassword-error');
        confirmPasswordError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('gender') === null || formData.get('gender').trim() === '') {
        const genderError = document.getElementById('gender-error');
        genderError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('dateOfBirth') === null || formData.get('dateOfBirth').trim() === '') {
        const dateOfBirthError = document.getElementById('dateOfBirth-error');
        dateOfBirthError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('email') === null || formData.get('email').trim() === '') {
        const emailError = document.getElementById('email-error');
        emailError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('phoneNumber') === null || formData.get('phoneNumber').trim() === '') {
        const phoneNumberError = document.getElementById('phoneNumber-error');
        phoneNumberError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('role') === null || formData.get('role').trim() === '') {
        const roleError = document.getElementById('role-error');
        roleError.classList.remove('hidden');
        formValid = false;
    }

    if (formValid) {
        form.submit();
    }
}

function resetForm() {
    onReset(['role-display']);
}

function toggleCreate() {
    const title = document.getElementById("create-edit-title");
    
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");

    title.textContent = "Create User";

    username.parentElement.classList.remove('hidden');
    password.parentElement.classList.remove('hidden');
    confirmPassword.parentElement.classList.remove('hidden');

    toggleForm();
}

function toggleEdit() {
    const title = document.getElementById("create-edit-title");
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");

    title.textContent = "Edit User";

    username.parentElement.classList.add('hidden');
    password.parentElement.classList.add('hidden');
    confirmPassword.parentElement.classList.add('hidden');

    toggleForm();
}