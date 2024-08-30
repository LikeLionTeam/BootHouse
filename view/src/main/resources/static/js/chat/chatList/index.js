import { initNewChatButton } from './newChatButton.js';

document.addEventListener('DOMContentLoaded', () => {
    const $newChatBtn = document.getElementById('newChatBtn');
    const $currentUsername = document.getElementById('currentUsername');

    initNewChatButton($newChatBtn, $currentUsername);
});