export function deletePost() {
    const noticeId = $('#editForm').data('notice-id');
    if (confirm('정말로 삭제하시겠습니까?')) {
        $.ajax({
            url: '/notice/' + noticeId,
            method: 'DELETE',
            success: function(response) {
                window.location.href = '/notice';
            },
            error: function(xhr) {
                alert('공지사항 삭제 실패: ' + xhr.responseJSON.error);
            }
        });
    }
}