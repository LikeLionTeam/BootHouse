export function initLikeButton() {
    const likeButton = document.querySelector('.like-button');
    const likeIcon = document.querySelector('#likeButton');

    if (!likeButton) return;

    let liked = likeButton.dataset.liked === 'true';
    updateLikeIcon(liked);

    likeButton.addEventListener('click', () => {
        const reviewId = likeButton.getAttribute('data-review-id');
        toggleLike(reviewId, liked, updateLikeIcon);
    });
}

function updateLikeIcon(liked) {
    const likeIcon = document.querySelector('#likeButton');
    likeIcon.classList.toggle('liked', liked);
    likeIcon.classList.toggle('fas', liked);
    likeIcon.classList.toggle('far', !liked);
}

function toggleLike(reviewId, liked, callback) {
    fetch(`/likes/review/${reviewId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            liked = data === "리뷰 좋아요 등록";
            callback(liked);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}