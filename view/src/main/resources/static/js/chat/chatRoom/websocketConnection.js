// websocketConnection.js

import { showMessage } from './messageHandling.js';
import { getCookie } from './utils.js';

let stompClient = null;

export function initWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    const headers = {};
    const token = getCookie('userTokenCode');
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    stompClient.connect(headers, onConnected, onError);
}

function onConnected() {
    console.log('WebSocket Connected');

    if (typeof chatroomId === 'undefined') {
        console.error('chatroomId is undefined');
        return;
    }

    stompClient.subscribe('/topic/messages/' + chatroomId, onMessageReceived);
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({chatroomId: chatroomId, sender: username, type: 'JOIN'})
    );

    fetchLatestMessages(chatroomId);
}

function onError(error) {
    console.error('WebSocket Error:', error);
    // 여기에 사용자에게 연결 오류를 알리는 로직을 추가할 수 있습니다.
}

function fetchLatestMessages(chatroomId) {
    fetch(`/api/chat/${chatroomId}/messages`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(messages => {
            console.log('Fetched messages:', messages);
            messages.forEach(showMessage);
        })
        .catch(error => {
            console.error('Error fetching messages:', error);
            // 여기에 사용자에게 메시지 로딩 실패를 알리는 로직을 추가할 수 있습니다.
        });
}

export function sendWebSocketMessage(chatMessage) {
    if (stompClient && stompClient.connected) {
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
    } else {
        console.error('STOMP client is not connected');
        // 여기에 사용자에게 메시지 전송 실패를 알리는 로직을 추가할 수 있습니다.
    }
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    console.log('Received message:', message);
    showMessage(message);
}

export function disconnectWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

// 채팅방을 나갈 때 호출할 함수
export function leaveChat() {
    if (stompClient && stompClient.connected) {
        stompClient.send("/app/chat.leaveUser",
            {},
            JSON.stringify({chatroomId: chatroomId, sender: username, type: 'LEAVE'})
        );
        disconnectWebSocket();
    }
}