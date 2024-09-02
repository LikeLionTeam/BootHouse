import { showMessage } from './messageHandling.js';

export function inviteUsers(chatroomId, selectedUserIds) {
    fetch(`/api/chat/invite?chatroomId=${chatroomId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedUserIds)
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 409) {
                    throw new Error('이미 채팅방에 있는 사용자입니다.');
                }
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(result => {
            console.log('Invitation result:', result);
            showMessage({
                message: '새로운 유저가 초대되었습니다!',
                type: 'system'
            });
        })
        .catch(error => {
            console.error('Error inviting users:', error);
            alert(error.message);
        });
}
export function leaveChat() {
    if (confirm('정말로 이 채팅방을 나가시겠습니까?')) {
        fetch(`/api/chat/${chatroomId}/leave`, {
            method: 'POST',
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    showMessage({
                        message: '사용자가 채팅방을 나갔습니다.',
                        type: 'system'
                    });
                    window.location.href = '/messages';
                } else {
                    alert('채팅방을 나가는데 실패했습니다.');
                }
            });
    }
}