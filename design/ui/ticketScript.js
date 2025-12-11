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

    if (formData.get('movie') === null || formData.get('movie').trim() === '') {
        const movieError = document.getElementById('movie-error');
        movieError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('schedule') === null || formData.get('schedule').trim() === '') {
        const scheduleError = document.getElementById('schedule-error');
        scheduleError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('seat') === null || formData.get('seat').trim() === '') {
        const seatError = document.getElementById('seat-error');
        seatError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('user') === null || formData.get('user').trim() === '') {
        const userError = document.getElementById('user-error');
        userError.classList.remove('hidden');
        formValid = false;
    }

    if (formValid) {
        form.submit();
    }
}

function resetForm() {
    const scheduleSelect = document.getElementById("schedule");
    
    onReset();
    
    scheduleSelect.disabled = true;
}

function toggleCreate() {
    const title = document.getElementById("create-edit-title");
    
    title.textContent = "Create Ticket";

    toggleForm();
}

function toggleEdit() {
    const title = document.getElementById("create-edit-title");
   
    title.textContent = "Edit Ticket";

    toggleForm();
}

function enableSchedule() {
    const scheduleSelect = document.getElementById("schedule");
    scheduleSelect.disabled = false;
}