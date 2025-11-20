"use strict";

document.addEventListener('DOMContentLoaded', function () {
    const imageUpload = document.getElementById('imageUpload');
    const imagePreview = document.getElementById('imagePreview');

    imageUpload.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
});

function toggleSidebar() {
    const sidebar = document.getElementsByTagName('aside')[0];
    const overlay = document.getElementById('overlay');

    sidebar.classList.toggle('left-0');
    overlay.classList.toggle('hidden');
}

function toggleFilter() {
    const filter = document.getElementById('filter-container');
    const filterButton = document.getElementsByClassName('filter-button')[0];
    const filterBottom = document.getElementById('filter-bottom');

    filter.classList.toggle('max-h-full');
    filter.classList.toggle('opacity-100');
    
    filterButton.classList.toggle('bg-steel-blue-500');
    filterButton.classList.toggle('text-white');
    filterButton.classList.toggle('hover:bg-platinum-100');
    filterButton.classList.toggle('hover:bg-steel-blue-600');
    
    filterBottom.classList.toggle('hidden');
}

function toggleOrder() {
    const iconAsc = document.getElementById('icon-asc');
    const iconDesc = document.getElementById('icon-desc');

    iconAsc.classList.toggle('hidden');
    iconDesc.classList.toggle('hidden');
}

function toggleModal(modalName) {
    const modal = document.getElementsByClassName(modalName)[0];
    
    modal.classList.toggle('hidden');
    modal.classList.toggle('flex');
}

function onSubmit(event) {
    event.preventDefault();

    for (const errorText of document.getElementsByClassName('error-text')) {
        errorText.classList.add('hidden');
    }

    const editForm = document.forms.item('edit-form');
    const formData = new FormData(editForm);

    let formValid = true;

    if (formData.get('field1') === null || formData.get('field1').trim() === '') {
        const field1Error = document.getElementById('field1-error');
        field1Error.classList.remove('hidden');
        formValid = false;
    }

    if (formData.get('field2') === null || formData.get('field2').trim() === '') {
        const field2Error = document.getElementById('field2-error');
        field2Error.classList.remove('hidden');
        formValid = false;
    }

    if (formValid) {
        editForm.submit();
    }
}


function toggleOptionDropdown(name) {
    const dropdown = document.getElementById(name);

    dropdown.classList.toggle('hidden');
}

function updateSelected(target, displayId) {
    const display = document.getElementById(displayId);

    if (target.checked) {
        const span = document.createElement('span');
        span.textContent = target.value;
        display.appendChild(span);
    } else {
        for (const span of display.querySelectorAll('span')) {
            if (span.textContent === target.value) {
                span.remove();
            }
        }
    }
}