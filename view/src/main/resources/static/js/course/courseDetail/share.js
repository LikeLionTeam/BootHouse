export function initShare() {
    const shareButton = document.getElementById('shareButton');
    const sharePopup = document.getElementById('sharePopup');
    const closeSharePopup = document.getElementById('closeSharePopup');

    shareButton.addEventListener('click', () => sharePopup.classList.remove('hidden'));
    closeSharePopup.addEventListener('click', () => sharePopup.classList.add('hidden'));

    document.getElementById('shareKakao').addEventListener('click', shareKaKao);
    document.getElementById('shareFacebook').addEventListener('click', shareFacebook);
    document.getElementById('shareX').addEventListener('click', shareX);
    document.getElementById('copyUrl').addEventListener('click', copyToClipboard);
}

function shareKaKao() {
    if (!Kakao.isInitialized()) {
        console.error('Kakao SDK가 초기화되지 않았습니다.');
        return;
    }

    const currentURL = window.location.href;
    const title = '[부트하우스] 2024년 국비지원, 부트캠프 모음!';
    const description = '모든 부트캠프들의 정보를 한눈에 비교하고, 리뷰 및 후기들을 확인해보세요!';
    const imageurl = 'https://ifh.cc/g/b6Nn12.jpg';

    Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
            title: title,
            description: description,
            imageUrl: imageurl,
            link: { mobileWebUrl: currentURL, webUrl: currentURL },
        },
        buttons: [{ title: '웹으로 이동', link: { mobileWebUrl: currentURL, webUrl: currentURL } }],
        installTalk: true,
    });
}

function shareFacebook() {
    const url = encodeURI(window.location.href);
    window.open("http://www.facebook.com/sharer/sharer.php?u=" + url);
}

function shareX() {
    const url = encodeURI(window.location.href);
    const text = '부트캠프 프로그램 공유';
    window.open("https://twitter.com/intent/tweet?text=" + text + "&url=" + url);
}

function copyToClipboard() {
    const url = window.location.href;
    navigator.clipboard.writeText(url).then(() => {
        alert('URL이 복사되었습니다.');
    });
}