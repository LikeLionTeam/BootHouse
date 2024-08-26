export function initCategorySelection() {
    const categoryItems = document.querySelectorAll('.category-item');
    categoryItems.forEach(item => {
        item.addEventListener('click', (event) => {
            const categoryId = event.target.dataset.categoryId;
            window.location.href = '/boothouse/camps?categories=' + categoryId;
        });
    });
}