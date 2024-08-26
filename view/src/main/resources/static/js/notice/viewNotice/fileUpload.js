import { insertAtCursor } from './utils.js';

export function uploadFiles() {
    const fileInput = document.getElementById('fileInput');
    Array.from(fileInput.files).forEach(file => {
        const formData = new FormData();
        formData.append("file", file);

        fetch('/notice/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.url) {
                    const editContent = document.getElementById('editContent');
                    insertAtCursor(editContent, `![](${data.url})\n`);
                }
                fileInput.value = '';
            })
            .catch(error => {
                console.error('Error uploading image:', error);
                alert('Error uploading image: ' + error.message);
            });
    });
}