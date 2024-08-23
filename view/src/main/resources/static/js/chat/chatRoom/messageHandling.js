import { formatDate, formatTime } from './utils.js';

export function initializeChat() {
    const $messageContainer = document.getElementById('messageContainer');
    if ($messageContainer) {
        $messageContainer.innerHTML = ''; // 기존 메시지 초기화
        initialMessages.forEach(showMessage);
    } else {
        console.error('Message container not found');
    }
}

export function showMessage(message) {
    const $messageContainer = document.getElementById('messageContainer');
    if (!$messageContainer) {
        console.error('Message container not found');
        return;
    }

    const messageDate = new Date(message.registrationDate || new Date()); // 현재 시간을 기본값으로 사용
    const formattedDate = formatDate(messageDate);
    const formattedTime = formatTime(messageDate);

    addDateSeparator($messageContainer, messageDate, formattedDate);
    addMessageElement($messageContainer, message, formattedTime);
    scrollToBottom();
}

function addDateSeparator($messageContainer, messageDate, formattedDate) {
    const dateString = messageDate.toDateString();
    if (!document.getElementById('date-' + dateString)) {
        const $dateTemplate = document.getElementById('messageDateTemplate');
        if ($dateTemplate) {
            const $dateElement = $dateTemplate.content.cloneNode(true);
            const $dateDiv = $dateElement.querySelector('.message-date-wrap');
            if ($dateDiv) {
                $dateDiv.id = 'date-' + dateString;
                $dateDiv.textContent = formattedDate;
                $messageContainer.appendChild($dateElement);
            }
        }
    }
}

function addMessageElement($messageContainer, message, formattedTime) {
    const $messageTemplate = document.getElementById('messageTemplate');
    if ($messageTemplate) {
        const $messageElement = $messageTemplate.content.cloneNode(true);
        const $messageWrap = $messageElement.querySelector('.message-wrap');
        const $messageBubble = $messageElement.querySelector('.message-bubble-wrap');

        if ($messageWrap && $messageBubble) {
            const isSentByCurrentUser = message.sender === username || message.sender.name === username;
            $messageWrap.classList.add(isSentByCurrentUser ? 'justify-end' : 'justify-start');
            $messageBubble.classList.add(isSentByCurrentUser ? 'bg-purple-100' : 'bg-gray-100');

            const $messageText = $messageElement.querySelector('.message-text');
            const $messageTime = $messageElement.querySelector('.message-time');

            if ($messageText) $messageText.textContent = message.message;
            if ($messageTime) $messageTime.textContent = formattedTime;

            $messageContainer.appendChild($messageElement);
        }
    }
}

function scrollToBottom() {
    const $chatMessages = document.getElementById('chatMessages');
    if ($chatMessages) {
        $chatMessages.scrollTop = $chatMessages.scrollHeight;
    }
}

export function onMessageReceived(payload) {
    console.log('Message received:', payload);
    const message = JSON.parse(payload.body);
    showMessage(message);
}