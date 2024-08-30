import { setButtonColor } from './utils.js';

export function initWishlist() {
    const wishlistBtn = document.getElementById('wishlistBtn');
    const isUserLoggedIn = document.getElementById('isUserLoggedIn').value === 'true';
    const courseId = document.getElementById('courseId').value;

    if (isUserLoggedIn) {
        checkWishlistStatus(courseId);
    }

    wishlistBtn.addEventListener('click', () => wishlistAction(isUserLoggedIn, courseId));
}

function checkWishlistStatus(courseId) {
    fetch(`/courses/${courseId}/isLiked`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => response.json())
        .then(data => {
            const wishlistBtn = document.getElementById('wishlistBtn');
            setButtonColor(wishlistBtn, data.isLiked === 'true');
        })
        .catch(error => console.error('Error:', error));
}

function wishlistAction(isUserLoggedIn, courseId) {
    if (isUserLoggedIn) {
        fetch(`/courses/${courseId}/like`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        })
            .then(response => response.text())
            .then(message => {
                alert(message);
                const wishlistBtn = document.getElementById('wishlistBtn');
                setButtonColor(wishlistBtn, message.includes("추가"));
            })
            .catch(error => console.error('Error:', error));
    } else {
        if (confirm('로그인/회원가입을 하시겠어요?')) {
            window.location.href = '/login';
        }
    }
}