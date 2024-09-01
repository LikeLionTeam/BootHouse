export function inviteUsers(chatroomId, selectedUserIds) {
    fetch(`/api/chat/invite?chatroomId=${chatroomId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedUserIds)  // 변경된 부분
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(result => {
            console.log('Invitation result:', result);
            // 성공 메시지 표시
            alert('사용자 초대에 성공했습니다.');
        })
        .catch(error => {
            console.error('Error inviting users:', error);
            // 에러 메시지 표시
            alert('사용자 초대 중 오류가 발생했습니다.');
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
                    window.location.href = '/messages';  // 채팅 목록 페이지로 리다이렉트
                } else {
                    alert('채팅방을 나가는데 실패했습니다.');
                }
            });
    }
}