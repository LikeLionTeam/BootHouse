export function initCourseList() {
    const sortSelect = document.querySelector('.sort-select');
    sortSelect.addEventListener('change', () => {
        const sortValue = sortSelect.value;
        const currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('sort', sortValue);
        window.location.href = currentUrl.toString();
    });
}