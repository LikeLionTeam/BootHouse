import { htmlToMarkdown } from './utils.js';

export function enableEditMode() {
    $('#editTitle').val($('#noticeTitle').text());
    const htmlContent = $('#noticeContent').html();
    const markdown = htmlToMarkdown(htmlContent);
    $('#editContent').val(markdown);
    $('#noticeTitle').hide();
    $('#noticeContent').hide();
    $('#editForm').show();
    $('#editButton').hide();
}