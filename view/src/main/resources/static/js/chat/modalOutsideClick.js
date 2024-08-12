import { closeModal } from './modal.js';

export function initModalCloseOnOutsideClick(modalId) {
    document.addEventListener('click', (e) => {
        if (e.target.id === modalId) {
            closeModal(modalId);
        }
    });
}