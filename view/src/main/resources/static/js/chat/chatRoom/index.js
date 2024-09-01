import { initWebSocket } from './websocketConnection.js';
import { initializeChat } from './messageHandling.js';
import { setupEventListeners } from './uiUpdates.js';
import { initChatOptions } from './chatOptions.js';

document.addEventListener('DOMContentLoaded', () => {
    initWebSocket();
    initializeChat();
    setupEventListeners();
    initChatOptions();
});