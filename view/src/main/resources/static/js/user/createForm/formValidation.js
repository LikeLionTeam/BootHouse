export function initFormValidation() {
    const form = document.getElementById('signupForm');

    if (form) {
        form.addEventListener('submit', validateForm);
    }
}

function validateForm(event) {
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const nameInput = document.getElementById('name');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const addressInput = document.getElementById('address');

    let isValid = true;

    if (!emailInput.value) {
        showError(emailInput, '이메일을 입력해주세요.');
        isValid = false;
    }

    if (!passwordInput.value) {
        showError(passwordInput, '비밀번호를 입력해주세요.');
        isValid = false;
    }

    if (!nameInput.value) {
        showError(nameInput, '이름을 입력해주세요.');
        isValid = false;
    }

    if (!phoneNumberInput.value) {
        showError(phoneNumberInput, '전화번호를 입력해주세요.');
        isValid = false;
    }

    if (!addressInput.value) {
        showError(addressInput, '주소를 입력해주세요.');
        isValid = false;
    }

    if (!isValid) {
        event.preventDefault();
    }
}

function showError(input, message) {
    const errorElement = input.nextElementSibling;
    if (errorElement && errorElement.classList.contains('field-error')) {
        errorElement.textContent = message;
    } else {
        const newErrorElement = document.createElement('div');
        newErrorElement.classList.add('field-error', 'text-sm', 'mt-1');
        newErrorElement.textContent = message;
        input.parentNode.insertBefore(newErrorElement, input.nextSibling);
    }
}