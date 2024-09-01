import { openModal, closeModal } from './modal.js';

export function initNewChatButton() {
    const $newChatBtn = document.getElementById('newChatBtn');
    const $cancelBtn = document.getElementById('cancelBtn');
    const $startChatBtn = document.getElementById('startChatBtn');

    if ($newChatBtn) {
        $newChatBtn.addEventListener('click', () => {
            openModal('newChatModal');
            fetchUsers();
        });
    }

    if ($cancelBtn) {
        $cancelBtn.addEventListener('click', () => {
            closeModal('newChatModal');
        });
    }

    if ($startChatBtn) {
        $startChatBtn.addEventListener('click', startNewChat);
    }
}

function fetchUsers() {
    fetch('/api/chat/users')
        .then(response => response.json())
        .then(users => {
            const $userList = document.getElementById('userList');
            if ($userList) {
                $userList.innerHTML = '';
                users.forEach(user => {
                    const li = document.createElement('li');
                    li.className = 'flex items-center p-2 hover:bg-gray-100';
                    li.innerHTML = `
                        <input type="checkbox" value="${user.id}" class="mr-2">
                        <i class="fas fa-user-circle text-gray-400 mr-2"></i>
                        <span>${user.name}</span>
                    `;
                    $userList.appendChild(li);
                });
            }
        })
        .catch(error => console.error('Error fetching users:', error));
}

function startNewChat() {
    const selectedUsers = Array.from(document.querySelectorAll('#userList input:checked')).map(input => parseInt(input.value));
    if (selectedUsers.length === 0) {
        const errorMessage = document.getElementById('errorMessage');
        if (errorMessage) {
            errorMessage.textContent = '최소 한 명의 사용자를 선택해주세요.';
        }
        return;
    }

    fetch('/messages/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedUsers),
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            if (data.chatroomId) {
                window.location.href = `/messages/${data.chatroomId}`;
            } else {
                throw new Error('채팅방 ID를 받지 못했습니다.');
            }
        })
        .catch(error => {
            console.error('Error starting new chat:', error);
            const errorMessage = document.getElementById('errorMessage');
            if (errorMessage) {
                errorMessage.textContent = `채팅방 생성 중 오류가 발생했습니다: ${error.error || error.message}`;
            }
        });
}

export function inviteUsers() {
    startNewChat();
}