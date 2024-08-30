// let stompClient = null;
// const token = getCookie('userTokenCode');
//
// window.addEventListener('DOMContentLoaded', (event) => {
//     connect();
//     initializeChat();
// });
//
// function connect() {
//     const socket = new SockJS('/ws');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, onConnected, onError);
// }
//
// function onConnected(frame) {
//     console.log('Connected: ' + frame);
//     stompClient.subscribe('/topic/messages/' + chatroomId, onMessageReceived);
//     stompClient.subscribe('/user/' + username + '/queue/errors', onError);
// }
//
// function onError(error) {
//     console.log('STOMP error ' + error);
//     setTimeout(connect, 5000);  // 5초 후 재연결 시도
// }
//
// function initializeChat() {
//     const $messageContainer = document.getElementById('messageContainer');
//     if ($messageContainer) {
//         $messageContainer.innerHTML = ''; // 기존 메시지 초기화
//         initialMessages.forEach(showMessage);
//     } else {
//         console.error('Message container not found');
//     }
// }
//
// function showMessage(message) {
//     const $messageContainer = document.getElementById('messageContainer');
//     if (!$messageContainer) {
//         console.error('Message container not found');
//         return;
//     }
//
//     const messageDate = new Date(message.registrationDate);
//     const formattedDate = formatDate(messageDate);
//     const formattedTime = formatTime(messageDate);
//
//     // 날짜 구분선 추가
//     const dateString = messageDate.toDateString();
//     if (!document.getElementById('date-' + dateString)) {
//         const $dateTemplate = document.getElementById('messageDateTemplate');
//         if ($dateTemplate) {
//             const $dateElement = $dateTemplate.content.cloneNode(true);
//             const $dateDiv = $dateElement.querySelector('.message-date-wrap');
//             if ($dateDiv) {
//                 $dateDiv.id = 'date-' + dateString;
//                 $dateDiv.textContent = formattedDate;
//                 $messageContainer.appendChild($dateElement);
//             }
//         }
//     }
//
//     const $messageTemplate = document.getElementById('messageTemplate');
//     if ($messageTemplate) {
//         const $messageElement = $messageTemplate.content.cloneNode(true);
//         const $messageWrap = $messageElement.querySelector('.message-wrap');
//         const $messageBubble = $messageElement.querySelector('.message-bubble-wrap');
//
//         if ($messageWrap && $messageBubble) {
//             $messageWrap.classList.add(message.sender.name === username ? 'justify-end' : 'justify-start');
//             $messageBubble.classList.add(message.sender.name === username ? 'bg-purple-100' : 'bg-gray-100');
//
//             const $messageText = $messageElement.querySelector('.message-text');
//             const $messageTime = $messageElement.querySelector('.message-time');
//
//             if ($messageText) $messageText.textContent = message.message;
//             if ($messageTime) $messageTime.textContent = formattedTime;
//
//             $messageContainer.appendChild($messageElement);
//         }
//     }
//
//     const $chatMessages = document.getElementById('chatMessages');
//     if ($chatMessages) {
//         $chatMessages.scrollTop = $chatMessages.scrollHeight;
//     }
// }
//
// function formatDate(date) {
//     const options = { year: 'numeric', month: 'long', day: 'numeric' };
//     return date.toLocaleDateString('ko-KR', options);
// }
//
// function formatTime(date) {
//     const hours = date.getHours();
//     const minutes = date.getMinutes();
//     const ampm = hours >= 12 ? '오후' : '오전';
//     const formattedHours = hours % 12 || 12;
//     const formattedMinutes = minutes < 10 ? '0' + minutes : minutes;
//     return `${ampm} ${formattedHours}:${formattedMinutes}`;
// }
//
// function sendMessage() {
//     const $messageInput = document.getElementById('messageInput');
//     const content = $messageInput.value.trim();
//     if (content !== '') {
//         const chatMessage = {
//             chatroomId: chatroomId,
//             sender: username,
//             message: content
//         };
//         stompClient.send("/app/chat", {
//             Authorization: 'Bearer ' + token
//         }, JSON.stringify(chatMessage));
//         $messageInput.value = '';
//     }
// }
//
// function onMessageReceived(payload) {
//     console.log('Message received:', payload);
//     const message = JSON.parse(payload.body);
//     showMessage(message);
// }
//
// function getCookie(name) {
//     const value = `; ${document.cookie}`;
//     const parts = value.split(`; ${name}=`);
//     if (parts.length === 2) return parts.pop().split(';').shift();
// }
//
// // Event listeners
// document.addEventListener('DOMContentLoaded', () => {
//     const $sendButton = document.getElementById('sendButton');
//     const $messageInput = document.getElementById('messageInput');
//
//     if ($sendButton) {
//         $sendButton.addEventListener('click', sendMessage);
//     }
//
//     if ($messageInput) {
//         $messageInput.addEventListener('keypress', function(e) {
//             if (e.key === 'Enter') {
//                 sendMessage();
//             }
//         });
//     }
// });