export function setButtonColor(button, isLiked) {
    if (isLiked) {
        button.classList.remove('bg-gray-200', 'hover:bg-gray-400', 'text-gray-800');
        button.classList.add('bg-red-500', 'hover:bg-red-700', 'text-white');
        button.innerHTML = '<i class="fas fa-heart mr-2"></i>찜 완료';
    } else {
        button.classList.remove('bg-red-500', 'hover:bg-red-700', 'text-white');
        button.classList.add('bg-gray-200', 'hover:bg-gray-400', 'text-gray-800');
        button.innerHTML = '<i class="fas fa-heart mr-2"></i>찜하기';
    }
}