import { onMessageReceived } from './messageHandling.js';
import { getCookie } from './utils.js';

let stompClient = null;
const token = getCookie('userTokenCode');

export function initWebSocket() {
    connect();
}

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages/' + chatroomId, onMessageReceived);
    stompClient.subscribe('/user/' + username + '/queue/errors', onError);
}

function onError(error) {
    console.log('STOMP error ' + error);
    setTimeout(connect, 5000);  // 5초 후 재연결 시도
}

export function sendWebSocketMessage(message) {
    stompClient.send("/app/chat", {
        Authorization: 'Bearer ' + token
    }, JSON.stringify(message));
}