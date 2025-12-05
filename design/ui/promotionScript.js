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

    if (formData.get('title') === null || formData.get('title').trim() === '') {
        const titleError = document.getElementById('title-error');
        titleError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('discount') === null || formData.get('discount').trim() === '') {
        const discountError = document.getElementById('discount-error');
        discountError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('startDate') === null || formData.get('startDate').trim() === '') {
        const startDateError = document.getElementById('startDate-error');
        startDateError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('endDate') === null || formData.get('endDate').trim() === '') {
        const endDateError = document.getElementById('endDate-error');
        endDateError.classList.remove('hidden');
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

    title.textContent = "Create Promotion";

    toggleForm();
}

function toggleEdit() {
    const title = document.getElementById("create-edit-title");

    title.textContent = "Edit Promotion";

    toggleForm();
}