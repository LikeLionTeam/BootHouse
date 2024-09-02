export function inviteUsers(chatroomId, selectedUserIds) {
    console.log('Inviting users to chatroom:', chatroomId, 'Selected users:', selectedUserIds);
    fetch(`/api/chat/invite?chatroomId=${chatroomId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedUserIds)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.error || 'Unknown error occurred');
                });
            }
            return response.json();
        })
        .then(result => {
            console.log('Invitation result:', result);
            alert('새로운 유저가 초대되었습니다!');
            document.querySelector('.chat-title').textContent = result.chatroomName;
            window.location.reload(); // 페이지 새로고침
        })
        .catch(error => {
            console.error('Error inviting users:', error);
            alert('유저 초대 중 오류가 발생했습니다: ' + error.message);
        });
}

export function leaveChat(chatroomId) {
    if (confirm('정말로 이 채팅방을 나가시겠습니까?')) {
        fetch(`/api/chat/${chatroomId}/leave`, {
            method: 'POST',
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert('채팅방을 나갔습니다.');
                    window.location.href = '/messages';  // 채팅 목록 페이지로 리다이렉트
                } else {
                    alert('채팅방을 나가는데 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error leaving chat:', error);
                alert('채팅방을 나가는 중 오류가 발생했습니다.');
            });
    }
}