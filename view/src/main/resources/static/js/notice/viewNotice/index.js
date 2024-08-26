import { enableEditMode } from './editMode.js';
import { deletePost } from './deletePost.js';
import { saveChanges } from './saveChanges.js';
import { uploadFiles } from './fileUpload.js';

document.addEventListener('DOMContentLoaded', () => {
    const enableEditBtn = document.getElementById('enableEditBtn');
    const deletePostBtn = document.getElementById('deletePostBtn');
    const saveChangesBtn = document.getElementById('saveChangesBtn');
    const fileInput = document.getElementById('fileInput');

    if (enableEditBtn) enableEditBtn.addEventListener('click', enableEditMode);
    if (deletePostBtn) deletePostBtn.addEventListener('click', deletePost);
    if (saveChangesBtn) saveChangesBtn.addEventListener('click', saveChanges);
    if (fileInput) fileInput.addEventListener('change', uploadFiles);
});