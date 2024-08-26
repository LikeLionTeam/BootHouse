export function initFilterSelection() {
    const filterSelects = document.querySelectorAll('.filter-select');
    const resetButton = document.getElementById('resetFilters');
    const searchInput = document.querySelector('.search-input');
    const searchButton = document.getElementById('searchButton');

    filterSelects.forEach(select => {
        select.addEventListener('change', updateFilters);
    });

    resetButton.addEventListener('click', resetFilters);
    searchButton.addEventListener('click', performSearch);

    function updateFilters() {
        const filters = Array.from(filterSelects).reduce((acc, select) => {
            if (select.value !== 'all') {
                acc[select.name] = select.value;
            }
            return acc;
        }, {});

        if (searchInput.value) {
            filters.search = searchInput.value;
        }

        applyFilters(filters);
    }

    function resetFilters() {
        filterSelects.forEach(select => select.value = 'all');
        searchInput.value = '';
        applyFilters({});
    }

    function performSearch() {
        const searchTerm = searchInput.value;
        applyFilters({ search: searchTerm });
    }

    function applyFilters(filters) {
        const queryString = new URLSearchParams(filters).toString();
        window.location.href = '/boothouse/camps?' + queryString;
    }
}