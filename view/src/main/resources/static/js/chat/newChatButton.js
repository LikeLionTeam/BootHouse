import { openModal, closeModal } from './modal.js';
import { initStartChatButton } from './startChatButton.js';

export function initNewChatButton($newChatBtn, $currentUsername) {
    $newChatBtn.addEventListener('click', () => {
        const modalTemplate = document.getElementById('newChatModalTemplate');
        const modalContent = modalTemplate.content.cloneNode(true);
        document.body.appendChild(modalContent);

        const $newChatModal = document.getElementById('newChatModal');
        const $targetName = document.getElementById('targetName');
        const $startChatBtn = document.getElementById('startChatBtn');
        const $errorMessage = document.getElementById('errorMessage');
        const $cancelBtn = document.getElementById('cancelBtn');

        openModal('newChatModal');

        initStartChatButton($startChatBtn, $targetName, $currentUsername, $errorMessage);

        $cancelBtn.addEventListener('click', () => {
            closeModal('newChatModal');
        });

        $newChatModal.addEventListener('click', (e) => {
            if (e.target === $newChatModal) {
                closeModal('newChatModal');
            }
        });
    });
}