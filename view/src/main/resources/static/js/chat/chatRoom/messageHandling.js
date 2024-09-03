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

    const messageDate = new Date(message.timestamp || new Date());
    const formattedDate = formatDate(messageDate);
    const formattedTime = formatTime(messageDate);

    addDateSeparator($messageContainer, messageDate, formattedDate);

    if (message.type === 'JOIN' || message.type === 'LEAVE') {
        addSystemMessage($messageContainer, message.message);
    } else {
        addMessageElement($messageContainer, message, formattedTime);
    }

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
        const $messageContentWrap = $messageElement.querySelector('.message-content-wrap');
        const $messageBubble = $messageElement.querySelector('.message-bubble-wrap');
        const $messageSender = $messageElement.querySelector('.message-sender');

        if ($messageWrap && $messageContentWrap && $messageBubble && $messageSender) {
            const isSentByCurrentUser = message.sender === username || message.sender.name === username;
            $messageWrap.classList.add(isSentByCurrentUser ? 'justify-end' : 'justify-start');
            $messageContentWrap.classList.add(isSentByCurrentUser ? 'items-end' : 'items-start');
            $messageBubble.classList.add(isSentByCurrentUser ? 'bg-purple-100' : 'bg-gray-100');

            // 메시지 작성자 표시
            $messageSender.textContent = isSentByCurrentUser ? 'You' : (message.sender.name || message.sender);
            $messageSender.classList.add(isSentByCurrentUser ? 'text-right' : 'text-left');

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


function addSystemMessage($messageContainer, messageText) {
    const $systemMessageTemplate = document.getElementById('systemMessageTemplate');
    if ($systemMessageTemplate) {
        const $systemMessageElement = $systemMessageTemplate.content.cloneNode(true);
        const $systemMessageText = $systemMessageElement.querySelector('.system-message-text');
        if ($systemMessageText) {
            $systemMessageText.textContent = messageText;
            $messageContainer.appendChild($systemMessageElement);
        }
    }
}

export function onMessageReceived(payload) {
    console.log('Message received:', payload);
    const message = JSON.parse(payload.body);
    showMessage(message);
}