import { openModal, closeModal } from './modal.js';
import { startChat } from './startChat.js';

const $newChatBtn = document.getElementById('newChatBtn');
const $currentUsername = document.getElementById('currentUsername');

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

    $startChatBtn.addEventListener('click', () => {
        const targetName = $targetName.value;
        const currentUsername = $currentUsername.textContent;

        if (targetName === currentUsername) {
            $errorMessage.textContent = '자기 자신과는 채팅할 수 없습니다.';
            return;
        }

        startChat(targetName)
            .then(response => {
                if (response.error) {
                    $errorMessage.textContent = response.error;
                } else {
                    window.location.href = '/messages/' + response.chatroomId;
                }
            })
            .catch(error => {
                $errorMessage.textContent = 'An error occurred. Please try again.';
            });
    });

    $cancelBtn.addEventListener('click', () => {
        closeModal('newChatModal');
    });

    $newChatModal.addEventListener('click', (e) => {
        if (e.target === $newChatModal) {
            closeModal('newChatModal');
        }
    });
});