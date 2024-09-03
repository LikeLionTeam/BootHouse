import { initNewChatButton } from './newChatButton.js';
import { initUserList } from './userList.js';
import { initSearchInput } from './searchInput.js';

document.addEventListener('DOMContentLoaded', () => {
    const $newChatBtn = document.getElementById('newChatBtn');
    const $userList = document.getElementById('userList');
    const $searchInput = document.getElementById('searchInput');

    if ($newChatBtn) initNewChatButton();
    if ($userList) initUserList($userList);
    if ($searchInput && $userList) initSearchInput($searchInput, $userList);
});