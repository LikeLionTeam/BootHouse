export function initSearchInput($searchInput, $userList) {
    $searchInput.addEventListener('input', () => {
        const searchTerm = $searchInput.value.toLowerCase();
        const users = $userList.querySelectorAll('li');

        users.forEach(user => {
            const userName = user.querySelector('span').textContent.toLowerCase();
            if (userName.includes(searchTerm)) {
                user.style.display = '';
            } else {
                user.style.display = 'none';
            }
        });
    });
}