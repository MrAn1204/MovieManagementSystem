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

    if (formValid) {
        form.submit();
    }
}

function resetForm() {
    onReset(['genre-display', 'studio-display', 'talent-display']);
}

function toggleCreate() {
    const title = document.getElementById("create-edit-title");

    title.textContent = "Create Movie";

    toggleForm();
}

function toggleEdit() {
    const title = document.getElementById("create-edit-title");

    title.textContent = "Edit Movie";

    toggleForm();
}

function updateTicketInfo(seat, price) {
    const ticketSeat = document.getElementById("ticket-seat");
    const ticketPrice = document.getElementById("ticket-price");
    
    ticketSeat.textContent = seat;
    ticketPrice.textContent = price;
}