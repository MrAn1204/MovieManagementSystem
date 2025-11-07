"use strict";

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
    filterBottom.classList.toggle('hidden');
}

function toggleOrder() {
    const iconAsc = document.getElementById('icon-asc');
    const iconDesc = document.getElementById('icon-desc');

    iconAsc.classList.toggle('hidden');
    iconDesc.classList.toggle('hidden');
}