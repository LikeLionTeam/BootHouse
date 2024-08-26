export function initPagination() {
    const paginationLinks = document.querySelectorAll('.pagination-link');
    paginationLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault();
            const url = new URL(link.href);
            const currentUrl = new URL(window.location.href);

            // 현재 URL의 모든 쿼리 파라미터를 새 URL에 추가
            for (let [key, value] of currentUrl.searchParams.entries()) {
                if (key !== 'page' && key !== 'size') {
                    url.searchParams.set(key, value);
                }
            }

            window.location.href = url.toString();
        });
    });
}