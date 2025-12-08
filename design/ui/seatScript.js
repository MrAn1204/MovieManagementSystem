"use strict";

function toggleSeatForm() {
    resetSeatForm();
    toggleModal('seat-create-edit-modal');
}

function submitSeatForm(event) {
    event.preventDefault();

    for (const errorText of document.getElementsByClassName('error-text')) {
        errorText.classList.add('hidden');
    }

    const form = document.getElementById('seat-create-edit-form');
    const formData = new FormData(form);

    let formValid = true;

    if (formData.get('seatName') === null || formData.get('seatName').trim() === '') {
        const seatNameError = document.getElementById('seatName-error');
        seatNameError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('seatType') === null || formData.get('seatType').trim() === '') {
        const seatTypeError = document.getElementById('seatType-error');
        seatTypeError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('seatRow') === null || formData.get('seatRow').trim() === '') {
        const seatRowError = document.getElementById('seatRow-error');
        seatRowError.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('seatColumn') === null || formData.get('seatColumn').trim() === '') {
        const seatColumnError = document.getElementById('seatColumn-error');
        seatColumnError.classList.remove('hidden');
        formValid = false;
    }

    if (formValid) {
        form.submit();
    }
}

function resetSeatForm() {
    const form = document.getElementById('seat-create-edit-form');
    form.reset();
    
    for (const errorText of document.getElementsByClassName('error-text')) {
        errorText.classList.add('hidden');
    }
}

function toggleCreateSeat() {
    const title = document.getElementById("seat-create-edit-title");

    console.log('aaa');
    
    title.textContent = "Create Seat";
    toggleSeatForm();
}

function toggleEditSeat() {
    const title = document.getElementById("seat-create-edit-title");
    title.textContent = "Edit Seat";

    toggleSeatForm();
}