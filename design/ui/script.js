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
    const filter = document.getElementsByClassName('filter')[0];
    const filterButton = document.getElementsByClassName('filter-button')[0];
    const filterBottom = document.getElementById('filter-bottom');

    filter.classList.toggle('max-h-full');
    filter.classList.toggle('opacity-100');
    
    filterButton.classList.toggle('bg-munshell-blue');
    filterButton.classList.toggle('text-white');
    filterButton.classList.toggle('hover:bg-platinum/50');
    filterButton.classList.toggle('hover:bg-cerulean');
    
    filterBottom.classList.toggle('hidden');
}

function toggleOrder() {
    const iconAsc = document.getElementById('icon-asc');
    const iconDesc = document.getElementById('icon-desc');

    iconAsc.classList.toggle('hidden');
    iconDesc.classList.toggle('hidden');
}

function toggleDetailModal() {
    const detailModal = document.getElementsByClassName('detail-modal')[0];
    
    detailModal.classList.toggle('hidden');
    detailModal.classList.toggle('flex');
}

function toggleCreateModal() {
    const createModal = document.getElementsByClassName('create-modal')[0];
    
    createModal.classList.toggle('hidden');
    createModal.classList.toggle('flex');
}

function toggleEditModal() {
    const editModal = document.getElementsByClassName('edit-modal')[0];
    
    editModal.classList.toggle('hidden');
    editModal.classList.toggle('flex');
}
