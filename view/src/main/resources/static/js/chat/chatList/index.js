import { initNewChatButton } from './newChatButton.js';
import { initUserList } from './userList.js';
import { initSearchInput } from './searchInput.js';

document.addEventListener('DOMContentLoaded', () => {
    initNewChatButton();
    initUserList();
    initSearchInput();
});