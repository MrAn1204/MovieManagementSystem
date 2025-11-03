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
    
    filter.classList.toggle('hidden');
    filterButton.classList.toggle('bg-munshell-blue');
    filterButton.classList.toggle('text-white');
}