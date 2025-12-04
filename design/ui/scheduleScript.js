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

    if (formData.get('showTime') === null || formData.get('showTime').trim() === '') {
        const showTimeError = document.getElementById('showTime-error');
        showTimeError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('room') === null || formData.get('room').trim() === '') {
        const roomError = document.getElementById('room-error');
        roomError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('movie') === null || formData.get('movie').trim() === '') {
        const movieError = document.getElementById('movie-error');
        movieError.classList.remove('hidden');
        formValid = false;
    }

    if (formValid) {
        form.submit();
    }
}

function resetForm() {
    onReset();
}

function toggleCreate() {
    const title = document.getElementById("create-edit-title");

    title.textContent = "Create Schedule";

    toggleForm();
}

function toggleEdit() {
    const title = document.getElementById("create-edit-title");

    title.textContent = "Edit Schedule";

    toggleForm();
}