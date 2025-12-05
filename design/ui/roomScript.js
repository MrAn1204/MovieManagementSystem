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

    if (formData.get('name') === null || formData.get('name').trim() === '') {
        const nameError = document.getElementById('name-error');
        nameError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('seatQuantity') === null || formData.get('seatQuantity').trim() === '') {
        const seatQuantityError = document.getElementById('seatQuantity-error');
        seatQuantityError.classList.remove('hidden');
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

    title.textContent = "Create Room";

    toggleForm();
}

function toggleEdit() {
    const title = document.getElementById("create-edit-title");

    title.textContent = "Edit Room";

    toggleForm();
}