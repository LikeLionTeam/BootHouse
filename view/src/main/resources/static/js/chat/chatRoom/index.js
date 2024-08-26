import { initWebSocket } from './websocketConnection.js';
import { initializeChat } from './messageHandling.js';
import { setupEventListeners } from './uiUpdates.js';

document.addEventListener('DOMContentLoaded', () => {
    initWebSocket();
    initializeChat();
    setupEventListeners();
});