export function initSearchInput($searchInput, $userList) {
    if (!$searchInput || !$userList) return;

    $searchInput.addEventListener('input', () => {
        const searchTerm = $searchInput.value.toLowerCase();
        const users = $userList.querySelectorAll('li');

        users.forEach(user => {
            const userName = user.textContent.toLowerCase();
            if (userName.includes(searchTerm)) {
                user.style.display = '';
            } else {
                user.style.display = 'none';
            }
        });
    });
}