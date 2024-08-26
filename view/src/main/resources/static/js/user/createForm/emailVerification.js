let sentCode = null;

export function initEmailVerification() {
    const sendVerificationBtn = document.getElementById('sendVerificationBtn');
    const verifyCodeBtn = document.getElementById('verifyCodeBtn');

    if (sendVerificationBtn) {
        sendVerificationBtn.addEventListener('click', sendEmailVerification);
    }

    if (verifyCodeBtn) {
        verifyCodeBtn.addEventListener('click', verifyCode);
    }
}

function sendEmailVerification() {
    const email = document.getElementById('email').value;

    if (email) {
        fetch('/users/send-verification-code', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('인증 코드가 전송되었습니다.');
                    sentCode = data.code;
                    document.getElementById('verificationCodeSection').classList.remove('hidden');
                } else {
                    alert('인증 코드 전송에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다.');
            });
    } else {
        alert('이메일을 입력하세요.');
    }
}

function verifyCode() {
    const userCode = document.getElementById('verificationCode').value;
    const submitBtn = document.getElementById('submitBtn');

    if (userCode === sentCode) {
        alert('인증이 성공적으로 완료되었습니다!');
        document.getElementById('hiddenVerificationCode').value = userCode;
        submitBtn.disabled = false;
    } else {
        alert('인증 코드가 일치하지 않습니다. 다시 시도하세요.');
        submitBtn.disabled = true;
    }
}