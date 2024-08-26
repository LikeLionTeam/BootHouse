export function initComparisonMenu() {
    const toggleButton = document.getElementById('toggleComparisonMenu');
    const menu = document.getElementById('comparisonMenu');
    const checkboxes = document.querySelectorAll('.comparison-checkbox');

    toggleButton.addEventListener('click', () => {
        menu.classList.toggle('hidden');
    });

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            const columnClass = checkbox.value;
            const display = checkbox.checked ? 'table-cell' : 'none';
            document.querySelectorAll('.' + columnClass).forEach(cell => {
                cell.style.display = display;
            });
        });
    });
}