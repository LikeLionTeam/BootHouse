import { startChat } from './startChat.js';

export function initStartChatButton($startChatBtn, $targetName, $currentUsername, $errorMessage) {
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
}