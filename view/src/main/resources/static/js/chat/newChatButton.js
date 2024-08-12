import { openModal } from './modal.js';

export function initNewChatButton($newChatBtn, templateId, modalId) {
    $newChatBtn.addEventListener('click', () => {
        const modalTemplate = document.getElementById(templateId);
        const modalContent = modalTemplate.content.cloneNode(true);
        document.body.appendChild(modalContent);
        openModal(modalId);
    });
}