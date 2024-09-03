export function initUserList($userList) {
    if (!$userList) return;

    $userList.addEventListener('change', (event) => {
        if (event.target.type === 'checkbox') {
            const li = event.target.closest('li');
            if (li) {
                li.classList.toggle('bg-orange-100', event.target.checked);
            }
        }
    });
}