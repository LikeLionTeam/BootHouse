import { openModal, closeModal } from '../chatList/modal.js';
import { inviteUsers, leaveChat } from './chatActions.js';

export function initChatOptions() {
    const chatOptionsBtn = document.getElementById('chatOptionsBtn');
    const chatOptionsMenu = document.getElementById('chatOptionsMenu');
    const inviteUsersBtn = document.getElementById('inviteUsersBtn');
    const leaveChatBtn = document.getElementById('leaveChatBtn');

    chatOptionsBtn.addEventListener('click', function(e) {
        e.preventDefault();
        chatOptionsMenu.classList.toggle('hidden');
    });

    inviteUsersBtn.addEventListener('click', function(e) {
        e.preventDefault();
        openInviteUsersModal();
    });

    leaveChatBtn.addEventListener('click', function(e) {
        e.preventDefault();
        leaveChat(chatroomId);
    });

    // 메뉴 외의 영역 클릭 시 메뉴 닫기
    document.addEventListener('click', function(e) {
        if (!chatOptionsMenu.contains(e.target) && !chatOptionsBtn.contains(e.target)) {
            chatOptionsMenu.classList.add('hidden');
        }
    });
}

function openInviteUsersModal() {
    fetch('/api/chat/users')
        .then(response => response.json())
        .then(users => {
            showUsersInModal(users);
        });
}

function showUsersInModal(users) {
    const modalContent = `
        <div id="inviteUsersModal" class="modal-wrap fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div class="modal-content-wrap bg-white rounded-lg p-8 max-w-md w-full">
                <h2 class="modal-title-wrap text-2xl font-bold mb-4">사용자 초대</h2>
                <div id="userList" class="max-h-60 overflow-y-auto">
                    ${users.map(user => `
                        <div class="user-item">
                            <label>
                                <input type="checkbox" value="${user.id}"> ${user.name}
                            </label>
                        </div>
                    `).join('')}
                </div>
                <button id="inviteSelectedBtn" class="bg-orange-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded mt-4">
                    선택한 사용자 초대하기
                </button>
                <button id="closeModalBtn" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded mt-4 ml-2">
                    취소
                </button>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalContent);

    document.getElementById('inviteSelectedBtn').addEventListener('click', () => {
        const selectedUsers = Array.from(document.querySelectorAll('#userList input:checked'))
            .map(checkbox => parseInt(checkbox.value, 10));
        console.log('Selected user IDs:', selectedUsers);
        if (selectedUsers.length > 0) {
            inviteUsers(chatroomId, selectedUsers);
            closeModal('inviteUsersModal');
        } else {
            alert('초대할 사용자를 선택해주세요.');
        }
    });

    document.getElementById('closeModalBtn').addEventListener('click', () => {
        closeModal('inviteUsersModal');
    });
}