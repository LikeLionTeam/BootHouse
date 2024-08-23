import { sendWebSocketMessage } from './websocketConnection.js';
import { showMessage } from './messageHandling.js';

export function setupEventListeners() {
    const $sendButton = document.getElementById('sendButton');
    const $messageInput = document.getElementById('messageInput');

    if ($sendButton) {
        $sendButton.addEventListener('click', sendMessage);
    }

    if ($messageInput) {
        $messageInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                sendMessage();
            }
        });
    }
}

function sendMessage() {
    const $messageInput = document.getElementById('messageInput');
    const content = $messageInput.value.trim();
    if (content !== '') {
        const chatMessage = {
            chatroomId: chatroomId,
            sender: username,
            message: content
        };
        sendWebSocketMessage(chatMessage);
        $messageInput.value = '';
    }
}