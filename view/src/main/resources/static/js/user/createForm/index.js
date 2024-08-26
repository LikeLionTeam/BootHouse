import { initEmailVerification } from './emailVerification.js';
import { initFormValidation } from './formValidation.js';

document.addEventListener('DOMContentLoaded', () => {
    initEmailVerification();
    initFormValidation();
});