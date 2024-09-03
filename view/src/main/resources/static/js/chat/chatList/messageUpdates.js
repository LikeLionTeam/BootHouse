export function updateLastMessageTimes() {
    const chatItems = document.querySelectorAll('.chat-item-wrap');
    chatItems.forEach(item => {
        const chatroomId = item.dataset.chatroomId;
        const timeElement = item.querySelector('.chat-time-wrap');

        fetch(`/api/chat/last-message?chatroomId=${chatroomId}`)
            .then(response => response.json())
            .then(data => {
                timeElement.textContent = formatTime(new Date(data.timestamp));
            })
            .catch(error => console.error('Error updating message time:', error));
    });
}

function formatTime(date) {
    const now = new Date();
    const diff = now - date;
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    if (days > 0) return `${days}일 전`;
    if (hours > 0) return `${hours}시간 전`;
    if (minutes > 0) return `${minutes}분 전`;
    return '방금 전';
}