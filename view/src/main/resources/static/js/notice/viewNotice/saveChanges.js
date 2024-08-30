export function saveChanges() {
    const noticeId = $('#editForm').data('notice-id');
    const updatedTitle = $('#editTitle').val();
    const updatedContent = $('#editContent').val();
    const updatedPostType = $('#editPostType').val();
    const updatedImportance = $('#editImportance').is(':checked');

    $.ajax({
        url: '/notice/' + noticeId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            title: updatedTitle,
            content: updatedContent,
            postType: updatedPostType,
            importance: updatedImportance
        }),
        success: function(response) {
            window.location.href = response.redirectUrl;
        },
        error: function(xhr) {
            alert('Error updating post: ' + xhr.responseJSON.error);
        }
    });

    $('#editForm').hide();
    $('#noticeTitle').show();
    $('#noticeContent').show();
    $('#editButton').show();
}